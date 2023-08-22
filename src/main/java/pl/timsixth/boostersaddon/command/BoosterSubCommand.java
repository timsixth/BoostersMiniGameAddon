package pl.timsixth.boostersaddon.command;

import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.timsixth.boostersaddon.config.Messages;
import pl.timsixth.boostersaddon.config.Settings;
import pl.timsixth.boostersaddon.gui.process.CreateBoosterProcess;
import pl.timsixth.guilibrary.core.manager.YAMLMenuManager;
import pl.timsixth.guilibrary.processes.manager.ProcessRunner;
import pl.timsixth.minigameapi.api.command.SubCommand;

@RequiredArgsConstructor
public class BoosterSubCommand implements SubCommand {

    private final Messages messages;
    private final Settings settings;
    private final YAMLMenuManager menuManager;
    /*
     * /tta booster give <booster_id> <global|player_name>
     * /tta booster player <player_name>
     */

    @Override
    public boolean executeCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        if (args.length == 2) {
            if (args[1].equalsIgnoreCase("help")) {
                messages.getAdminHelp().forEach(player::sendMessage);
            } else if (args[1].equalsIgnoreCase("create")) {
                ProcessRunner.runProcess(player, new CreateBoosterProcess(settings, menuManager));
            }
        } else {
            messages.getAdminHelp().forEach(player::sendMessage);
        }
        return false;
    }

    @Override
    public String getName() {
        return "booster";
    }
}
