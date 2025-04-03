package janggi.model.piece;

import janggi.model.CastleArea;
import janggi.model.Color;
import janggi.model.OccupiedPositions;
import janggi.model.PieceIdentity;
import janggi.model.PieceType;
import janggi.model.Position;
import java.util.Set;
import java.util.stream.Collectors;

public class Guard extends Piece {

    public Guard(Color color) {
        super(new PieceIdentity(color, PieceType.GUARD));
    }

    @Override
    public Set<Position> calculateMovablePositions(Position start, OccupiedPositions occupied) {
        return CastleArea.fromCastleMovablePositions(start).stream()
                .filter(destination -> destinationIsNotSameColor(occupied, destination))
                .collect(Collectors.toSet());
    }

    @Override
    public double getScore() {
        return identity().score();
    }

    private boolean destinationIsNotSameColor(OccupiedPositions occupied, Position destination) {
        return !occupied.existSameColor(destination, identity().color());
    }
}
