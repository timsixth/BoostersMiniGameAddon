package pl.timsixth.boostersaddon.model.user;

import pl.timsixth.boostersaddon.gui.Generable;
import pl.timsixth.boostersaddon.model.Booster;

import java.time.LocalDateTime;
import java.util.UUID;

public interface UserBooster extends Generable {
    LocalDateTime getEndDate();

    Booster getBooster();

    UUID getUuid();
}
