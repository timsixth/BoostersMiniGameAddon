package pl.timsixth.boostersaddon.gui;

import pl.timsixth.boostersaddon.config.ConfigFile;
import pl.timsixth.guilibrary.core.manager.YAMLMenuManager;
import pl.timsixth.guilibrary.core.manager.registration.ActionRegistration;

public class MenuManager extends YAMLMenuManager {

    private final ConfigFile configFile;

    public MenuManager(ActionRegistration actionRegistration, ConfigFile configFile) {
        super(actionRegistration);
        this.configFile = configFile;
    }
    @Override
    public void load() {
        load(configFile.getYmlGuis());
    }
}
