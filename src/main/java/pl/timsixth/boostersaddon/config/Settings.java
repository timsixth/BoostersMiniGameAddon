package pl.timsixth.boostersaddon.config;

import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import pl.timsixth.boostersaddon.BoostersMiniGameAddon;
import pl.timsixth.guilibrary.core.util.ChatUtil;
@Getter
public final class Settings {

    private String giveBoosterNameMenuTitle;
    private String giveBoosterDisplayNameMenuTitle;
    private String giveBoosterDurationMenuTitle;
    private String giveBoosterMultiplierMenuTitle;

    @Getter(AccessLevel.NONE)
    private final BoostersMiniGameAddon boostersMiniGameAddon;

    public Settings(BoostersMiniGameAddon boostersMiniGameAddon) {
        this.boostersMiniGameAddon = boostersMiniGameAddon;

        loadSettings();
    }

    void loadSettings() {
        FileConfiguration config = boostersMiniGameAddon.getConfig();

        giveBoosterNameMenuTitle = ChatUtil.chatColor(config.getString("giveBoosterNameMenuTitle"));
        giveBoosterDisplayNameMenuTitle = ChatUtil.chatColor(config.getString("giveBoosterDisplayNameMenuTitle"));
        giveBoosterDurationMenuTitle = ChatUtil.chatColor(config.getString("giveBoosterDurationMenuTitle"));
        giveBoosterMultiplierMenuTitle = ChatUtil.chatColor(config.getString("giveBoosterMultiplierMenuTitle"));

    }
}
