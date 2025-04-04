package persistence.dao;

import domain.board.BoardLocation;
import persistence.entity.PieceEntity;
import domain.piece.PieceType;
import domain.piece.Score;
import domain.piece.Team;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PieceDao {

    public void createAll(Connection connection, List<PieceEntity> pieceEntities) throws SQLException {
        final var createQuery = """
                INSERT INTO piece (column_index, row_index, type, team, score, janggi_game_id) VALUES (?, ?, ?, ?, ?, ?)
                """;
        try (final var prepareStatement = connection.prepareStatement(createQuery)) {
            for (PieceEntity pieceEntity : pieceEntities) {
                prepareStatement.setInt(1, pieceEntity.column());
                prepareStatement.setInt(2, pieceEntity.row());
                prepareStatement.setString(3, pieceEntity.type().name());
                prepareStatement.setString(4, pieceEntity.team().name());
                prepareStatement.setDouble(5, pieceEntity.score().score());
                prepareStatement.setLong(6, pieceEntity.janggiGameId());

                prepareStatement.addBatch();
            }
            int[] rowsCreated = prepareStatement.executeBatch();
            for (int rowCreated : rowsCreated) {
                if (rowCreated == 0) {
                    throw new SQLException("[ERROR] Piece 생성에 실패하였습니다!");
                }
            }
        }
    }

    public void update(Connection connection, BoardLocation originLocation, BoardLocation updateLocation) throws SQLException {
        final var updateQuery = """
                UPDATE piece SET column_index = ?, row_index = ? WHERE column_index = ? AND row_index = ?;
                """;
        try (final var preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setInt(1, updateLocation.column());
            preparedStatement.setInt(2, updateLocation.row());
            preparedStatement.setInt(3, originLocation.column());
            preparedStatement.setInt(4, originLocation.row());
            int rowUpdated = preparedStatement.executeUpdate();

            if (rowUpdated == 0) {
                throw new SQLException("[ERROR] Piece 수정에 실패하였습니다");
            }
        }
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
                            resultSet.getInt("column_index"),
                            resultSet.getInt("row_index"),
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
