package pl.timsixth.boostersaddon.loader;

import lombok.RequiredArgsConstructor;
import pl.timsixth.boostersaddon.manager.BoosterManager;
import pl.timsixth.boostersaddon.model.Booster;
import pl.timsixth.boostersaddon.model.user.UserBooster;
import pl.timsixth.boostersaddon.model.user.impl.UserBoosterImpl;
import pl.timsixth.databasesapi.DatabasesApiPlugin;
import pl.timsixth.databasesapi.database.ISQLDataBase;
import pl.timsixth.databasesapi.database.query.QueryBuilder;
import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.loader.database.AbstractSqlDataBaseLoader;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
public class UserBoosterDBLoader extends AbstractSqlDataBaseLoader<UserBooster> {

    private final ISQLDataBase dataBase = DatabasesApiPlugin.getApi().getCurrentSqlDataBase();
    private final BoosterManager boosterManager;

    @Override
    public void load(String tableName) {
        QueryBuilder queryBuilder = new QueryBuilder();

        String query = queryBuilder.selectAll(tableName).build();

        try (ResultSet resultSet = dataBase.getAsyncQuery().query(query)) {
            while (resultSet.next()) {

                Optional<Booster> boosterOptional = boosterManager.getBoosterByName(resultSet.getString("boosterName"));
                if (!boosterOptional.isPresent()) continue;

                UserBooster userBoosterDbModel = new UserBoosterImpl(
                        UUID.fromString(resultSet.getString("uuid")),
                        boosterOptional.get(),
                        LocalDateTime.parse(resultSet.getString("startedDate").replace(" ", "T"), DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                );

                if (resultSet.getString("endDate") != null) {
                    userBoosterDbModel = new UserBoosterImpl(
                            UUID.fromString(resultSet.getString("uuid")),
                            boosterOptional.get(),
                            LocalDateTime.parse(resultSet.getString("startedDate").replace(" ", "T"), DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                            LocalDateTime.parse(resultSet.getString("endDate").replace(" ", "T"), DateTimeFormatter.ISO_LOCAL_DATE_TIME)

                    );
                }
                this.addObject(userBoosterDbModel);
            }
        } catch (ExecutionException | InterruptedException | SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void load() {
        load(MiniGame.getInstance().getDefaultPluginConfiguration().getTablesPrefix() + "user_booster");
    }
}
