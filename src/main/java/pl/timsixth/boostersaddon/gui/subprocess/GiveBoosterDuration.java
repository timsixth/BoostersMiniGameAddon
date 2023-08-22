package pl.timsixth.boostersaddon.gui.subprocess;

import net.wesjd.anvilgui.AnvilGUI;
import pl.timsixth.boostersaddon.config.Settings;
import pl.timsixth.guilibrary.processes.model.impl.AbstractSubGuiProcess;
import pl.timsixth.guilibrary.processes.model.input.WriteableInput;

public class GiveBoosterDuration extends AbstractSubGuiProcess implements WriteableInput {

    private final Settings settings;

    public GiveBoosterDuration(Settings settings) {
        super("GIVE_BOOSTER_DURATION");
        this.settings = settings;
    }

    @Override
    public String getInventoryDisplayName() {
        return settings.getGiveBoosterDurationMenuTitle();
    }

    @Override
    public AnvilGUI.Builder getAnvilInput() {
        return null;
    }
}
