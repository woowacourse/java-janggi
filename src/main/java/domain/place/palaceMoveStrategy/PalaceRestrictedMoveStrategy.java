package domain.place.palaceMoveStrategy;

import domain.place.Place;
import domain.place.piece.Side;
import domain.position.Position;
import java.util.List;
import java.util.Map;

public class PalaceRestrictedMoveStrategy implements PalaceMoveStrategy {
    @Override
    public List<Position> getPath(Position from) {
        return List.of();
    }

    @Override
    public boolean canMove(Map<Position, Place> board, Position from, Position to, Side fromSide) {
        return false;
    }

}
