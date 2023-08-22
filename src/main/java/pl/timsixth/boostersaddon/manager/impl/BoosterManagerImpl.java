package pl.timsixth.boostersaddon.manager.impl;

import lombok.RequiredArgsConstructor;
import pl.timsixth.boostersaddon.loader.BoosterFileLoader;
import pl.timsixth.boostersaddon.manager.BoosterManager;
import pl.timsixth.boostersaddon.model.BoosterFileModel;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class BoosterManagerImpl implements BoosterManager<BoosterFileModel> {

    private final BoosterFileLoader boosterFileLoader;

    @Override
    public Optional<BoosterFileModel> getBoosterByName(String name) {
        return boosterFileLoader.getData().stream()
                .filter(boosterFileModel -> boosterFileModel.getName().equalsIgnoreCase(name))
                .findAny();
    }

    @Override
    public void addBooster(BoosterFileModel booster) {
        boosterFileLoader.addObject(booster);
    }

    @Override
    public void removeBooster(BoosterFileModel booster) {
        boosterFileLoader.removeObject(booster);
    }

    @Override
    public List<BoosterFileModel> getBoosters() {
        return boosterFileLoader.getData();
    }
}
