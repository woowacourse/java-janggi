package domain.palace;

import domain.Position;

import java.util.List;

public interface PalaceRouter {
    boolean isInsidePalace(Position position);
    List<Position> getDiagonalAdjacents(Position position);
}
