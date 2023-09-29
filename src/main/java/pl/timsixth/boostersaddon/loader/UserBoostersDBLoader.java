package pl.timsixth.boostersaddon.loader;

import lombok.RequiredArgsConstructor;
import pl.timsixth.boostersaddon.model.user.UserBooster;
import pl.timsixth.boostersaddon.model.user.UserBoosters;
import pl.timsixth.boostersaddon.model.user.impl.UserBoostersImpl;
import pl.timsixth.databasesapi.DatabasesApiPlugin;
import pl.timsixth.databasesapi.database.ISQLDataBase;
import pl.timsixth.databasesapi.database.query.QueryBuilder;
import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.loader.database.AbstractSqlDataBaseLoader;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class UserBoostersDBLoader extends AbstractSqlDataBaseLoader<UserBoosters> {

    private final UserBoosterDBLoader userBoosterDbLoader;
    private final ISQLDataBase dataBase = DatabasesApiPlugin.getApi().getCurrentSqlDataBase();
    @Override
    public void load(String tableName) {
        QueryBuilder queryBuilder = new QueryBuilder();

        String query = queryBuilder.selectAll(tableName).build();

        try(ResultSet resultSet = dataBase.getAsyncQuery().query(query)) {
            while(resultSet.next()) {

                UUID uuid = UUID.fromString(resultSet.getString("uuid"));
                List<UserBooster> boosters = getBoosters(uuid);

                UserBoosters userBoostersDbModel = new UserBoostersImpl(uuid,boosters);

                this.addObject(userBoostersDbModel);
            }
        } catch (ExecutionException | InterruptedException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void load() {
        load(MiniGame.getInstance().getDefaultPluginConfiguration().getTablesPrefix() + "user_boosters");
    }


    private List<UserBooster> getBoosters(UUID uuid){
        return userBoosterDbLoader.getData()
                .stream()
                .filter(userBoosterDbModel -> userBoosterDbModel.getUuid().equals(uuid))
                .collect(Collectors.toList());
    }
}
