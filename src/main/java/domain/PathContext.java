package domain;

import domain.piece.Piece;
import domain.piece.strategy.Direction;
import domain.position.Position;
import java.util.Collections;
import java.util.Map;

public class PathContext {
    private final Map<Position, Piece> context;

    private PathContext(Map<Position, Piece> context) {
        this.context = context;
    }

    public static PathContext from(Map<Position, Piece> pathContext) {
        return new PathContext(Collections.unmodifiableMap(pathContext));
    }

    public int getPieceCount() {
        return context.size();
    }

    public boolean hasPo() {
        return context.values().stream()
                .anyMatch(piece -> !piece.canBeJumpedOver());
    }

    public Direction getDirection(Position start) {
        for (Position position : context.keySet()) {
            try {
                return Direction.getDirectionByPosition(position, start);
            } catch (IllegalArgumentException e) {
                continue;
            }
        }
        return null;
    }
}
