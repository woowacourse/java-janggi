package janggi.domain.board.dao;

import janggi.database.utils.DatabaseUtils;
import janggi.domain.setting.CampType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TurnDAOImpl implements TurnDAO {
    private static final String INSERT_QUERY = "INSERT INTO turn(current_turn) values(?)";
    private static final String SELECT_QUERY = "SELECT current_turn from turn";
    private static final String UPDATE_QUERY = "UPDATE turn SET current_turn = ?";
    private static final String DROP_QUERY = "DROP TABLE IF EXISTS turn";
    private static final int CURRENT_TURN = 1;

    private final DatabaseUtils databaseUtils;

    public TurnDAOImpl(final DatabaseUtils databaseUtils) {
        this.databaseUtils = databaseUtils;
    }

    @Override
    public void insertQuery(final CampType campType) {
        try(final PreparedStatement preparedStatement = databaseUtils.prepareStatement(INSERT_QUERY)) {
            String turnName = campType.getName();
            preparedStatement.setString(CURRENT_TURN, turnName);

            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new IllegalArgumentException("[ERROR] turn 테이블에 레코드를 추가하다가 에러가 발생했습니다.");
        }
    }

    @Override
    public void updateQuery(final CampType campType) {
        try(final PreparedStatement preparedStatement = databaseUtils.prepareStatement(UPDATE_QUERY)) {
            String turnName = campType.getName();
            preparedStatement.setString(CURRENT_TURN, turnName);

            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new IllegalArgumentException("[ERROR[ turn 테이블 레코드를 업데이트하다가 에러가 발생했습니다.");
        }
    }

    @Override
    public CampType selectQuery() {
        try(final PreparedStatement preparedStatement = databaseUtils.prepareStatement(SELECT_QUERY)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new IllegalStateException("[ERROR] turn 테이블에 데이터가 없습니다.");
            }
            return CampType.findCampType(resultSet.getString("current_turn"));
        } catch (final SQLException e) {
            throw new IllegalArgumentException("[ERROR] turn 테이블 레코드를 조회하다가 에러가 발생했습니다.");
        }
    }

    @Override
    public void dropTurnTable() {
        try(final PreparedStatement preparedStatement =  databaseUtils.prepareStatement(DROP_QUERY)) {
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new IllegalArgumentException("[ERROR] turn 테이블 삭제 중 에러가 발생했습니다.");
        }
    }

}
