package pl.timsixth.boostersaddon.factory;

import pl.timsixth.boostersaddon.model.BoosterFileModel;
import pl.timsixth.boostersaddon.model.BoosterType;
import pl.timsixth.boostersaddon.model.TemporaryBooster;
import pl.timsixth.boostersaddon.model.TemporaryBoosterFileModel;
import pl.timsixth.boostersaddon.model.user.UserBooster;
import pl.timsixth.boostersaddon.model.user.impl.UserBoosterImpl;

import java.time.LocalDateTime;
import java.util.UUID;

public final class UserBoosterFactory {

    public static UserBooster createUserBooster(UUID uuid, BoosterFileModel boosterFileModel) {
        if (boosterFileModel.getType() == BoosterType.TEMPORARY) {
            TemporaryBoosterFileModel booster = (TemporaryBoosterFileModel) boosterFileModel;
            LocalDateTime now = LocalDateTime.now();

            return new UserBoosterImpl(uuid, booster, now, endDate(now, booster));
        }

        return new UserBoosterImpl(uuid, boosterFileModel);
    }

    private static LocalDateTime endDate(LocalDateTime startedDate, TemporaryBooster booster) {
        switch (booster.getTimeUnit()) {
            case MINUTES:
                return startedDate.plusMinutes(booster.getTime());
            case DAYS:
                return startedDate.plusDays(booster.getTime());
            case HOURS:
                return startedDate.plusHours(booster.getTime());
        }
        return LocalDateTime.now();
    }
}
