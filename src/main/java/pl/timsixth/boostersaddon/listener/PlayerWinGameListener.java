package pl.timsixth.boostersaddon.listener;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import pl.timsixth.boostersaddon.manager.UserBoostersManager;
import pl.timsixth.boostersaddon.model.user.UserBoostersDbModel;
import pl.timsixth.minigameapi.api.coins.UserCoinsDbModel;
import pl.timsixth.minigameapi.api.coins.manager.UserCoinsManager;
import pl.timsixth.minigameapi.api.game.event.PlayerWinGameEvent;

import java.util.Optional;

@RequiredArgsConstructor
public class PlayerWinGameListener implements Listener {

    private final UserCoinsManager<UserCoinsDbModel> userCoinsManager;
    private final UserBoostersManager<UserBoostersDbModel> userBoostersManager;

    @EventHandler
    private void onWin(PlayerWinGameEvent event) {
        Player player = event.getPlayer();

        Optional<UserBoostersDbModel> userBoostersOptional = userBoostersManager.getUserByUuid(player.getUniqueId());
        Optional<UserCoinsDbModel> userCoinsOptional = userCoinsManager.getUserByUuid(player.getUniqueId());

        if (!userBoostersOptional.isPresent()) return;
        if (!userCoinsOptional.isPresent()) return;

        UserBoostersDbModel userBoostersDbModel = userBoostersOptional.get();
        UserCoinsDbModel userCoinsDbModel = userCoinsOptional.get();

        double fullMultiplier = userBoostersDbModel.getFullMultiplier();

        double newBalance = event.getCostOfWin() * fullMultiplier;

        int coins = (int) (newBalance - event.getCostOfWin());

        userCoinsDbModel.addCoins(coins);
    }
}
