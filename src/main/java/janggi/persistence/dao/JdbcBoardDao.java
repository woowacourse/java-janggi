package janggi.persistence.dao;

import janggi.domain.board.Board;
import janggi.domain.board.Position;
import janggi.domain.game.Side;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.dto.PiecePositionSnapshot;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcBoardDao implements BoardDao {

    @Override
    public void deleteByGameId(Connection connection, long gameId) throws SQLException {
        String sql = "delete from board where game_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, gameId);
            statement.executeUpdate();
        }
    }

    @Override
    public void insertAll(Connection connection, long gameId, Board board) throws SQLException {
        String sql = "insert into board (game_id, side, piece_type, piece_number, row_index, column_index) values (?, ?, ?, ?, ?, ?)";
        List<PiecePositionSnapshot> snapshots = mapToSnapshots(board.piecePosition());
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            executeBatchInsert(statement, gameId, snapshots);
        }
    }

    private List<PiecePositionSnapshot> mapToSnapshots(Map<Position, Piece> piecePosition) {
        return piecePosition.entrySet().stream()
                .map(this::createSnapshot)
                .toList();
    }

    private PiecePositionSnapshot createSnapshot(Map.Entry<Position, Piece> entry) {
        Position position = entry.getKey();
        Piece piece = entry.getValue();
        return new PiecePositionSnapshot(
                piece.side().name(),
                piece.type().name(),
                piece.pieceNumber(),
                position.row(),
                position.column()
        );
    }

    private void executeBatchInsert(PreparedStatement statement, long gameId, List<PiecePositionSnapshot> snapshots)
            throws SQLException {
        for (PiecePositionSnapshot snapshot : snapshots) {
            bindPieceParameters(statement, gameId, snapshot);
            statement.addBatch();
        }
        statement.executeBatch();
    }

    private void bindPieceParameters(PreparedStatement statement, long gameId, PiecePositionSnapshot snapshot)
            throws SQLException {
        statement.setLong(1, gameId);
        statement.setString(2, snapshot.side());
        statement.setString(3, snapshot.pieceType());
        statement.setString(4, snapshot.pieceNumber());
        statement.setInt(5, snapshot.rowIndex());
        statement.setInt(6, snapshot.columnIndex());
    }

    @Override
    public Board findByGameId(Connection connection, long gameId) throws SQLException {
        String sql = "select side, piece_type, piece_number, row_index, column_index from board where game_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            return executeFindAllById(statement, gameId);
        }
    }

    private Board executeFindAllById(PreparedStatement statement, long gameId) throws SQLException {
        statement.setLong(1, gameId);
        try (ResultSet resultSet = statement.executeQuery()) {
            return mapToBoard(resultSet);
        }
    }

    private Board mapToBoard(ResultSet resultSet) throws SQLException {
        Map<Position, Piece> board = new HashMap<>();
        while (resultSet.next()) {
            mapToSinglePiecePosition(resultSet, board);
        }
        return new Board(board);
    }

    private void mapToSinglePiecePosition(ResultSet resultSet, Map<Position, Piece> board) throws SQLException {
        Side side = Side.valueOf(resultSet.getString("side"));
        PieceType pieceType = PieceType.valueOf(resultSet.getString("piece_type"));
        String pieceNumber = resultSet.getString("piece_number");
        Piece piece = new Piece(side, pieceType, pieceNumber);

        int rowIndex = resultSet.getInt("row_index");
        int columnIndex = resultSet.getInt("column_index");
        board.put(new Position(rowIndex, columnIndex), piece);
    }
}
