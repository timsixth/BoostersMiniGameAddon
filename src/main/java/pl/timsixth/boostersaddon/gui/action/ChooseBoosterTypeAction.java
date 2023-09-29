package pl.timsixth.boostersaddon.gui.action;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import pl.timsixth.boostersaddon.BoostersMiniGameAddon;
import pl.timsixth.boostersaddon.config.Messages;
import pl.timsixth.boostersaddon.manager.BoosterManager;
import pl.timsixth.boostersaddon.model.Booster;
import pl.timsixth.boostersaddon.model.BoosterType;
import pl.timsixth.boostersaddon.model.impl.BoosterImpl;
import pl.timsixth.guilibrary.core.model.MenuItem;
import pl.timsixth.guilibrary.core.model.action.AbstractAction;
import pl.timsixth.guilibrary.core.model.action.click.ClickAction;
import pl.timsixth.guilibrary.processes.manager.ProcessRunner;
import pl.timsixth.guilibrary.processes.model.MainGuiProcess;
import pl.timsixth.guilibrary.processes.model.SubGuiProcess;

import java.util.Map;
import java.util.Optional;

import static pl.timsixth.boostersaddon.util.NumberUtil.isDouble;

public class ChooseBoosterTypeAction extends AbstractAction implements ClickAction {

    private final BoostersMiniGameAddon boostersMiniGameAddon = BoostersMiniGameAddon.getPlugin(BoostersMiniGameAddon.class);

    public ChooseBoosterTypeAction() {
        super("CHOOSE_BOOSTER_TYPE");
    }

    @Override
    public void handleClickEvent(InventoryClickEvent event, MenuItem menuItem) {
        Messages messages = boostersMiniGameAddon.getMessages();
        BoosterType boosterType = BoosterType.valueOf(menuItem.getAction().getArgs().get(0));
        Player player = (Player) event.getWhoClicked();
        MainGuiProcess currentUserProcess = ProcessRunner.getCurrentUserProcess(player);
        BoosterManager boosterManager = boostersMiniGameAddon.getBoosterManager();

        if (currentUserProcess == null) {
            event.setCancelled(true);
            return;
        }

        Optional<SubGuiProcess> subGuiProcessOptional = currentUserProcess.currentProcess();
        if (!subGuiProcessOptional.isPresent()) {
            event.setCancelled(true);
            return;
        }

        SubGuiProcess subGuiProcess = subGuiProcessOptional.get();
        Map<String, Object> transformedData = subGuiProcess.transformedData();

        if (boosterType == BoosterType.TEMPORARY) {

            transformedData.forEach(subGuiProcess::transformData);
            subGuiProcess.setEnded(true);

            ProcessRunner.runSubProcess(player, subGuiProcess.nextProcess());

            event.setCancelled(true);
            return;
        }
        //PERMANENTLY process

        String multiplier = String.valueOf(transformedData.get("multiplier"));

        if (!isDouble(multiplier)) {
            player.sendMessage(messages.getInvalidBoosterMultiplier());
            endProcess(subGuiProcess, player, currentUserProcess);
            return;
        }

        String name = String.valueOf(transformedData.get("name"));

        if (boosterManager.getBoosterByName(name).isPresent()){
            player.sendMessage(messages.getBoosterAlreadyExists());
            endProcess(subGuiProcess, player, currentUserProcess);
            return;
        }

        Booster boosterFileModel = new BoosterImpl(
                name,
                boosterType,
                Double.parseDouble(multiplier),
                String.valueOf(transformedData.get("displayName"))
        );

        boosterManager.addBooster(boosterFileModel);
        boosterFileModel.save();
        player.sendMessage(messages.getCreatedBooster());


        endProcess(subGuiProcess, player, currentUserProcess);
    }

    private void endProcess(SubGuiProcess subGuiProcess, Player player, MainGuiProcess currentUserProcess) {
        subGuiProcess.setEnded(true);
        ProcessRunner.endProcess(player, currentUserProcess);
        player.closeInventory();
    }
}
