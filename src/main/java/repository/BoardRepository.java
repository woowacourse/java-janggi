package repository;

import domain.place.Place;
import domain.position.Position;
import java.util.Map;

public interface BoardRepository {
    void saveBoard(Map<Position, Place> board);
    Map<Position, Place> findBoard();
}
