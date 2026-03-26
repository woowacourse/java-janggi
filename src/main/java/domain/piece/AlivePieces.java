package domain.piece;

import domain.board.Intersection;
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

    public Piece placedAt(Intersection intersection) {
        return alivePieces.get(intersection);
    }
}
