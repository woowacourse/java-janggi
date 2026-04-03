package janggi.repository;

import janggi.domain.board.Position;
import janggi.domain.piece.Camp;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public final class GamePieceRepository {

    private static final String SELECT_GAME_PIECES = """
            select row_position, column_position, piece_type, camp
            from game_piece
            where game_id = ?
            """;
    private static final String DELETE_GAME_PIECES = """
            delete from game_piece
            where game_id = ?
            """;
    private static final String INSERT_GAME_PIECE = """
            insert into game_piece (game_id, row_position, column_position, piece_type, camp)
            values (?, ?, ?, ?, ?)
            """;

    public Map<Position, Piece> findByGameId(Connection connection, long gameId) throws SQLException {
        Map<Position, Piece> boardSnapshot = new HashMap<>();
        PreparedStatement statement = connection.prepareStatement(SELECT_GAME_PIECES);
        statement.setLong(1, gameId);

        try (statement; ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                boardSnapshot.put(toPosition(resultSet), toPiece(resultSet));
            }
        }
        return boardSnapshot;
    }

    public void saveGameByBoard(Connection connection, long gameId, Map<Position, Piece> boardSnapshot)
            throws SQLException {
        deleteByGameId(connection, gameId);

        if (boardSnapshot.isEmpty()) {
            return;
        }

        try (PreparedStatement statement = connection.prepareStatement(INSERT_GAME_PIECE)) {
            for (Map.Entry<Position, Piece> entry : boardSnapshot.entrySet()) {
                setPieceStatement(statement, gameId, entry.getKey(), entry.getValue());
            }
            statement.executeBatch();
        }
    }

    public void deleteByGameId(Connection connection, long gameId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_GAME_PIECES)) {
            statement.setLong(1, gameId);
            statement.executeUpdate();
        }
    }

    private Position toPosition(ResultSet resultSet) throws SQLException {
        return new Position(
                resultSet.getInt("row_position"),
                resultSet.getInt("column_position")
        );
    }

    private Piece toPiece(ResultSet resultSet) throws SQLException {
        return new Piece(
                PieceType.valueOf(resultSet.getString("piece_type")),
                Camp.valueOf(resultSet.getString("camp"))
        );
    }

    private void setPieceStatement(PreparedStatement statement, long gameId, Position position, Piece piece)
            throws SQLException {
        statement.setLong(1, gameId);
        statement.setInt(2, position.row());
        statement.setInt(3, position.column());
        statement.setString(4, piece.pieceType().name());
        statement.setString(5, piece.camp().name());
        statement.addBatch();
    }
}
