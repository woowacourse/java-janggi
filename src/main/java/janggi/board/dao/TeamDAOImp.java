package janggi.board.dao;

import janggi.database.DBConnector;
import janggi.setting.CampType;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TeamDAOImp implements TeamDao{
    private final DBConnector dbConnector;
    private static final String INSERT_TEAM = "INSERT INTO team(name) values(?)";

    public TeamDAOImp(final DBConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    @Override
    public void insertTeam() {
        try (final PreparedStatement preparedStatement = dbConnector.getConnection().prepareStatement(INSERT_TEAM)) {
            preparedStatement.setString(1, CampType.CHO.getName());
            preparedStatement.executeUpdate();
            preparedStatement.setString(1, CampType.HAN.getName());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new IllegalArgumentException("[ERROR] team 테이블에 값 추가 중 에러 발생했습니다.");
        }
    }
}
