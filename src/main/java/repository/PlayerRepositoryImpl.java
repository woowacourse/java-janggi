package repository;

import domain.Player;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PlayerRepositoryImpl implements PlayerRepository {

    private static final String SERVER = "localhost:13306"; // MySQL 서버 주소
    private static final String DATABASE = "janggi"; // MySQL DATABASE 이름
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root"; //  MySQL 서버 아이디
    private static final String PASSWORD = "root"; // MySQL 서버 비밀번호

    @Override
    public void save(final String gameName, final Player player) {
        final String query = "INSERT INTO player (team, name, score) VALUES (?, ?, ?)";
        try (final Connection connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, player.getTeam().name());
            preparedStatement.setString(2, gameName);
            preparedStatement.setInt(3, player.getScore().value());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
