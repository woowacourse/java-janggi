package dao.piece;

import dao.converter.PieceDto;
import domain.point.Point;
import java.sql.Connection;
import java.util.List;

public interface PieceCommandDao {

    void insert(Connection connection, PieceDto piece);

    void insertAll(Connection connection, List<PieceDto> pieceDtos);

    void updatePointByGameRoomNameAndPoint(Connection connection, String gameRoomName, Point oldPoint, Point newPoint);

    void deleteByGameRoomNameAndPoint(Connection connection, String gameRoomName, Point point);
}
