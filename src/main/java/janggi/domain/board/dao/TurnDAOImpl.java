package janggi.domain.board.dao;

import janggi.database.utils.DatabaseUtils;
import janggi.domain.setting.CampType;
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
        databaseUtils.executeQuery(INSERT_QUERY, stmt -> {
            try {
                String turnName = campType.getName();
                stmt.setString(CURRENT_TURN, turnName);

                stmt.executeUpdate();
                return null;
            } catch (SQLException e) {
                throw new RuntimeException("[ERROR] TURN INSERT 쿼리 에러 발생");
            }
        });
    }

    @Override
    public void updateQuery(final CampType campType) {
        databaseUtils.executeQuery(UPDATE_QUERY, stmt -> {
           try {
               String turnName = campType.getName();
               stmt.setString(CURRENT_TURN, turnName);
               stmt.executeUpdate();
               return null;
           } catch (SQLException e) {
               throw new RuntimeException("[ERROR] TURN UPDATE 쿼리 에러 발생");
           }
        });
    }

    @Override
    public CampType selectQuery() {
        return databaseUtils.executeQuery(SELECT_QUERY, stmt -> {
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (!resultSet.next()) {
                    throw new IllegalStateException("[ERROR] turn 테이블에 데이터가 없습니다.");
                }
                return CampType.findCampType(resultSet.getString("current_turn"));
            } catch (SQLException e) {
                throw new IllegalArgumentException("[ERROR] turn 테이블 레코드를 조회하다가 에러가 발생했습니다.");
            }
        });
    }


    @Override
    public void dropTurnTable() {
        databaseUtils.executeQuery(DROP_QUERY, stmt -> {
           try {
               stmt.executeUpdate();
               return null;
           } catch (SQLException e) {
               throw new RuntimeException("[ERROR] TURN DROP 쿼리 에러 발생");
           }
        });
    }

}
