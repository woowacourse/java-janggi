package repository.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcInitializer {

    private static final String ERROR_INITIALIZE_TABLE = "게임 테이블 초기화에 실패했습니다.";
    private static final String CREATE_GAMES_TABLE = """
            CREATE TABLE IF NOT EXISTS games (
                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                current_turn VARCHAR(10) NOT NULL,
                status VARCHAR(20) NOT NULL,
                created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
                    ON UPDATE CURRENT_TIMESTAMP
            )
            """;
    private static final String CREATE_GAME_PIECES_TABLE = """
            CREATE TABLE IF NOT EXISTS game_pieces (
                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                game_id BIGINT NOT NULL,
                col_no INT NOT NULL,
                row_no INT NOT NULL,
                piece_type VARCHAR(30) NOT NULL,
                team VARCHAR(10) NOT NULL,
                CONSTRAINT uk_game_piece_position UNIQUE (game_id, col_no, row_no),
                CONSTRAINT fk_game_pieces_game
                    FOREIGN KEY (game_id) REFERENCES games(id) ON DELETE CASCADE
            )
            """;

    private final ConnectionProvider connectionProvider;

    public JdbcInitializer(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public void initialize() {
        try (Connection connection = connectionProvider.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(CREATE_GAMES_TABLE);
            statement.execute(CREATE_GAME_PIECES_TABLE);
        } catch (SQLException e) {
            throw new IllegalStateException(ERROR_INITIALIZE_TABLE, e);
        }
    }
}
