package data;

import java.sql.Connection;
import java.util.List;

public interface PieceDao {

    void insertAll(Connection conn, Long gameId, List<PieceDto> pieces);

    List<PieceDto> findByGameId(Connection conn, Long gameId);

    void deleteByGameId(Connection conn, Long gameId);
}
