package config;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;

public final class DatabaseInitializer {

    private static final String CREATE_GAME_TABLE = """
            CREATE TABLE IF NOT EXISTS game (
                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                current_team VARCHAR(20),
                winner VARCHAR(20)
            )
            """;

    private static final String CREATE_PIECE_TABLE = """
            CREATE TABLE IF NOT EXISTS piece (
                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                game_id BIGINT NOT NULL,
                piece_type ENUM('GENERAL', 'GUARD', 'ELEPHANT', 'HORSE', 'CHARIOT', 'CANNON', 'SOLDIER') NOT NULL,
                team ENUM('CHO', 'HAN') NOT NULL,
                position_column INT,
                position_row INT,
                CONSTRAINT fk_piece_game
                    FOREIGN KEY (game_id) REFERENCES game(id)
                    ON DELETE CASCADE
            )
            """;

    public static void initialize(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(CREATE_GAME_TABLE);
            statement.execute(CREATE_PIECE_TABLE);

        } catch (SQLException exception) {
            throw new IllegalStateException("데이터베이스 초기화에 실패했습니다.", exception);
        }
    }
}