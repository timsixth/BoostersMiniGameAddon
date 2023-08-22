package pl.timsixth.boostersaddon.manager;

import pl.timsixth.boostersaddon.model.user.UserBoosters;
import pl.timsixth.minigameapi.api.user.UserManager;

import java.util.List;

public interface UserBoostersManager<T extends UserBoosters> extends UserManager<T> {

    List<T> getUsersBoosters();

    void addUserBoosters(T userBoosters);

    void removeUserBoosters(T userBoosters);
}
