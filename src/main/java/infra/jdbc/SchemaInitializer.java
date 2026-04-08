package infra.jdbc;

import infra.jdbc.exception.JdbcRepositoryException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SchemaInitializer {

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
        String sql = """
                CREATE TABLE IF NOT EXISTS janggi_game (
                    game_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    current_turn VARCHAR(10) NOT NULL,
                    status VARCHAR(10) NOT NULL,
                    winner VARCHAR(10),
                    created_at TIMESTAMP NOT NULL,
                    updated_at TIMESTAMP NOT NULL
                )
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        }
    }

    private void createGamePieceTable(Connection connection) throws SQLException {
        String sql = """
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
                        UNIQUE (game_id, row_index, column_index)
                )
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        }
    }
}
