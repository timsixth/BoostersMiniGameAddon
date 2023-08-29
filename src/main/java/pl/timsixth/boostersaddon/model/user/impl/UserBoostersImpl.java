package pl.timsixth.boostersaddon.model.user.impl;

import lombok.Getter;
import pl.timsixth.boostersaddon.model.user.UserBooster;
import pl.timsixth.boostersaddon.model.user.UserBoosterDbModel;
import pl.timsixth.boostersaddon.model.user.UserBoostersDbModel;
import pl.timsixth.databasesapi.database.query.QueryBuilder;
import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.database.AbstractDbModel;
import pl.timsixth.minigameapi.api.database.annoations.Id;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class UserBoostersImpl extends AbstractDbModel implements UserBoostersDbModel {

    @Id
    private final UUID uuid;
    private final List<UserBooster> boosters;

    public UserBoostersImpl(UUID uuid) {
        this.uuid = uuid;
        this.boosters = new ArrayList<>();
        init();
    }

    public UserBoostersImpl(UUID uuid, List<UserBooster> boosters) {
        this.uuid = uuid;
        this.boosters = boosters;
        init();
    }

    @Override
    public double getFullMultiplier() {
        return boosters.stream()
                .mapToDouble(userBooster -> userBooster.getBooster().getMultiplier())
                .sum();
    }

    @Override
    public void addBooster(UserBooster userBooster) {
        boosters.add(userBooster);
    }

    @Override
    public boolean hasBooster(UserBooster booster) {
        return boosters.stream()
                .anyMatch(userBooster -> userBooster.getBooster().getName().equalsIgnoreCase(booster.getBooster().getName()));
    }

    @Override
    public String getTableName() {
        return MiniGame.getInstance().getDefaultPluginConfiguration().getTablesPrefix() + "user_boosters";
    }

    @Override
    public Object save() {
        QueryBuilder queryBuilder = new QueryBuilder();

        String insert = queryBuilder.insert(getTableName(), null, uuid).build();

        executeUpdate(insert);

        boosters.forEach(userBooster -> ((UserBoosterDbModel) userBooster).save());

        return this;
    }

    @Override
    public boolean delete() {
        QueryBuilder queryBuilder = new QueryBuilder();

        String query = queryBuilder.deleteAll(getTableName()).where("uuid = " + getId()).build();

        executeUpdate(query);

        boosters.forEach(userBooster -> ((UserBoosterDbModel) userBooster).delete());

        return true;
    }

    @Override
    public Object update() {
        boosters.forEach(userBooster -> ((UserBoosterDbModel) userBooster).delete());

        boosters.forEach(userBooster -> ((UserBoosterDbModel) userBooster).save());

        return this;
    }
}
