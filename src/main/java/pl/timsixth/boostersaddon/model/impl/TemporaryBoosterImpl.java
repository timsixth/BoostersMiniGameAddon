package pl.timsixth.boostersaddon.model.impl;

import lombok.Getter;
import lombok.NonNull;
import pl.timsixth.boostersaddon.model.BoosterType;
import pl.timsixth.boostersaddon.model.TemporaryBoosterFileModel;
import pl.timsixth.minigameapi.api.file.annotaions.SingleFile;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Getter
@SingleFile(fileName = "boosters.yml", primarySection = "boosters")
public class TemporaryBoosterImpl extends BoosterImpl implements TemporaryBoosterFileModel {

    private final int time;
    private final TimeUnit timeUnit;

    public TemporaryBoosterImpl(String name, BoosterType type, double multiplier, String displayName, int time, TimeUnit timeUnit) {
        super(name, type, multiplier, displayName);
        this.time = time;
        this.timeUnit = timeUnit;
        init();
    }

    @Override
    @NonNull
    public Map<String, Object> serialize() {
        Map<String, Object> data = new LinkedHashMap<>();

        data.put("name", getName());
        data.put("boosterType", getType().name());
        data.put("multiplier", getMultiplier());
        data.put("displayName", getDisplayName());
        data.put("time", time);
        data.put("timeUnit", timeUnit.name());

        return data;
    }

    public static TemporaryBoosterImpl deserialize(Map<String, Object> args) {
        return new TemporaryBoosterImpl(String.valueOf(args.get("name")),
                BoosterType.valueOf(String.valueOf(args.get("boosterType"))),
                (double) args.get("multiplier"), String.valueOf(args.get("displayName")),
                (int) args.get("time"), TimeUnit.valueOf(String.valueOf(args.get("timeUnit")))
        );
    }
}
