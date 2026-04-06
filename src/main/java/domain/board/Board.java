package domain.board;

import domain.game.Side;
import domain.piece.AlivePieces;
import domain.piece.Piece;
import domain.piece.PieceType;
import java.util.List;

public final class Board {

    private final AlivePieces alivePieces;

    public Board(AlivePieces alivePieces) {
        this.alivePieces = alivePieces;
    }

    public void movePiece(
            Intersection startIntersection,
            Intersection destination,
            Side side
    ) {
        List<Intersection> movableIntersections = getMovableIntersections(startIntersection, side);
        if (!movableIntersections.contains(destination)) {
            throw new IllegalArgumentException("도착 가능한 지점을 선택해야 합니다.");
        }

        alivePieces.replace(startIntersection, destination);
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
                .movableDestinations(selectedIntersection, alivePieces);
    }

    public boolean isEmpty(Intersection intersection) {
        return alivePieces.isEmpty(intersection);
    }

    public Piece placedAt(Intersection intersection) {
        return alivePieces.placedAt(intersection);
    }

    public boolean isGeneralCaptured(Side side) {
        return alivePieces.toList()
                .stream()
                .filter(piece -> piece.isSameSide(side))
                .noneMatch(piece -> piece.isSameType(PieceType.GENERAL));
    }

    public int calculatePiecePointOf(Side side) {
        return alivePieces.calculatePiecePointOf(side);
    }

    public AlivePieces toAlivePieces() {
        return alivePieces;
    }
}
