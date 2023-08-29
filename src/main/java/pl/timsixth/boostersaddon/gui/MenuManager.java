package pl.timsixth.boostersaddon.gui;

import org.bukkit.entity.Player;
import pl.timsixth.boostersaddon.config.ConfigFile;
import pl.timsixth.guilibrary.core.manager.YAMLMenuManager;
import pl.timsixth.guilibrary.core.manager.registration.ActionRegistration;
import pl.timsixth.guilibrary.core.model.Menu;
import pl.timsixth.guilibrary.core.model.MenuItem;

import java.util.List;
import java.util.Set;

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


    public <T extends Generable> void openGeneratedMenu(Player player, Menu menu, List<T> items) {
        if (items.isEmpty()) {
            player.openInventory(createMenu(menu));
            return;
        }

        Set<MenuItem> menuItems = menu.getItems();
        menuItems.clear();
        for (int i = 0; i < items.size(); i++) {
            menuItems.add(items.get(i).getGeneratedItem(i));
        }
        menu.setItems(menuItems);
        player.openInventory(createMenu(menu));
    }
}
