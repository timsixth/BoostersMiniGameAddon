package pl.timsixth.boostersaddon.migrations;

import pl.timsixth.databasesapi.DatabasesApiPlugin;
import pl.timsixth.databasesapi.database.ISQLDataBase;
import pl.timsixth.databasesapi.database.migration.ICreationMigration;
import pl.timsixth.databasesapi.database.structure.datatype.VarcharDataType;
import pl.timsixth.minigameapi.api.MiniGame;

public class CreateUserBoostersTable implements ICreationMigration {

    private final ISQLDataBase dataBase = DatabasesApiPlugin.getApi().getCurrentSqlDataBase();

    @Override
    public String getTableName() {
        return MiniGame.getInstance().getDefaultPluginConfiguration().getTablesPrefix() + "user_boosters";
    }

    @Override
    public int getVersion() {
        return 1;
    }

    @Override
    public void up() {
        dataBase.getTableCreator()
                .id()
                .createColumn("uuid", new VarcharDataType(36), false)
                .createTable(getTableName());
    }
}
