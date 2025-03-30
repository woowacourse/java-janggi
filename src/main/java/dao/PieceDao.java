package dao;

import dao.dto.CreatePieceDto;
import domain.game.JanggiGame;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PieceDao {

    public void createTable(Connection connection) throws SQLException {
        final var createTableQuery = """                
                CREATE TABLE IF NOT EXISTS piece (
                    id BIGINT PRIMARY KEY AUTO_INCREMENT,
                    x INTEGER NOT NULL,
                    y INTEGER NOT NULL,
                    type VARCHAR(20),
                    team VARCHAR(20),
                    score DOUBLE NOT NULL,
                    janggi_game_id BIGINT,
                    FOREIGN KEY (janggi_game_id) REFERENCES janggi_game (id)
                )
                """;
        try (final var statement = connection.createStatement()) {
            statement.execute(createTableQuery);
        }
    }

    public void createAll(Connection connection, List<CreatePieceDto> createPieceDtos) throws SQLException {
        final var createQuery = """
                INSERT INTO piece (x, y, type, team, score, janggi_game_id) VALUES (?, ?, ?, ?, ?, ?)
                """;
        try (final var prepareStatement = connection.prepareStatement(createQuery)) {
            for (CreatePieceDto createPieceDto : createPieceDtos) {
                prepareStatement.setInt(1, createPieceDto.x());
                prepareStatement.setInt(2, createPieceDto.y());
                prepareStatement.setString(3, createPieceDto.type());
                prepareStatement.setString(4, createPieceDto.team());
                prepareStatement.setDouble(5, createPieceDto.score());
                prepareStatement.setLong(6, createPieceDto.janggiGameId());

                prepareStatement.addBatch();
            }
            prepareStatement.executeBatch();
        }
    }

    public void update(JanggiGame janggiGame) {

    }

    public Optional<JanggiGame> find() {
        return Optional.empty();
    }
}
