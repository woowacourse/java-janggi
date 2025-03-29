package domain.board;

import java.util.List;

@FunctionalInterface
public interface PathCreator {

    List<BoardLocation> create(BoardLocation currentLocation, BoardVector boardVector);
}
