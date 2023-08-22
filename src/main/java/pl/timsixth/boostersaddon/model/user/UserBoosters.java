package pl.timsixth.boostersaddon.model.user;

import pl.timsixth.minigameapi.api.user.User;

import java.util.List;

public interface UserBoosters extends User {

    List<UserBooster> getActivatedBoosters();

    List<UserBooster> getBoosters();

    int getFullMultiplier();

    void setFullMultiplier(int newMultiplier);

}
