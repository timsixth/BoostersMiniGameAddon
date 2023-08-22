package pl.timsixth.boostersaddon.gui.subprocess;

import org.bukkit.inventory.Inventory;
import pl.timsixth.guilibrary.core.manager.YAMLMenuManager;
import pl.timsixth.guilibrary.core.model.Menu;
import pl.timsixth.guilibrary.processes.model.impl.AbstractSubGuiProcess;

public class GiveBoosterType extends AbstractSubGuiProcess {

    private final YAMLMenuManager menuManager;
    private final Menu menu;

    public GiveBoosterType(YAMLMenuManager menuManager) {
        super("GIVE_BOOSTER_TYPE");
        this.menuManager = menuManager;
        this.menu = menuManager.getMenuByName("boosterTypeMenu").orElseThrow(IllegalStateException::new);
    }

    @Override
    public String getInventoryDisplayName() {
        return menu.getDisplayName();
    }

    @Override
    public Inventory getInventory() {
        return menuManager.createMenu(menu);
    }
}
