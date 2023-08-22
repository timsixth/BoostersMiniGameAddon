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
    private String invalidBoosterMultiplier;
    private String createdBooster;

    public Messages(BoostersMiniGameAddon boostersMiniGameAddon) {
        this.boostersMiniGameAddon = boostersMiniGameAddon;

        loadMessages();
    }

    void loadMessages() {
        FileConfiguration config = boostersMiniGameAddon.getConfig();

        adminHelp = Replacer.replaceCommandNames(ChatUtil.chatColor(config.getStringList("messages.adminHelp")));
        invalidBoosterMultiplier = ChatUtil.chatColor(config.getString("messages.invalidBoosterMultiplier"));
        createdBooster = ChatUtil.chatColor(config.getString("messages.createdBooster"));
    }
}
