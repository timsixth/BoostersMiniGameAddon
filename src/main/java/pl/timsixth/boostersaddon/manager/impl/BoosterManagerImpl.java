package pl.timsixth.boostersaddon.manager.impl;

import lombok.RequiredArgsConstructor;
import pl.timsixth.boostersaddon.loader.BoosterFileLoader;
import pl.timsixth.boostersaddon.manager.BoosterManager;
import pl.timsixth.boostersaddon.model.Booster;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class BoosterManagerImpl implements BoosterManager {

    private final BoosterFileLoader boosterFileLoader;

    @Override
    public Optional<Booster> getBoosterByName(String name) {
        return boosterFileLoader.getData().stream()
                .filter(boosterFileModel -> boosterFileModel.getName().equalsIgnoreCase(name))
                .findAny();
    }

    @Override
    public void addBooster(Booster booster) {
        boosterFileLoader.addObject(booster);
    }

    @Override
    public List<Booster> getBoosters() {
        return boosterFileLoader.getData();
    }
}
