package pl.timsixth.boostersaddon.model.user;

import pl.timsixth.minigameapi.api.user.User;

import java.util.List;

public interface UserBoosters extends User {
    List<UserBooster> getBoosters();

    double getFullMultiplier();

    void addBooster(UserBooster userBooster);

    boolean hasBooster(UserBooster booster);
}
