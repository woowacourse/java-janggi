package dao;

import dao.dto.CreatePieceDto;
import domain.entity.PieceEntity;
import domain.game.JanggiGame;
import domain.piece.PieceType;
import domain.piece.Score;
import domain.piece.Team;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public List<PieceEntity> findAllByJanggiGameId(Connection connection, Long id) throws SQLException {
        final var findByIdQuery = "SELECT * FROM piece WHERE janggi_game_id = ?";
        List<PieceEntity> pieces = new ArrayList<>();

        try (final var preparedStatement = connection.prepareStatement(findByIdQuery)) {
            preparedStatement.setLong(1, id);

            try (final var resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    PieceEntity piece = new PieceEntity(
                            resultSet.getLong("id"),
                            resultSet.getInt("x"),
                            resultSet.getInt("y"),
                            PieceType.valueOf(resultSet.getString("type")),
                            Team.valueOf(resultSet.getString("team")),
                            new Score(resultSet.getDouble("score")),
                            resultSet.getLong("janggi_game_id")
                    );
                    pieces.add(piece);
                }
            }
        }
        return pieces;
    }
}
