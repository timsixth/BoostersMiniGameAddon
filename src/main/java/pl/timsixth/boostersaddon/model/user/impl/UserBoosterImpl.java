package pl.timsixth.boostersaddon.model.user.impl;

import lombok.Data;

import pl.timsixth.boostersaddon.model.Booster;
import pl.timsixth.boostersaddon.model.user.UserBooster;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class UserBoosterImpl implements UserBooster {

    private final UUID uuid;
    private final Booster booster;
    private final LocalDateTime startedDate;
    private final LocalDateTime endDate;
    
    @Override
    public boolean isActive() {
        return LocalDateTime.now().equals(endDate);
    }
}
