package pl.timsixth.boostersaddon.model.user;


import pl.timsixth.boostersaddon.model.Booster;

import java.time.LocalDateTime;
import java.util.UUID;

public interface UserBooster  {

    LocalDateTime getStartedDate();

    LocalDateTime getEndDate();

    Booster getBooster();

    boolean isActive();

    UUID getUuid();
}
