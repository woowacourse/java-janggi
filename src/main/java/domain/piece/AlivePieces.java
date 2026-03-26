package domain.piece;

import domain.board.Intersection;
import domain.game.Side;
import java.util.HashMap;
import java.util.Map;

public class AlivePieces {

    private final Map<Intersection, Piece> alivePieces;

    public AlivePieces(Map<Intersection, Piece> alivePieces) {
        this.alivePieces = new HashMap<>(alivePieces);
    }

    public boolean isEmpty(Intersection intersection) {
        return !alivePieces.containsKey(intersection);
    }

    public boolean isNotEmpty(Intersection intersection) {
        return !isEmpty(intersection);
    }

    public Piece placedAt(Intersection intersection) {
        return alivePieces.get(intersection);
    }

    public boolean placedSameSide(Intersection intersection, Side side) {
        if (isEmpty(intersection)) {
            return false;
        }

        Piece piece = placedAt(intersection);

        return piece.side == side;
    }

    public boolean placedNotSameSide(Intersection intersection, Side side) {
        return !placedSameSide(intersection, side);
    }
}
