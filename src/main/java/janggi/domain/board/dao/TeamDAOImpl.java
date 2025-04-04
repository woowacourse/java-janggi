package janggi.domain.board.dao;

import janggi.database.utils.DatabaseUtils;
import janggi.domain.setting.CampType;
import java.sql.SQLException;

public class TeamDAOImpl implements TeamDAO {
    private final DatabaseUtils databaseUtils;
    private static final String INSERT_TEAM = "INSERT INTO team(name) values(?)";
    private static final String DROP_TEAM = "DROP TABLE IF EXISTS team";
    private static final int NAME = 1;

    public TeamDAOImpl(final DatabaseUtils databaseUtils) {
        this.databaseUtils = databaseUtils;
    }

    @Override
    public void insertTeam() {
        databaseUtils.executeQuery(INSERT_TEAM, stmt -> {
           try {
               stmt.setString(NAME, CampType.CHO.getName());
               stmt.addBatch();
               stmt.setString(NAME, CampType.HAN.getName());
               stmt.addBatch();

               stmt.executeBatch();
               return null;
           } catch (SQLException e) {
               throw new RuntimeException("[ERROR] TEAM 테이블 insert 에러 발생");
           }
        });
    }

    @Override
    public void dropTeamTable() {
        databaseUtils.executeQuery(DROP_TEAM, stmt -> {
           try {
               stmt.executeUpdate();

               return null;
           } catch (SQLException e) {
               throw new RuntimeException("[ERROR] TEAM 테이블 DROP 에러 발생");
           }
        });
    }
}
