package domain.piece;

import domain.board.Intersection;
import domain.movement.Move;
import domain.game.Side;
import java.util.HashMap;
import java.util.Map;

public class AlivePieces {

    private final Map<Intersection, Piece> alivePieces;

    public AlivePieces(Map<Intersection, Piece> alivePieces) {
        this.alivePieces = Map.copyOf(alivePieces);
    }

    public AlivePieces replace(Move move) {
        if (isEmpty(move.from())) {
            throw new IllegalStateException("기물을 움직이기 위해선, 출발지에 기물이 존재해야 합니다.");
        }

        HashMap<Intersection, Piece> nextAlivePieces = new HashMap<>(alivePieces);
        Piece piece = nextAlivePieces.remove(move.from());
        nextAlivePieces.put(move.to(), piece);

        return new AlivePieces(nextAlivePieces);
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

    public double getTotalScore(Side side) {
        return alivePieces.values()
                .stream()
                .filter(piece -> piece.hasSameSide(side))
                .mapToDouble(Piece::getScore)
                .sum();
    }
}
