package data;

import domain.position.Position;
import java.sql.Connection;
import java.util.List;

public interface BoardDao {

    void insertAll(Connection conn, Long gameId, List<BoardEntity> pieces);

    List<BoardEntity> findByGameId(Connection conn, Long gameId);

    void deleteByPosition(Connection conn, Long gameId, Position position);

    void updatePosition(Connection conn, Long gameId, Position from, Position to);
}
