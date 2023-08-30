package pl.timsixth.boostersaddon.timer;

import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.scheduler.BukkitRunnable;
import pl.timsixth.boostersaddon.config.Messages;
import pl.timsixth.boostersaddon.manager.UserBoostersManager;
import pl.timsixth.boostersaddon.model.user.UserBooster;
import pl.timsixth.boostersaddon.model.user.UserBoosterDbModel;
import pl.timsixth.boostersaddon.model.user.UserBoostersDbModel;
import pl.timsixth.minigameapi.api.util.ChatUtil;

import java.time.LocalDateTime;
import java.util.Iterator;

import static pl.timsixth.boostersaddon.model.user.impl.UserBoosterImpl.DATE_TIME_FORMATTER;

@RequiredArgsConstructor
public class UserBoostersTimer extends BukkitRunnable {

    private final UserBoostersManager<UserBoostersDbModel> userBoostersManager;
    private final Messages messages;
    @Override
    public void run() {

        for (UserBoostersDbModel user : userBoostersManager.getUsers()) {
            for (Iterator<UserBooster> it = user.getBoosters().iterator(); it.hasNext();) {
                UserBooster userBooster = it.next();

                if (userBooster.getEndDate() == null) continue;

                String formatNow = LocalDateTime.now().format(DATE_TIME_FORMATTER);
                String formatEndDate = userBooster.getEndDate().format(DATE_TIME_FORMATTER);

                if (formatNow.equalsIgnoreCase(formatEndDate)) {
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(userBooster.getUuid());

                    UserBoosterDbModel userBoosterDbModel = (UserBoosterDbModel) userBooster;
                    userBoosterDbModel.delete();
                    it.remove();

                    if (offlinePlayer.isOnline()) {
                        offlinePlayer.getPlayer().sendMessage(ChatUtil.chatColor(messages.getBoosterExpired().replace("{BOOSTER_DISPLAY_NAME}",
                                userBooster.getBooster().getDisplayName())));
                    }
                }
            }
        }
    }
}
