package pl.timsixth.boostersaddon;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import pl.timsixth.boostersaddon.command.BoosterSubCommand;
import pl.timsixth.boostersaddon.config.ConfigFile;
import pl.timsixth.boostersaddon.config.Messages;
import pl.timsixth.boostersaddon.config.Settings;
import pl.timsixth.boostersaddon.gui.MenuManager;
import pl.timsixth.boostersaddon.gui.action.ChooseBoosterTypeAction;
import pl.timsixth.boostersaddon.loader.BoosterFileLoader;
import pl.timsixth.boostersaddon.manager.BoosterManager;
import pl.timsixth.boostersaddon.manager.impl.BoosterManagerImpl;
import pl.timsixth.boostersaddon.model.BoosterFileModel;
import pl.timsixth.guilibrary.core.GUIApi;
import pl.timsixth.guilibrary.core.manager.YAMLMenuManager;
import pl.timsixth.guilibrary.processes.ProcessesModule;
import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.command.ParentCommand;
import pl.timsixth.minigameapi.api.loader.Loaders;

@Getter
public class BoostersMiniGameAddon extends JavaPlugin {

    private GUIApi guiApi;
    private Messages messages;
    private Settings settings;
    private YAMLMenuManager menuManager;
    private BoosterManager<BoosterFileModel> boosterManager;
    private BoosterFileLoader boosterFileLoader;
    private ConfigFile configFile;

    @Override
    public void onEnable() {
        this.getConfig().options().copyDefaults(true);
        saveConfig();

        configFile = new ConfigFile(this);
        messages = new Messages(this);
        settings = new Settings(this);
        guiApi = new GUIApi(this);

        registerGuiApi();
        registerLoaders();
        registerManagers();
        registerSubCommands();

        menuManager.load();
    }

    private void registerManagers() {
        boosterManager = new BoosterManagerImpl(boosterFileLoader);
    }

    private void registerGuiApi() {
        menuManager = new MenuManager(guiApi.getActionRegistration(), configFile);

        guiApi.setMenuManager(menuManager);

        guiApi.registerMenuListener();
        guiApi.registerDefaultActions();

        guiApi.getActionRegistration().register(new ChooseBoosterTypeAction());
        guiApi.getModuleManager().registerModule(new ProcessesModule(this));
    }

    private void registerLoaders() {
        boosterFileLoader = new BoosterFileLoader();

        Loaders loaders = new Loaders(MiniGame.getInstance().getDefaultPluginConfiguration());

        loaders.registerLoader(boosterFileLoader);
        loaders.load(boosterFileLoader);
    }

    private void registerSubCommands() {
        ParentCommand adminCommand = MiniGame.getInstance().getAdminCommand();
        adminCommand.addSubCommand(new BoosterSubCommand(messages, settings, menuManager));
    }

}
