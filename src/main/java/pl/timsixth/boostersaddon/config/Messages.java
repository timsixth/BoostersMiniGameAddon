package pl.timsixth.boostersaddon.config;

import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import pl.timsixth.boostersaddon.BoostersMiniGameAddon;
import pl.timsixth.minigameapi.api.util.ChatUtil;
import pl.timsixth.minigameapi.api.util.Replacer;

import java.util.List;

@Getter
public final class Messages {

    @Getter(AccessLevel.NONE)
    private final BoostersMiniGameAddon boostersMiniGameAddon;

    private List<String> adminHelp;
    private List<String> playerHelp;
    private String invalidBoosterMultiplier;
    private String invalidBoosterDuration;
    private String createdBooster;
    private String playerOffline;
    private String boosterDoesNotExist;
    private String givenBooster;
    private String userDoesNotExist;
    private String givenBoosterEveryone;
    private String boosters;
    private String haveThisBooster;
    private String boosterExpired;
    private String boosterAlreadyExists;

    public Messages(BoostersMiniGameAddon boostersMiniGameAddon) {
        this.boostersMiniGameAddon = boostersMiniGameAddon;

        loadMessages();
    }

    void loadMessages() {
        FileConfiguration config = boostersMiniGameAddon.getConfig();

        adminHelp = Replacer.replaceCommandNames(ChatUtil.chatColor(config.getStringList("messages.adminHelp")));
        playerHelp = Replacer.replaceCommandNames(ChatUtil.chatColor(config.getStringList("messages.playerHelp")));
        invalidBoosterMultiplier = ChatUtil.chatColor(config.getString("messages.invalidBoosterMultiplier"));
        createdBooster = ChatUtil.chatColor(config.getString("messages.createdBooster"));
        invalidBoosterDuration = ChatUtil.chatColor(config.getString("messages.invalidBoosterDuration"));
        playerOffline = ChatUtil.chatColor(config.getString("messages.playerOffline"));
        boosterDoesNotExist = ChatUtil.chatColor(config.getString("messages.boosterDoesNotExist"));
        givenBooster = ChatUtil.chatColor(config.getString("messages.givenBooster"));
        userDoesNotExist = ChatUtil.chatColor(config.getString("messages.userDoesNotExist"));
        givenBoosterEveryone = ChatUtil.chatColor(config.getString("messages.givenBoosterEveryone"));
        boosters = ChatUtil.chatColor(config.getString("messages.boosters"));
        haveThisBooster = ChatUtil.chatColor(config.getString("messages.haveThisBooster"));
        boosterExpired = ChatUtil.chatColor(config.getString("messages.boosterExpired"));
        boosterAlreadyExists = ChatUtil.chatColor(config.getString("messages.boosterAlreadyExists"));
    }
}
