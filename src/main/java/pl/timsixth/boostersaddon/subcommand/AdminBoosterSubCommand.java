package pl.timsixth.boostersaddon.subcommand;

import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.timsixth.boostersaddon.config.Messages;
import pl.timsixth.boostersaddon.config.Settings;
import pl.timsixth.boostersaddon.factory.UserBoosterFactory;
import pl.timsixth.boostersaddon.gui.process.CreateBoosterProcess;
import pl.timsixth.boostersaddon.manager.BoosterManager;
import pl.timsixth.boostersaddon.manager.UserBoostersManager;
import pl.timsixth.boostersaddon.model.BoosterFileModel;
import pl.timsixth.boostersaddon.model.user.UserBooster;
import pl.timsixth.boostersaddon.model.user.UserBoostersDbModel;
import pl.timsixth.boostersaddon.model.user.impl.UserBoostersImpl;
import pl.timsixth.guilibrary.core.manager.YAMLMenuManager;
import pl.timsixth.guilibrary.processes.manager.ProcessRunner;
import pl.timsixth.minigameapi.api.command.SubCommand;
import pl.timsixth.minigameapi.api.util.ChatUtil;

import java.util.Optional;

@RequiredArgsConstructor
public class AdminBoosterSubCommand implements SubCommand {

    private final Messages messages;
    private final Settings settings;
    private final YAMLMenuManager menuManager;
    private final BoosterManager<BoosterFileModel> boosterManager;
    private final UserBoostersManager<UserBoostersDbModel> userBoostersManager;

    @Override
    public boolean executeCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        if (args.length == 2) {
            if (args[1].equalsIgnoreCase("help")) {
                messages.getAdminHelp().forEach(player::sendMessage);
            } else if (args[1].equalsIgnoreCase("create")) {
                ProcessRunner.runProcess(player, new CreateBoosterProcess(settings, menuManager, player, messages));
            } else if (args[1].equalsIgnoreCase("list")) {
                player.sendMessage(messages.getBoosters());
                boosterManager.getBoosters().forEach(boosterFileModel -> player.sendMessage(
                        ChatUtil.chatColor("&e- &a" + boosterFileModel.getName() + " &c" + boosterFileModel.getMultiplier() + " &6" + boosterFileModel.getType().name())));
            }
        } else if (args.length == 4) {
            if (args[1].equalsIgnoreCase("give")) {
                if (args[2].equalsIgnoreCase("global")) {
                    Optional<BoosterFileModel> boosterOptional = boosterManager.getBoosterByName(args[3]);

                    if (!boosterOptional.isPresent()) {
                        player.sendMessage(messages.getBoosterDoesNotExist());
                        return true;
                    }

                    BoosterFileModel boosterFileModel = boosterOptional.get();

                    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                        UserBoostersDbModel userBoostersDbModel = getUserBoostersDbModel(player);
                        if (giveBooster(onlinePlayer, boosterFileModel, userBoostersDbModel)) continue;

                        userBoostersDbModel.update();
                    }
                    player.sendMessage(messages.getGivenBoosterEveryone());
                } else {
                    Player other = Bukkit.getPlayer(args[2]);

                    if (other == null) {
                        player.sendMessage(messages.getPlayerOffline());
                        return true;
                    }

                    Optional<BoosterFileModel> boosterOptional = boosterManager.getBoosterByName(args[3]);

                    if (!boosterOptional.isPresent()) {
                        player.sendMessage(messages.getBoosterDoesNotExist());
                        return true;
                    }

                    UserBoostersDbModel userBoostersDbModel = getUserBoostersDbModel(player);
                    BoosterFileModel boosterFileModel = boosterOptional.get();

                    if (giveBooster(player, boosterFileModel, userBoostersDbModel)) {
                        player.sendMessage(messages.getHaveThisBooster());
                        return true;
                    }
                    userBoostersDbModel.update();

                    player.sendMessage(messages.getGivenBooster().replace("{PLAYER_NAME}", other.getName()));
                }
            }
        } else {
            messages.getAdminHelp().forEach(player::sendMessage);
        }
        return false;
    }

    private UserBoostersDbModel getUserBoostersDbModel(Player player) {
        Optional<UserBoostersDbModel> userBoostersOptional = userBoostersManager.getUserByUuid(player.getUniqueId());

        UserBoostersDbModel userBoostersDbModel;
        if (!userBoostersOptional.isPresent()) {
            userBoostersDbModel = new UserBoostersImpl(player.getUniqueId());
            userBoostersDbModel.save();
            userBoostersManager.addUser(userBoostersDbModel);
        } else {
            userBoostersDbModel = userBoostersOptional.get();
        }
        return userBoostersDbModel;
    }


    @Override
    public String getName() {
        return "booster";
    }

    private boolean giveBooster(Player player, BoosterFileModel boosterFileModel, UserBoostersDbModel userBoostersDbModel) {
        UserBooster userBooster = UserBoosterFactory.createUserBooster(player.getUniqueId(), boosterFileModel);

        if (userBoostersDbModel.hasBooster(userBooster)) return true;

        userBoostersDbModel.addBooster(userBooster);
        return false;
    }
}
