package pl.timsixth.boostersaddon.model.user.impl;

import lombok.Getter;
import org.bukkit.Material;
import pl.timsixth.boostersaddon.model.Booster;
import pl.timsixth.boostersaddon.model.user.UserBooster;
import pl.timsixth.databasesapi.database.query.QueryBuilder;
import pl.timsixth.guilibrary.core.model.MenuItem;
import pl.timsixth.guilibrary.core.model.action.custom.NoneClickAction;
import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.database.AbstractDbModel;
import pl.timsixth.minigameapi.api.database.annoations.Id;
import pl.timsixth.minigameapi.api.util.ChatUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.UUID;

@Getter
public class UserBoosterImpl extends AbstractDbModel implements UserBooster {

    @Id
    private final UUID uuid;
    private final Booster booster;
    private final LocalDateTime startedDate;
    private final LocalDateTime endDate;

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


    public UserBoosterImpl(UUID uuid, Booster booster, LocalDateTime startedDate, LocalDateTime endDate) {
        this.uuid = uuid;
        this.booster = booster;
        this.startedDate = startedDate;
        this.endDate = endDate;
        init();
    }

    public UserBoosterImpl(UUID uuid, Booster booster, LocalDateTime startedDate) {
        this.uuid = uuid;
        this.booster = booster;
        this.startedDate = startedDate;
        this.endDate = null;
        init();
    }

    public UserBoosterImpl(UUID uuid, Booster booster) {
        this.uuid = uuid;
        this.booster = booster;
        this.startedDate = LocalDateTime.now();
        this.endDate = null;
        init();
    }

    @Override
    public String getTableName() {
        return MiniGame.getInstance().getPluginConfiguration().getTablesPrefix() + "user_booster";
    }

    @Override
    public Object save() {
        QueryBuilder queryBuilder = new QueryBuilder();

        String startedDateFormatted = startedDate.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).replace("T", " ");
        String endDateFormatted = null;
        if (endDate != null)
            endDateFormatted = endDate.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).replace("T", " ");

        String query = queryBuilder.insert(getTableName(), null, uuid, booster.getName(), startedDateFormatted, endDateFormatted).build();

        executeUpdate(query);

        return this;
    }

    @Override
    public boolean delete() {
        QueryBuilder queryBuilder = new QueryBuilder();

        String query = queryBuilder.deleteAll(getTableName()).where("uuid =" + getId() + " and boosterName = '" + booster.getName() + "'").build();

        executeUpdate(query);
        return true;
    }

    @Override
    public MenuItem getGeneratedItem(int slot) {
        MenuItem menuItem = new MenuItem(slot, Material.GOLD_INGOT, ChatUtil.chatColor(booster.getDisplayName()),
                ChatUtil.chatColor(Arrays.asList("&aType:&7 " + booster.getType(),
                        "&aMultiplier:&7 " + booster.getMultiplier())));

        if (endDate != null)
            menuItem = new MenuItem(slot, Material.GOLD_INGOT, ChatUtil.chatColor(booster.getDisplayName()),
                    ChatUtil.chatColor(Arrays.asList("&aType:&7 " + booster.getType(),
                            "&aMultiplier:&7 " + booster.getMultiplier(),
                            "&aEnd date:&7 " + endDate.format(DATE_TIME_FORMATTER))));

        menuItem.setAction(new NoneClickAction());

        return menuItem;
    }
}
