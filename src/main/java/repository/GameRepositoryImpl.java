package repository;

import domain.Game;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GameRepositoryImpl implements GameRepository {

    private static final String SERVER = "localhost:13306"; // MySQL 서버 주소
    private static final String DATABASE = "janggi"; // MySQL DATABASE 이름
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root"; //  MySQL 서버 아이디
    private static final String PASSWORD = "root"; // MySQL 서버 비밀번호

    @Override
    public void save(final Game game) {
        final String query = "INSERT INTO game (name, status) VALUES (?, ?)";
        try (final Connection connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, game.getName());
            preparedStatement.setString(2, game.getStatus().name());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean hasPlayingGame() {
        final String query = "SELECT COUNT(*) FROM game WHERE status = 'PLAYING'";
        try (final Connection connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query);
             final var resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public List<String> findGameNameAll() {
        final String query = "SELECT name FROM game";
        try (final Connection connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query);
             final var resultSet = preparedStatement.executeQuery()) {
            final List<String> gameNames = new ArrayList<>();
            while (resultSet.next()) {
                gameNames.add(resultSet.getString("name"));
            }
            return gameNames;
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            return null;
        }
    }
}
