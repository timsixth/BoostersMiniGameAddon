package pl.timsixth.boostersaddon.manager.impl;

import lombok.RequiredArgsConstructor;
import pl.timsixth.boostersaddon.loader.UserBoostersDBLoader;
import pl.timsixth.boostersaddon.manager.UserBoostersManager;
import pl.timsixth.boostersaddon.model.user.UserBoostersDbModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class UserBoostersManagerImpl implements UserBoostersManager<UserBoostersDbModel> {

    private final UserBoostersDBLoader userBoostersDBLoader;

    @Override
    public Optional<UserBoostersDbModel> getUserByUuid(UUID uuid) {
        return userBoostersDBLoader.getData().stream()
                .filter(userBoostersDbModel -> userBoostersDbModel.getUuid().equals(uuid))
                .findAny();
    }

    @Override
    public List<UserBoostersDbModel> getUsers() {
        return userBoostersDBLoader.getData();
    }

    @Override
    public void addUser(UserBoostersDbModel userBoostersDbModel) {
        userBoostersDBLoader.addObject(userBoostersDbModel);
    }

    @Override
    public void removeUser(UserBoostersDbModel userBoostersDbModel) {
        userBoostersDBLoader.removeObject(userBoostersDbModel);
    }
}
