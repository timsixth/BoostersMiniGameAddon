package pl.timsixth.boostersaddon.gui.subprocess;

import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.timsixth.boostersaddon.BoostersMiniGameAddon;
import pl.timsixth.boostersaddon.config.Messages;
import pl.timsixth.boostersaddon.config.Settings;
import pl.timsixth.boostersaddon.manager.BoosterManager;
import pl.timsixth.boostersaddon.model.BoosterType;
import pl.timsixth.boostersaddon.model.TemporaryBooster;
import pl.timsixth.boostersaddon.model.impl.TemporaryBoosterImpl;
import pl.timsixth.boostersaddon.util.TextConverter;
import pl.timsixth.guilibrary.processes.manager.ProcessRunner;
import pl.timsixth.guilibrary.processes.model.MainGuiProcess;
import pl.timsixth.guilibrary.processes.model.impl.AbstractSubGuiProcess;
import pl.timsixth.guilibrary.processes.model.input.WriteableInput;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import static pl.timsixth.boostersaddon.util.NumberUtil.isDouble;

public class GiveBoosterDuration extends AbstractSubGuiProcess implements WriteableInput {

    private final Settings settings;
    private final Player player;
    private final Messages messages;

    public GiveBoosterDuration(Settings settings, Player player, Messages messages) {
        super("GIVE_BOOSTER_DURATION");
        this.settings = settings;
        this.player = player;
        this.messages = messages;
    }

    @Override
    public String getInventoryDisplayName() {
        return settings.getGiveBoosterDurationMenuTitle();
    }

    @Override
    public AnvilGUI.Builder getAnvilInput() {
        return new AnvilGUI.Builder()
                .itemOutput(new ItemStack(Material.PAPER))
                .itemLeft(new ItemStack(Material.PAPER))
                .title(settings.getGiveBoosterDurationMenuTitle())
                .onClick((slot, stateSnapshot) -> {
                    if (slot != AnvilGUI.Slot.OUTPUT) {
                        return Collections.emptyList();
                    } else {
                        return Arrays.asList(
                                AnvilGUI.ResponseAction.close(),
                                AnvilGUI.ResponseAction.run(() -> this.setEnded(true)),
                                AnvilGUI.ResponseAction.run(() -> createTemporaryBooster(stateSnapshot.getText()))
                        );
                    }
                })
                .plugin(BoostersMiniGameAddon.getPlugin(BoostersMiniGameAddon.class));
    }

    private void createTemporaryBooster(String duration) {
        BoostersMiniGameAddon miniGameAddon = BoostersMiniGameAddon.getPlugin(BoostersMiniGameAddon.class);
        BoosterManager boosterManager = miniGameAddon.getBoosterManager();
        MainGuiProcess currentUserProcess = ProcessRunner.getCurrentUserProcess(player);

        Map<String, Object> transformedData = this.transformedData();

        String multiplier = String.valueOf(transformedData.get("multiplier"));

        Pattern durationPattern = Pattern.compile("^[0-9]{1,3}[hdm]");

        if (!isDouble(multiplier)) {
            player.sendMessage(messages.getInvalidBoosterMultiplier());
            player.closeInventory();
            return;
        }

        if (!durationPattern.matcher(duration).matches()) {
            player.sendMessage(messages.getInvalidBoosterDuration());
            player.closeInventory();
            return;
        }
        String name = String.valueOf(transformedData.get("name"));

        if (boosterManager.getBoosterByName(name).isPresent()) {
            player.sendMessage(messages.getBoosterAlreadyExists());
            ProcessRunner.endProcess(player, currentUserProcess);
            player.closeInventory();
            return;
        }

        Object[] objects = TextConverter.convertToTime(duration);

        TemporaryBooster boosterFileModel = new TemporaryBoosterImpl(
                name,
                BoosterType.TEMPORARY,
                Double.parseDouble(multiplier),
                String.valueOf(transformedData.get("displayName")),
                (int) objects[0],
                (TimeUnit) objects[1]
        );

        ProcessRunner.endProcess(player, currentUserProcess);
        miniGameAddon.getBoosterManager().addBooster(boosterFileModel);
        boosterFileModel.save();
        player.sendMessage(messages.getCreatedBooster());
    }
}
