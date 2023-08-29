package pl.timsixth.boostersaddon.gui.process;

import org.bukkit.entity.Player;
import pl.timsixth.boostersaddon.config.Messages;
import pl.timsixth.boostersaddon.config.Settings;
import pl.timsixth.boostersaddon.gui.subprocess.*;
import pl.timsixth.guilibrary.core.manager.YAMLMenuManager;
import pl.timsixth.guilibrary.processes.model.impl.AbstractMainGuiProcess;

public class CreateBoosterProcess extends AbstractMainGuiProcess {

    public CreateBoosterProcess(Settings settings, YAMLMenuManager menuManager, Player player, Messages messages) {
        super("CREATE_BOOSTER");
        getGuiProcesses().add(new GiveBoosterName(settings));
        getGuiProcesses().add(new GiveBoosterDisplayName(settings));
        getGuiProcesses().add(new GiveBoosterMultiplier(settings));
        getGuiProcesses().add(new GiveBoosterType(menuManager));
        getGuiProcesses().add(new GiveBoosterDuration(settings, player, messages));
    }
}
