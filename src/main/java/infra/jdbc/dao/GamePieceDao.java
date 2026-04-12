package infra.jdbc.dao;

import domain.pieces.PieceType;
import domain.pieces.Side;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import infra.jdbc.SavedPieceDto;

public class GamePieceDao {
    private static final String INSERT_ALL_PIECES_SQL = """
            INSERT INTO game_piece (
                game_id,
                row_index,
                column_index,
                side,
                piece_type
            ) VALUES (?, ?, ?, ?, ?)
            """;

    private static final String FIND_ALL_BY_GAME_ID_SQL = """
            SELECT row_index, column_index, side, piece_type
            FROM game_piece
            WHERE game_id = ?
            ORDER BY row_index, column_index
            """;

    private static final String DELETE_ALL_BY_GAME_ID_SQL = "DELETE FROM game_piece WHERE game_id = ?";

    public void insertAll(Connection connection, long gameId, List<SavedPieceDto> savedPieceDtos) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_ALL_PIECES_SQL)) {
            for (SavedPieceDto savedPieceDto : savedPieceDtos) {
                statement.setLong(1, gameId);
                statement.setInt(2, savedPieceDto.row());
                statement.setInt(3, savedPieceDto.column());
                statement.setString(4, savedPieceDto.side().name());
                statement.setString(5, savedPieceDto.pieceType().name());
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    public List<SavedPieceDto> findAllByGameId(Connection connection, long gameId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_GAME_ID_SQL)) {
            statement.setLong(1, gameId);

            try (ResultSet resultSet = statement.executeQuery()) {
                List<SavedPieceDto> pieces = new ArrayList<>();
                while (resultSet.next()) {
                    pieces.add(new SavedPieceDto(
                            resultSet.getInt("row_index"),
                            resultSet.getInt("column_index"),
                            Side.valueOf(resultSet.getString("side")),
                            PieceType.valueOf(resultSet.getString("piece_type"))
                    ));
                }
                return pieces;
            }
        }
    }

    public void deleteAllByGameId(Connection connection, long gameId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_ALL_BY_GAME_ID_SQL)) {
            statement.setLong(1, gameId);
            statement.executeUpdate();
        }
    }
}
