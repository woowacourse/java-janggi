package db.jdbc;

import db.dao.BoardPieceDao;
import db.model.BoardPiece;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pieces.PieceType;
import pieces.Side;

public class JdbcBoardPieceDao implements BoardPieceDao {

    private final ConnectionManager connectionManager;

    public JdbcBoardPieceDao(final ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public void saveAll(final Long gameId, final List<BoardPiece> boardPieces) {
        validateGameId(gameId);

        String sql = """
            INSERT INTO board_piece (game_id, row_index, column_index, piece_type, piece_side)
            VALUES (?, ?, ?, ?, ?)
            """;

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            for (BoardPiece boardPiece : boardPieces) {
                statement.setLong(1, gameId);
                statement.setInt(2, boardPiece.row());
                statement.setInt(3, boardPiece.column());
                statement.setString(4, boardPiece.pieceType().name());
                statement.setString(5, boardPiece.pieceSide().name());
                statement.addBatch();
            }

            statement.executeBatch();
        } catch (SQLException e) {
            throw new IllegalStateException("보드 기물 배치 저장에 실패했습니다.", e);
        }
    }

    @Override
    public List<BoardPiece> findByGameId(final Long gameId) {
        validateGameId(gameId);

        String sql = """
            SELECT row_index, column_index, piece_type, piece_side
            FROM board_piece
            WHERE game_id = ?
            ORDER BY row_index, column_index
            """;

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, gameId);

            try (ResultSet resultSet = statement.executeQuery()) {
                List<BoardPiece> boardPieces = new ArrayList<>();
                while (resultSet.next()) {
                    boardPieces.add(parseBoardPiece(resultSet));
                }
                return boardPieces;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("보드 기물 배치 조회에 실패했습니다.", e);
        }
    }

    @Override
    public void deleteByGameId(final Long gameId) {
        validateGameId(gameId);

        String sql = """
            DELETE FROM board_piece
            WHERE game_id = ?
            """;

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, gameId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("보드 기물 배치 삭제에 실패했습니다.", e);
        }
    }

    private void validateGameId(final Long gameId) {
        if (gameId == null) {
            throw new IllegalArgumentException("게임 ID가 필요합니다.");
        }
    }

    private BoardPiece parseBoardPiece(final ResultSet resultSet) throws SQLException {
        return new BoardPiece(
            resultSet.getInt("row_index"),
            resultSet.getInt("column_index"),
            PieceType.valueOf(resultSet.getString("piece_type")),
            Side.valueOf(resultSet.getString("piece_side"))
        );
    }
}
