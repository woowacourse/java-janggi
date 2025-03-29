package domain.board;

import java.util.List;

public interface PathCreator {

    List<BoardLocation> create(BoardLocation currentLocation, BoardVector boardVector);
}
