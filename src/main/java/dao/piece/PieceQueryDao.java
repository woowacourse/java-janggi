package dao.piece;

import dao.converter.PieceDto;
import java.sql.Connection;
import java.util.List;

public interface PieceQueryDao {

    List<PieceDto> findByGameRoomName(Connection connection, String gameRoomName);
}
