package domain.piece;

import domain.board.Intersection;
import domain.movement.Move;
import domain.game.Side;
import java.util.HashMap;
import java.util.Map;

public class AlivePieces {

    private final Map<Intersection, Piece> alivePieces;

    public AlivePieces(Map<Intersection, Piece> alivePieces) {
        this.alivePieces = new HashMap<>(alivePieces);
    }

    public void replace(Move move) {
        if (isEmpty(move.from())) {
            return;
        }

        Piece piece = alivePieces.remove(move.from());
        alivePieces.put(move.to(), piece);
    }

    public boolean hasRoyalPiece(Side side) {
        return alivePieces.values()
                .stream()
                .filter(piece -> piece.hasSameSide(side))
                .anyMatch(Piece::isRoyalPiece);
    }

    public Map<Intersection, Piece> get() {
        return Map.copyOf(alivePieces);
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

        return piece.hasSameSide(side);
    }

    public boolean placedNotSameSide(Intersection intersection, Side side) {
        return !placedSameSide(intersection, side);
    }

    public boolean placedOppositeSide(Intersection intersection, Side side) {
        if (isEmpty(intersection)) {
            return false;
        }

        Piece piece = placedAt(intersection);

        return piece.hasDifferentSide(side);
    }

    public int getTotalScore(Side side) {
        return alivePieces.values()
                .stream()
                .filter(piece -> piece.hasSameSide(side))
                .mapToInt(Piece::getScore)
                .sum();
    }
}
