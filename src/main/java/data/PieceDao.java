package data;

import java.sql.Connection;
import java.util.List;

public interface PieceDao {

    void insertAll(Connection conn, Long boardId, List<PieceDto> pieces);

    List<PieceDto> findByBoardId(Connection conn, Long boardId);

    void deleteByBoardId(Connection conn, Long boardId);
}
