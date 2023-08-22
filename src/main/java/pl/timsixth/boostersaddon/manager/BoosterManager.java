package pl.timsixth.boostersaddon.manager;

import pl.timsixth.boostersaddon.model.Booster;

import java.util.List;
import java.util.Optional;

public interface BoosterManager<T extends Booster> {

    Optional<T> getBoosterByName(String name);

    void addBooster(T booster);

    void removeBooster(T booster);

    List<T> getBoosters();
}
