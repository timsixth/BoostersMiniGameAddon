package pl.timsixth.boostersaddon.listener;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import pl.timsixth.boostersaddon.manager.UserBoostersManager;
import pl.timsixth.boostersaddon.model.user.UserBoosters;
import pl.timsixth.minigameapi.api.coins.UserCoins;
import pl.timsixth.minigameapi.api.coins.manager.UserCoinsManager;
import pl.timsixth.minigameapi.api.game.event.PlayerWinGameEvent;

import java.util.Optional;

@RequiredArgsConstructor
public class PlayerWinGameListener implements Listener {

    private final UserCoinsManager userCoinsManager;
    private final UserBoostersManager userBoostersManager;

    @EventHandler
    private void onWin(PlayerWinGameEvent event) {
        Player player = event.getPlayer();

        Optional<UserBoosters> userBoostersOptional = userBoostersManager.getUserByUuid(player.getUniqueId());
        Optional<UserCoins> userCoinsOptional = userCoinsManager.getUserByUuid(player.getUniqueId());

        if (!userBoostersOptional.isPresent()) return;
        if (!userCoinsOptional.isPresent()) return;

        UserBoosters userBoostersDbModel = userBoostersOptional.get();
        UserCoins userCoinsDbModel = userCoinsOptional.get();

        double fullMultiplier = userBoostersDbModel.getFullMultiplier();

        double newBalance = event.getCostOfWin() * fullMultiplier;

        int coins = (int) (newBalance - event.getCostOfWin());

        userCoinsDbModel.addCoins(coins);
    }
}
