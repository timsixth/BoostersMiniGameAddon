package pl.timsixth.boostersaddon.model;

import pl.timsixth.minigameapi.api.model.Model;

import java.util.concurrent.TimeUnit;

public interface TemporaryBooster extends Booster, Model {

    int getTime();

    TimeUnit getTimeUnit();
}
