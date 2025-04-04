package janggi.board.dao;

import janggi.database.utils.DatabaseUtils;
import janggi.setting.CampType;
import java.sql.PreparedStatement;
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
        try (final PreparedStatement preparedStatement = databaseUtils.prepareStatement(INSERT_TEAM)) {
            preparedStatement.setString(NAME, CampType.CHO.getName());
            preparedStatement.addBatch();

            preparedStatement.setString(NAME, CampType.HAN.getName());
            preparedStatement.addBatch();

            preparedStatement.executeBatch();
        } catch (final SQLException e) {
            throw new IllegalArgumentException("[ERROR] team 테이블에 값 추가 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }

    @Override
    public void dropTeamTable() {
        try(final PreparedStatement preparedStatement = databaseUtils.prepareStatement(DROP_TEAM)) {
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new IllegalArgumentException("[ERROR] team 테이블 삭제 중 에러가 발생했습니다.");
        }
    }
}
