package domain.board;

import domain.movement.Move;
import domain.game.Side;
import domain.piece.AlivePieces;
import domain.piece.Piece;
import java.util.List;
import java.util.Map;

public final class Board {

    private final AlivePieces alivePieces;

    public Board(AlivePieces alivePieces) {
        this.alivePieces = alivePieces;
    }

    public void movePiece(
            Move move,
            Side side
    ) {
        List<Intersection> movableIntersections = getMovableIntersections(move.from(), side);
        if (!movableIntersections.contains(move.to())) {
            throw new IllegalArgumentException("도착 가능한 지점을 선택해야 합니다.");
        }

        alivePieces.replace(move);
    }

    public List<Intersection> getMovableIntersections(
            Intersection selectedIntersection,
            Side side
    ) {
        if (alivePieces.isEmpty(selectedIntersection)) {
            throw new IllegalArgumentException("기물이 있는 지점을 선택해야 합니다.");
        }
        if (alivePieces.placedNotSameSide(selectedIntersection, side)) {
            throw new IllegalArgumentException("같은 진영의 기물을 선택해야 합니다.");
        }

        return alivePieces.placedAt(selectedIntersection)
                .movableIntersections(selectedIntersection, alivePieces);
    }

    public boolean hasRoyalPiece(Side side) {
        return alivePieces.hasRoyalPiece(side);
    }

    public double getTotalScore(Side side) {
        return alivePieces.getTotalScore(side);
    }

    public Map<Intersection, Piece> getPieces() {
        return alivePieces.get();
    }
}
