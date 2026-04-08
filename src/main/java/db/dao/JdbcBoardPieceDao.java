package db.dao;

import db.jdbc.SqlConnection;
import db.model.BoardPieceEntity;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import pieces.PieceType;
import pieces.Side;

public class JdbcBoardPieceDao implements BoardPieceDao {

    @Override
    public void saveAll(final SqlConnection connection, final List<BoardPieceEntity> boardPieceEntities) {
        if (boardPieceEntities.isEmpty()) {
            return;
        }

        final String sql = """
            INSERT INTO board_piece (game_id, board_row, board_column, piece_type, piece_side, created_at, updated_at)
            VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
            """;

        try (final PreparedStatement statement = connection.prepareStatement(sql)) {
            for (final BoardPieceEntity boardPieceEntity : boardPieceEntities) {
                validateGameId(boardPieceEntity.gameId());

                statement.setLong(1, boardPieceEntity.gameId());
                statement.setInt(2, boardPieceEntity.boardRow());
                statement.setInt(3, boardPieceEntity.boardColumn());
                statement.setString(4, boardPieceEntity.pieceType().name());
                statement.setString(5, boardPieceEntity.pieceSide().name());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            throw new IllegalStateException("보드 기물 배치 저장에 실패했습니다.", e);
        }
    }

    @Override
    public List<BoardPieceEntity> findAllByGameId(SqlConnection connection, final Long gameId) {
        validateGameId(gameId);

        final String sql = """
            SELECT id, game_id, board_row, board_column, piece_type, piece_side
            FROM board_piece
            WHERE game_id = ?
            """;

        try (final PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, gameId);

            try (final ResultSet resultSet = statement.executeQuery()) {
                final List<BoardPieceEntity> boardPieceEntities = new ArrayList<>();
                while (resultSet.next()) {
                    boardPieceEntities.add(parseBoardPiece(resultSet));
                }
                return boardPieceEntities;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("보드 기물 배치 조회에 실패했습니다.", e);
        }
    }

    @Override
    public Optional<BoardPieceEntity> findByGameIdAndPosition(SqlConnection connection, final Long gameId, final int row, final int column) {
        validateGameId(gameId);

        final String sql = """
            SELECT id, game_id, board_row, board_column, piece_type, piece_side
            FROM board_piece
            WHERE game_id = ? AND board_row = ? AND board_column = ?
            """;

        try (final PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, gameId);
            statement.setInt(2, row);
            statement.setInt(3, column);

            try (final ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(parseBoardPiece(resultSet));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new IllegalStateException("보드 기물 배치 조회에 실패했습니다.", e);
        }
    }

    @Override
    public void updatePosition(SqlConnection connection, final Long id, final int row, final int column) {
        validateId(id);

        final String sql = """
            UPDATE board_piece
            SET board_row = ?, board_column = ?, updated_at = CURRENT_TIMESTAMP
            WHERE id = ?
            """;

        try (final PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, row);
            statement.setInt(2, column);
            statement.setLong(3, id);

            final int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new IllegalArgumentException("수정할 기물이 존재하지 않습니다. id=" + id);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("보드 기물 수정에 실패했습니다.", e);
        }
    }

    @Override
    public void deleteByGameIdAndPosition(SqlConnection connection, final Long gameId, final int row, final int column) {
        validateGameId(gameId);

        final String sql = """
            DELETE FROM board_piece
            WHERE game_id = ? AND board_row = ? AND board_column = ?
            """;

        try (final PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, gameId);
            statement.setInt(2, row);
            statement.setInt(3, column);

            final int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new IllegalArgumentException(
                    "삭제할 기물이 존재하지 않습니다. gameId=" + gameId + ", row=" + row + ", column=" + column);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("보드 기물 삭제에 실패했습니다.", e);
        }
    }

    private void validateGameId(final Long gameId) {
        if (gameId == null) {
            throw new IllegalArgumentException("게임 ID가 필요합니다.");
        }
    }

    private void validateId(final Long id) {
        if (id == null) {
            throw new IllegalArgumentException("기물 ID가 필요합니다.");
        }
    }

    private BoardPieceEntity parseBoardPiece(final ResultSet resultSet) throws SQLException {
        return new BoardPieceEntity(
            resultSet.getLong("id"),
            resultSet.getLong("game_id"),
            resultSet.getInt("board_row"),
            resultSet.getInt("board_column"),
            PieceType.valueOf(resultSet.getString("piece_type")),
            Side.valueOf(resultSet.getString("piece_side"))
        );
    }
}
