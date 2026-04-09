package janggi.config;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class TestConfig {

    private TestConfig() {
    }

    public static void setUp() {
        System.setProperty("db.url", "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        System.setProperty("db.user", "sa");
        System.setProperty("db.password", "");
    }

    public static Long insertGame(String turn, String state) {
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO janggi_game (turn, state) VALUES (?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS
             )) {
            statement.setString(1, turn);
            statement.setString(2, state);
            statement.executeUpdate();

            try (var keys = statement.getGeneratedKeys()) {
                keys.next();
                return keys.getLong(1);
            }
        } catch (Exception e) {
            throw new RuntimeException("테스트용 게임 저장 실패", e);
        }
    }

}
