package repository;

import domain.place.Place;
import domain.position.Position;
import java.sql.Connection;
import java.util.Map;

public interface BoardRepository {
    void saveBoard(long roomId, Map<Position, Place> board, Connection conn);

    Map<Position, Place> findBoard(long roomId, Connection conn);
}
