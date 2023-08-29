package pl.timsixth.boostersaddon.model;

import java.util.concurrent.TimeUnit;

public interface TemporaryBooster extends Booster {

    int getTime();

    TimeUnit getTimeUnit();
}
