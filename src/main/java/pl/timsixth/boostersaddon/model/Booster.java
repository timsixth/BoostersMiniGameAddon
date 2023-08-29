package pl.timsixth.boostersaddon.model;

public interface Booster {

    String getName();

    BoosterType getType();

    double getMultiplier();

    String getDisplayName();
}
