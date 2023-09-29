package pl.timsixth.boostersaddon.manager.impl;

import lombok.RequiredArgsConstructor;
import pl.timsixth.boostersaddon.loader.UserBoostersDBLoader;
import pl.timsixth.boostersaddon.manager.UserBoostersManager;
import pl.timsixth.boostersaddon.model.user.UserBoosters;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class UserBoostersManagerImpl implements UserBoostersManager {

    private final UserBoostersDBLoader userBoostersDBLoader;

    @Override
    public Optional<UserBoosters> getUserByUuid(UUID uuid) {
        return userBoostersDBLoader.getData().stream()
                .filter(userBoostersDbModel -> userBoostersDbModel.getUuid().equals(uuid))
                .findAny();
    }

    @Override
    public List<UserBoosters> getUsers() {
        return userBoostersDBLoader.getData();
    }

    @Override
    public void addUser(UserBoosters userBoostersDbModel) {
        userBoostersDBLoader.addObject(userBoostersDbModel);
    }

    @Override
    public void removeUser(UserBoosters userBoostersDbModel) {
        userBoostersDBLoader.removeObject(userBoostersDbModel);
    }
}
