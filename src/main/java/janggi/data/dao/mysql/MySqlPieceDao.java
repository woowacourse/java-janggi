package janggi.data.dao.mysql;

import janggi.board.point.Point;
import janggi.data.DatabaseConnection;
import janggi.data.dao.BoardDao;
import janggi.data.dao.CampDao;
import janggi.data.dao.PieceDao;
import janggi.data.dao.PieceSymbolDao;
import janggi.data.exception.DatabaseQueryException;
import janggi.piece.Piece;
import java.sql.SQLException;

public final class MySqlPieceDao implements PieceDao {

    private final CampDao campDao;
    private final BoardDao boardDao;
    private final PieceSymbolDao pieceSymbolDao;

    public MySqlPieceDao(CampDao campDao, BoardDao boardDao, PieceSymbolDao pieceSymbolDao) {
        this.campDao = campDao;
        this.boardDao = boardDao;
        this.pieceSymbolDao = pieceSymbolDao;
    }

    @Override
    public void save(Point point, Piece piece) {
        final String query = """
                INSERT INTO piece (camp_id, piece_symbol_id, board_id, x, y)
                VALUES (?, ?, ?, ?, ?)
                """;
        try (final var connection = DatabaseConnection.createConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            var campId = campDao.findIdByName(piece.getCamp().name());
            var pieceSymbolId = pieceSymbolDao.findIdByName(piece.getPieceSymbol().name());
            var boardId = boardDao.findCurrentBoardId();
            preparedStatement.setInt(1, campId);
            preparedStatement.setInt(2, pieceSymbolId);
            preparedStatement.setInt(3, boardId);
            preparedStatement.setInt(4, point.x());
            preparedStatement.setInt(5, point.y());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new DatabaseQueryException("기물 정보를 저장하는 도중 오류가 발생했습니다. %s".formatted(e.getMessage()));
        }
    }

    @Override
    public void move(Point from, Point to) {
        final String query = """
                UPDATE piece
                SET x = ?, y = ?
                WHERE x = ? AND y = ? AND board_id = ?
                """;
        try (final var connection = DatabaseConnection.createConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, to.x());
            preparedStatement.setInt(2, to.y());
            preparedStatement.setInt(3, from.x());
            preparedStatement.setInt(4, from.y());
            preparedStatement.setInt(5, boardDao.findCurrentBoardId());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new DatabaseQueryException("기물 이동 정보를 저장하는 도중 오류가 발생했습니다. %s".formatted(e.getMessage()));
        }
    }

    @Override
    public void delete(Point point) {
        final String query = """
                DELETE FROM piece
                WHERE x = ? AND y = ? AND board_id = ?
                """;
        try (final var connection = DatabaseConnection.createConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, point.x());
            preparedStatement.setInt(2, point.y());
            preparedStatement.setInt(3, boardDao.findCurrentBoardId());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new DatabaseQueryException("기물 정보를 삭제하는 도중 오류가 발생했습니다. %s".formatted(e.getMessage()));
        }
    }
}
