package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Team;

public class GameDao {

    private static final String SERVER = "localhost:13306";
    private static final String DATABASE = "janggi";
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public Connection getConnection() {
        // 드라이버 연결
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION,
                USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /***
     * 지금 시점에서의 게임은 단일 게임만 존재함.
     * 그렇기에 game_id 의 default 값을 1로 고정시켜놨음
     * 추후 여러 게임을 진행하도록 수정된다면, 변경이 필요함
     */
    public void addTurn(Team turn) {
        final var query = "INSERT INTO GAME(turn) VALUES (?)";
        try (final var connection = getConnection();
            final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, turn.name());
            preparedStatement.execute();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteTurn() {
        final var query = "DELETE FROM GAME";
        try (final var connection = getConnection();
            final var preparedStatement = connection.prepareStatement(query)) {
            return preparedStatement.execute();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateTurn(Team turn) {
        final var query = "UPDATE GAME SET turn = ? WHERE game_id = 1";
        try (final var connection = getConnection();
            final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, turn.name());
            return preparedStatement.execute();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }



    /***
     * 현재 상황에서는 단일 게임만 존재함
     * 그렇기에 game_id를 1로 고정 함.
     * 추후 다른 게임이 추가된다면 해당 코드의 game_id = 1 부분을 수정해야 함.
     */
    public Team getTurn() {
        final var query = "SELECT turn FROM GAME WHERE game_id = ?";
        try (final var connection = getConnection();
            final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, 1); // 현 시점에서는 game_id를 1로 고정한다.
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String turn = resultSet.getString("turn");
                return Team.getTeamFromString(turn);
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
