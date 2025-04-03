package dao.piece;

import dao.converter.PieceDto;
import domain.point.Point;
import java.util.List;
import queue.Transaction;

public interface PieceCommandDao {

    void insert(Transaction transaction, PieceDto piece);

    void insertAll(Transaction transaction, List<PieceDto> pieceDtos);

    void updatePointByGameRoomNameAndPoint(Transaction transaction,
                                           String gameRoomName, Point oldPoint, Point newPoint);

    void deleteByGameRoomNameAndPoint(Transaction transaction,
                                      String gameRoomName, Point point);
}
