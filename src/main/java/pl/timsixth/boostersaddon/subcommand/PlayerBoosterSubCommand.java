package pl.timsixth.boostersaddon.subcommand;

import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.timsixth.boostersaddon.config.Messages;
import pl.timsixth.boostersaddon.gui.MenuManager;
import pl.timsixth.boostersaddon.manager.UserBoostersManager;
import pl.timsixth.boostersaddon.model.user.UserBooster;
import pl.timsixth.boostersaddon.model.user.UserBoosters;
import pl.timsixth.guilibrary.core.model.Menu;
import pl.timsixth.minigameapi.api.command.SubCommand;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class PlayerBoosterSubCommand implements SubCommand {

    private final Messages messages;
    private final UserBoostersManager userBoostersManager;
    private final MenuManager menuManager;

    @Override
    public boolean executeCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        if (args.length == 2) {
            if (args[1].equalsIgnoreCase("list")) {
                Optional<UserBoosters> userOptional = userBoostersManager.getUserByUuid(player.getUniqueId());
                Optional<Menu> menuOptional = menuManager.getMenuByName("yourBoosters");

                List<UserBooster> userBoosterList = Collections.emptyList();

                if (!menuOptional.isPresent()) return true;
                if (userOptional.isPresent()) {
                    UserBoosters userBoostersDbModel = userOptional.get();
                    userBoosterList = userBoostersDbModel.getBoosters();
                }

                Menu menu = menuOptional.get();

                menuManager.openGeneratedMenu(player, menu, userBoosterList);
            } else if (args[1].equalsIgnoreCase("help")) {
                messages.getPlayerHelp().forEach(player::sendMessage);
            } else {
                messages.getPlayerHelp().forEach(player::sendMessage);
            }
        } else {
            messages.getPlayerHelp().forEach(player::sendMessage);
        }
        return false;
    }

    @Override
    public String getName() {
        return "booster";
    }
}
