package janggi.piece;

import janggi.board.Point;
import janggi.camp.Camp;
import janggi.view.PieceSymbol;
import java.util.Set;

public final class Cannon extends Piece {

    private static final int POSSIBLE_JUMP_OVER_PIECE_COUNT = 1;

    public Cannon(Camp camp) {
        super(camp);
    }

    @Override
    public void validateMove(Point from, Point to) {
        validateLinearMove(from, to);
    }

    private void validateLinearMove(Point from, Point to) {
        if (!from.isHorizontallyAlignedWith(to) && !from.isVerticallyAlignedWith(to)) {
            throw new IllegalArgumentException("포는 수평 혹은 수직으로만 움직여야 합니다.");
        }
    }

    @Override
    public void validateRouteObstacles(Set<Piece> piecesOnRoute) {
        validatePieceCount(piecesOnRoute);
        validateNotJumpOverCannon(piecesOnRoute);
    }

    private void validatePieceCount(Set<Piece> pieces) {
        if (pieces.size() != POSSIBLE_JUMP_OVER_PIECE_COUNT) {
            throw new IllegalArgumentException("포는 정확히 하나의 기물만 넘을 수 있습니다. 넘은 기물 수: %d".formatted(pieces.size()));
        }
    }

    private void validateNotJumpOverCannon(Set<Piece> pieces) {
        if (hasCannon(pieces)) {
            throw new IllegalArgumentException("포는 포를 넘을 수 없습니다.");
        }
    }

    private boolean hasCannon(Set<Piece> pieces) {
        return pieces.stream()
                .anyMatch(piece -> piece.getPieceSymbol() == this.getPieceSymbol());
    }

    @Override
    public Set<Point> findRoute(Point from, Point to) {
        if (from.isHorizontallyAlignedWith(to)) {
            return from.findHorizontalPointsBetween(to);
        }
        return from.findVerticalPointsBetween(to);
    }

    @Override
    public void validateCatch(Piece targetPiece) {
        super.validateCatch(targetPiece);
        if (getPieceSymbol() == targetPiece.getPieceSymbol()) {
            throw new IllegalArgumentException("포는 포를 잡을 수 없습니다.");
        }
    }

    @Override
    public PieceSymbol getPieceSymbol() {
        return PieceSymbol.CANNON;
    }
}
