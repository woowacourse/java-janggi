package infra.jdbc;

import infra.jdbc.exception.JdbcRepositoryException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SchemaInitializer {
    private static final String CREATE_JANGGI_GAME_TABLE_SQL = """
            CREATE TABLE IF NOT EXISTS janggi_game (
                game_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                current_turn VARCHAR(10) NOT NULL,
                status VARCHAR(10) NOT NULL,
                winner VARCHAR(10),
                created_at TIMESTAMP NOT NULL,
                updated_at TIMESTAMP NOT NULL,
                CONSTRAINT chk_janggi_game_current_turn
                    CHECK (current_turn IN ('CHO', 'HAN')),
                CONSTRAINT chk_janggi_game_status
                    CHECK (status IN ('RUNNING', 'ENDED')),
                CONSTRAINT chk_janggi_game_winner
                    CHECK (winner IS NULL OR winner IN ('CHO', 'HAN'))
            )
            """;

    private static final String CREATE_GAME_PIECE_TABLE_SQL = """
            CREATE TABLE IF NOT EXISTS game_piece (
                piece_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                game_id BIGINT NOT NULL,
                row_index INT NOT NULL,
                column_index INT NOT NULL,
                side VARCHAR(10) NOT NULL,
                piece_type VARCHAR(20) NOT NULL,
                CONSTRAINT fk_game_piece_game
                    FOREIGN KEY (game_id) REFERENCES janggi_game(game_id),
                CONSTRAINT uk_game_piece_position
                    UNIQUE (game_id, row_index, column_index),
                CONSTRAINT chk_game_piece_side
                    CHECK (side IN ('CHO', 'HAN')),
                CONSTRAINT chk_game_piece_type
                    CHECK (piece_type IN ('CHA', 'PO', 'MA', 'SANG', 'SA', 'JOL_BYEONG', 'GUNG'))
            )
            """;

    private final JdbcConnectionManager connectionManager;

    public SchemaInitializer(JdbcConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void initialize() {
        try (Connection connection = connectionManager.getConnection()) {
            createJanggiGameTable(connection);
            createGamePieceTable(connection);
        } catch (SQLException e) {
            throw new JdbcRepositoryException("장기 게임 저장소 스키마 초기화에 실패했습니다.", e);
        }
    }

    private void createJanggiGameTable(Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_JANGGI_GAME_TABLE_SQL)) {
            statement.executeUpdate();
        }
    }

    private void createGamePieceTable(Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_GAME_PIECE_TABLE_SQL)) {
            statement.executeUpdate();
        }
    }
}
