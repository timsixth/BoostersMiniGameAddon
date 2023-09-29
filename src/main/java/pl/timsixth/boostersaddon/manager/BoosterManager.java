package pl.timsixth.boostersaddon.manager;

import pl.timsixth.boostersaddon.model.Booster;

import java.util.List;
import java.util.Optional;

public interface BoosterManager {

    Optional<Booster> getBoosterByName(String name);

    void addBooster(Booster booster);

    List<Booster> getBoosters();
}
