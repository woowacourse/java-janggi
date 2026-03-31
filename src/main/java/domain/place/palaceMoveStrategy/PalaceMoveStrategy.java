package domain.place.palaceMoveStrategy;

import domain.place.Place;
import domain.place.piece.Side;
import domain.position.Position;
import java.util.List;
import java.util.Map;

public interface PalaceMoveStrategy {
    List<Position> getPath(Position from);

    boolean canMove(Map<Position, Place> board, Position from, Position to, Side fromSide);
}