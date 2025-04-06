package dao.piece;

import dao.converter.PieceDto;
import domain.point.Point;
import java.sql.Connection;
import java.util.List;

public interface PieceDao {

    boolean insert(Connection connection, PieceDto piece);

    boolean insertAll(Connection connection, List<PieceDto> pieceDtos);

    boolean updatePointByGameRoomNameAndPoint(Connection connection,
                                              String gameRoomName, Point oldPoint, Point newPoint);

    boolean deleteByGameRoomNameAndPoint(Connection connection, String gameRoomName, Point point);

    List<PieceDto> findByGameRoomName(Connection connection, String gameRoomName);
}
