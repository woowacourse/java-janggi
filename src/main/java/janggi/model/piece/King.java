package janggi.model.piece;

import janggi.model.CastleArea;
import janggi.model.Color;
import janggi.model.OccupiedPositions;
import janggi.model.PieceIdentity;
import janggi.model.PieceType;
import janggi.model.Position;
import java.util.Set;
import java.util.stream.Collectors;

public class King extends Piece {

    public King(Color color) {
        super(new PieceIdentity(color, PieceType.KING));
    }

    @Override
    public Set<Position> calculateMovablePositions(Position start, OccupiedPositions occupied) {
        return CastleArea.calculateMovableDirections(start).stream()
                .filter(start::canMove)
                .map(start::move)
                .filter(destination -> destinationIsNotSameColor(occupied, destination))
                .collect(Collectors.toSet());
    }

    @Override
    public double getScore() {
        if (identity().getColor() == Color.BLUE) {
            return 0;
        }
        return 1.5;
    }

    private boolean destinationIsNotSameColor(OccupiedPositions occupied, Position destination) {
        return !occupied.existSameColor(destination, identity().getColor());
    }
}
