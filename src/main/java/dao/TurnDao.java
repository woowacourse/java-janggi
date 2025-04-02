package dao;

import java.sql.SQLException;

public class TurnDao {
    public int readTurnDB() {
        final var query = "SELECT turn FROM Turn";

        try (final var connection = Connector.getConnection()) {
            try (final var statement = connection.createStatement();
                 final var resultSet = statement.executeQuery(query)) {

                if (resultSet.next()) {
                    return resultSet.getInt("turn");
                }
                throw new SQLException("[ERROR] Turn 테이블에서 데이터를 찾을 수 없습니다.");
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void resetTurnDB() {
        final var query = "UPDATE Turn SET turn = 0";

        try (final var connection = Connector.getConnection()) {
            try (final var statement = connection.createStatement()) {
                int rowsAffected = statement.executeUpdate(query);
                if (rowsAffected == 0) {
                    throw new SQLException("[ERROR] Turn 테이블에서 값을 리셋할 수 없습니다.");
                }
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void incrementTurnDB() {
        final var query = "UPDATE Turn SET turn = turn + 1";

        try (final var connection = Connector.getConnection()) {
            try (final var statement = connection.createStatement()) {
                int rowsAffected = statement.executeUpdate(query);
                if (rowsAffected == 0) {
                    throw new SQLException("[ERROR] Turn 테이블에서 값을 증가시킬 수 없습니다.");
                }
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
