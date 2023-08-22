package pl.timsixth.boostersaddon.loader;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.timsixth.boostersaddon.model.impl.BoosterImpl;
import pl.timsixth.boostersaddon.model.impl.TemporaryBoosterImpl;
import pl.timsixth.boostersaddon.model.BoosterFileModel;
import pl.timsixth.boostersaddon.model.BoosterType;
import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.loader.file.AbstractFileLoader;
import pl.timsixth.minigameapi.api.util.ConfigurationSectionUtil;

import java.io.File;

public class BoosterFileLoader extends AbstractFileLoader<BoosterFileModel> {
    @Override
    public void load() {
        load("boosters.yml", "boosters");
    }

    @Override
    public void load(String fileName, String primarySection) {
        File file = new File(MiniGame.getInstance().getDataFolder(), fileName);
        if (!file.exists()) {
            Bukkit.getLogger().warning("File " + fileName + " does not exists!");
            return;
        }

        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

        ConfigurationSection section = ConfigurationSectionUtil.getSection(yamlConfiguration, primarySection);

        for (String key : section.getKeys(false)) {
            ConfigurationSection nameSection = ConfigurationSectionUtil.getSection(yamlConfiguration, key);

            BoosterType boosterType = BoosterType.valueOf(nameSection.getString("boosterType"));

            if (boosterType == BoosterType.TEMPORARY) this.addObject(section.getObject(key, BoosterImpl.class));
            else this.addObject(section.getObject(key, TemporaryBoosterImpl.class));
        }
    }
}
