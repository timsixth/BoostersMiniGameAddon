package pl.timsixth.boostersaddon;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;
import pl.timsixth.boostersaddon.config.ConfigFile;
import pl.timsixth.boostersaddon.config.Messages;
import pl.timsixth.boostersaddon.config.Settings;
import pl.timsixth.boostersaddon.gui.MenuManager;
import pl.timsixth.boostersaddon.gui.action.ChooseBoosterTypeAction;
import pl.timsixth.boostersaddon.listener.PlayerWinGameListener;
import pl.timsixth.boostersaddon.loader.BoosterFileLoader;
import pl.timsixth.boostersaddon.loader.UserBoosterDBLoader;
import pl.timsixth.boostersaddon.loader.UserBoostersDBLoader;
import pl.timsixth.boostersaddon.manager.BoosterManager;
import pl.timsixth.boostersaddon.manager.UserBoostersManager;
import pl.timsixth.boostersaddon.manager.impl.BoosterManagerImpl;
import pl.timsixth.boostersaddon.manager.impl.UserBoostersManagerImpl;
import pl.timsixth.boostersaddon.migrations.CreateUserBoosterTable;
import pl.timsixth.boostersaddon.migrations.CreateUserBoostersTable;
import pl.timsixth.boostersaddon.model.impl.BoosterImpl;
import pl.timsixth.boostersaddon.model.impl.TemporaryBoosterImpl;
import pl.timsixth.boostersaddon.subcommand.AdminBoosterSubCommand;
import pl.timsixth.boostersaddon.subcommand.PlayerBoosterSubCommand;
import pl.timsixth.boostersaddon.timer.UserBoostersTimer;
import pl.timsixth.databasesapi.DatabasesApiPlugin;
import pl.timsixth.databasesapi.api.IDataBasesApi;
import pl.timsixth.databasesapi.database.migration.Migrations;
import pl.timsixth.guilibrary.core.GUIApi;
import pl.timsixth.guilibrary.core.manager.YAMLMenuManager;
import pl.timsixth.guilibrary.processes.ProcessesModule;
import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.command.ParentCommand;
import pl.timsixth.minigameapi.api.loader.Loaders;

import java.util.Arrays;

@Getter
public class BoostersMiniGameAddon extends JavaPlugin {

    private GUIApi guiApi;
    private Messages messages;
    private Settings settings;
    private YAMLMenuManager menuManager;
    private BoosterManager boosterManager;
    private UserBoostersManager userBoostersManager;
    private BoosterFileLoader boosterFileLoader;
    private UserBoostersDBLoader userBoostersDBLoader;
    private ConfigFile configFile;
    private IDataBasesApi dataBasesApi;

    @Override
    public void onEnable() {
        this.getConfig().options().copyDefaults(true);
        saveConfig();

        configFile = new ConfigFile(this);
        messages = new Messages(this);
        settings = new Settings(this);

        guiApi = new GUIApi(this);
        dataBasesApi = DatabasesApiPlugin.getApi();

        ConfigurationSerialization.registerClass(BoosterImpl.class);
        ConfigurationSerialization.registerClass(TemporaryBoosterImpl.class);

        registerGuiApi();
        initMigrations();
        registerLoaders();
        registerManagers();
        registerSubCommands();

        Bukkit.getPluginManager().registerEvents(new PlayerWinGameListener(MiniGame.getInstance().getUserCoinsManager(), userBoostersManager), this);

        menuManager.load();

        new UserBoostersTimer(userBoostersManager, messages).runTaskTimer(this, 20L, 20L);
    }

    private void registerManagers() {
        userBoostersManager = new UserBoostersManagerImpl(userBoostersDBLoader);
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
        boosterManager = new BoosterManagerImpl(boosterFileLoader);
        UserBoosterDBLoader userBoosterDbLoader = new UserBoosterDBLoader(boosterManager);
        userBoostersDBLoader = new UserBoostersDBLoader(userBoosterDbLoader);

        Loaders loaders = new Loaders(MiniGame.getInstance().getPluginConfiguration());

        loaders.registerLoaders(boosterFileLoader, userBoosterDbLoader, userBoostersDBLoader);
        loaders.load(boosterFileLoader, userBoosterDbLoader, userBoostersDBLoader);
    }

    private void registerSubCommands() {
        ParentCommand adminCommand = MiniGame.getInstance().getAdminCommand();
        ParentCommand playerCommand = MiniGame.getInstance().getPlayerCommand();

        adminCommand.addSubCommand(new AdminBoosterSubCommand(messages, settings, menuManager, boosterManager, userBoostersManager));
        playerCommand.addSubCommand(new PlayerBoosterSubCommand(messages, userBoostersManager, (MenuManager) menuManager));
    }

    private void initMigrations() {
        Migrations migrations = dataBasesApi.getMigrations();

        CreateUserBoostersTable createUserBoostersTable = new CreateUserBoostersTable();
        CreateUserBoosterTable createUserBoosterTable = new CreateUserBoosterTable();

        migrations.addMigrations(Arrays.asList(createUserBoostersTable, createUserBoosterTable));

        migrations.migrate(createUserBoostersTable);
        migrations.migrate(createUserBoosterTable);
    }
}
