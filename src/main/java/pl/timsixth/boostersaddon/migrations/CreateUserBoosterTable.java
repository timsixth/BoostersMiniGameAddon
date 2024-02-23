package pl.timsixth.boostersaddon.migrations;

import pl.timsixth.databasesapi.DatabasesApiPlugin;
import pl.timsixth.databasesapi.database.ISQLDataBase;
import pl.timsixth.databasesapi.database.migration.ICreationMigration;
import pl.timsixth.databasesapi.database.structure.datatype.DataTypes;
import pl.timsixth.databasesapi.database.structure.datatype.VarcharDataType;
import pl.timsixth.minigameapi.api.MiniGame;

public class CreateUserBoosterTable implements ICreationMigration {

    private final ISQLDataBase dataBase = DatabasesApiPlugin.getApi().getCurrentSqlDataBase();

    @Override
    public String getTableName() {
        return MiniGame.getInstance().getPluginConfiguration().getTablesPrefix() + "user_booster";
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
                .createColumn("boosterName", new VarcharDataType(30), false)
                .createColumn("startedDate", DataTypes.DATETIME, false)
                .createColumn("endDate", DataTypes.DATETIME, true)
                .createTable(getTableName());
    }
}
