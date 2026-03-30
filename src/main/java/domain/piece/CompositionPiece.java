package domain.piece;

import domain.board.Intersection;
import domain.game.Side;
import domain.move.Path;
import java.util.List;
import java.util.Objects;

public class CompositionPiece {

    private final PieceType type;
    private final Side side;

    public CompositionPiece(PieceType type, Side side) {
        this.type = type;
        this.side = side;
    }

    public List<Intersection> movablePaths(Intersection from, AlivePieces alivePieces) {
        List<Path> movablePaths = type.movablePaths(from, side);

        return type.movableDestinations(side, movablePaths, alivePieces);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CompositionPiece that)) {
            return false;
        }
        return type == that.type && side == that.side;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, side);
    }

    @Override
    public String toString() {
        return "CompositionPiece{" +
                "type=" + type +
                ", side=" + side +
                '}';
    }
}
