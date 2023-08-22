package pl.timsixth.boostersaddon.model.impl;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import pl.timsixth.boostersaddon.model.BoosterFileModel;
import pl.timsixth.boostersaddon.model.BoosterType;
import pl.timsixth.minigameapi.api.file.SingleFileModel;
import pl.timsixth.minigameapi.api.file.annotaions.IdSection;
import pl.timsixth.minigameapi.api.file.annotaions.SingleFile;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
@SingleFile(fileName = "boosters.yml", primarySection = "boosters")
public class BoosterImpl extends SingleFileModel implements BoosterFileModel {

    @IdSection
    private final String name;
    private final BoosterType type;
    private final double multiplier;
    private String displayName;

    public BoosterImpl(String name, BoosterType type, double multiplier, String displayName) {
        this.name = name;
        this.type = type;
        this.multiplier = multiplier;
        this.displayName = displayName;
        init();
    }

    @Override
    @NonNull
    public Map<String, Object> serialize() {
        Map<String, Object> data = new LinkedHashMap<>();

        data.put("name", name);
        data.put("boosterType", type.name());
        data.put("multiplier", multiplier);
        data.put("displayName", displayName);

        return data;
    }

    public static BoosterImpl deserialize(Map<String, Object> args) {
        return new BoosterImpl(String.valueOf(args.get("name")),
                BoosterType.valueOf(String.valueOf(args.get("boosterType"))),
                (double) args.get("multiplier"), String.valueOf(args.get("displayName"))
        );
    }
}
