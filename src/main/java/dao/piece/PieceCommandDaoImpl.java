package dao.piece;

import dao.converter.PieceDto;
import domain.point.Point;
import java.util.List;
import queue.DelayedQuery;
import queue.Transaction;

public class PieceCommandDaoImpl implements PieceCommandDao {

    public void insert(Transaction transaction, PieceDto piece) {
        String sql = """
                INSERT INTO piece (row_index, column_index, piece_type, team, game_room_name)
                VALUES (?, ?, ?, ?, ?);
                """;
        transaction.addLast(createDelayedQuery(sql, List.of(piece.rowIndex(), piece.columnIndex(),
                piece.pieceType().name(), piece.team().name(), piece.gameRoomName())));
    }

    public void insertAll(Transaction transaction, List<PieceDto> pieceDtos) {
        pieceDtos.forEach(pieceDto -> insert(transaction, pieceDto));
    }

    public void updatePointByGameRoomNameAndPoint(Transaction transaction,
                                                  String gameRoomName, Point oldPoint, Point newPoint) {
        String sql = """
                UPDATE piece
                SET row_index = ?, column_index = ?
                WHERE row_index = ? AND column_index = ? AND game_room_name = ?
                """;
        transaction.addLast(createDelayedQuery(sql,
                List.of(newPoint.row(), newPoint.column(), oldPoint.row(), oldPoint.column(), gameRoomName))
        );
    }

    public void deleteByGameRoomNameAndPoint(Transaction transaction,
                                             String gameRoomName, Point point) {
        String sql = """
                DELETE FROM piece
                WHERE game_room_name = ? AND row_index = ? AND column_index = ?
                """;
        transaction.addLast(createDelayedQuery(sql, List.of(gameRoomName, point.row(), point.column())));
    }

    private DelayedQuery createDelayedQuery(String sql, List<Object> params) {
        return new DelayedQuery(sql, params);
    }
}
