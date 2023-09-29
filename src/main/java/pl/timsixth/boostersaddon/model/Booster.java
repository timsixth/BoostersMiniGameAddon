package pl.timsixth.boostersaddon.model;

import pl.timsixth.minigameapi.api.model.Model;

public interface Booster extends Model {

    String getName();

    BoosterType getType();

    double getMultiplier();

    String getDisplayName();
}
