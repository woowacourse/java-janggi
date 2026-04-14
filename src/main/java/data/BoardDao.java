package data;

import java.sql.Connection;
import java.util.List;

public interface BoardDao {

    void insertAll(Connection conn, Long gameId, List<BoardEntity> pieces);

    List<BoardEntity> findByGameId(Connection conn, Long gameId);

    void deleteByGameId(Connection conn, Long gameId);
}
