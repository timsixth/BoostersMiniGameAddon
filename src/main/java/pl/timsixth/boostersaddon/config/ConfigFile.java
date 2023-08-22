package pl.timsixth.boostersaddon.config;

import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
@Getter
public class ConfigFile {

    @Getter(AccessLevel.NONE)
    private final Plugin plugin;
    private final File guisFile;
    private final YamlConfiguration ymlGuis;

    public ConfigFile(Plugin plugin) {
        this.plugin = plugin;

        guisFile = createFile("guis.yml");

        ymlGuis = YamlConfiguration.loadConfiguration(guisFile);
    }

    private File createFile(String name) {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }
        File file = new File(plugin.getDataFolder(), name);
        if (!file.exists()) {
            plugin.saveResource(name, true);
        }
        return file;
    }
}
