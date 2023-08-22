package pl.timsixth.boostersaddon.gui.subprocess;

import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import pl.timsixth.boostersaddon.BoostersMiniGameAddon;
import pl.timsixth.boostersaddon.config.Settings;
import pl.timsixth.guilibrary.processes.manager.ProcessRunner;
import pl.timsixth.guilibrary.processes.model.impl.AbstractSubGuiProcess;
import pl.timsixth.guilibrary.processes.model.input.WriteableInput;

import java.util.Arrays;
import java.util.Collections;

public class GiveBoosterMultiplier extends AbstractSubGuiProcess implements WriteableInput {

    private final Settings settings;

    public GiveBoosterMultiplier(Settings settings) {
        super("GIVE_BOOSTER_MULTIPLIER");
        this.settings = settings;
    }

    @Override
    public String getInventoryDisplayName() {
        return settings.getGiveBoosterMultiplierMenuTitle();
    }

    @Override
    public AnvilGUI.Builder getAnvilInput() {
        return new AnvilGUI.Builder()
                .itemOutput(new ItemStack(Material.PAPER))
                .itemLeft(new ItemStack(Material.PAPER))
                .title(settings.getGiveBoosterMultiplierMenuTitle())
                .onClick((slot, stateSnapshot) -> {
                    if (slot != AnvilGUI.Slot.OUTPUT) {
                        return Collections.emptyList();
                    } else {
                        return Arrays.asList(
                                AnvilGUI.ResponseAction.close(),
                                AnvilGUI.ResponseAction.run(() -> this.transformData("name", this.getDatum("name"))),
                                AnvilGUI.ResponseAction.run(() -> this.transformData("displayName", this.getDatum("displayName"))),
                                AnvilGUI.ResponseAction.run(() -> this.transformData("multiplier", stateSnapshot.getText())),
                                AnvilGUI.ResponseAction.run(() -> this.setEnded(true)),
                                AnvilGUI.ResponseAction.run(() -> ProcessRunner.runSubProcess(stateSnapshot.getPlayer(), this.nextProcess()))
                        );
                    }
                })
                .plugin(BoostersMiniGameAddon.getPlugin(BoostersMiniGameAddon.class))
                .preventClose();
    }
}
