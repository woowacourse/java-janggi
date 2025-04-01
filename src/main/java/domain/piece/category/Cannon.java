package domain.piece.category;

import domain.MoveInfos;
import domain.direction.Directions;
import domain.direction.PieceDirection;
import domain.piece.Piece;
import domain.spatial.Position;
import java.util.List;

public class Cannon extends Piece {

    private static final PieceCategory CATEGORY = PieceCategory.CANNON;
    private static final int PIECES_TO_PASS = 1;

    public Cannon(final Position position, final Directions directions) {
        super(position, directions);
    }

    @Override
    public List<Position> getPaths(final Position target) {
        List<Position> paths = directions.getPaths(position, target);
        if (position.isWithinPalace()) {
            return PieceDirection.REPEATED_DIAGONAL.get().getPaths(position, target);
        }
        return paths;
    }

    @Override
    public PieceCategory getCategory() {
        return CATEGORY;
    }

    @Override
    public Cannon move(final Position target, final MoveInfos moveInfos) {
        validateMove(moveInfos);
        return new Cannon(target, directions);
    }

    private void validateMove(final MoveInfos moveInfos) {
        if (moveInfos.isDiagonalPath()) {
            validateLastPathWithinPalace(moveInfos);
        }
        validateIntermediatePieceCount(moveInfos);
        validateTargetPieceIsCannon(moveInfos);
        validateCannonInIntermediatePath(moveInfos);
    }

    private void validateIntermediatePieceCount(final MoveInfos moveInfos) {
        if (moveInfos.countPiecesInIntermediatePath() != PIECES_TO_PASS) {
            throw new IllegalArgumentException("포는 중간에 기물이 " + PIECES_TO_PASS + "개여야 합니다.");
        }
    }

    private void validateTargetPieceIsCannon(final MoveInfos moveInfos) {
        if (moveInfos.isSameAsTargetPiece(CATEGORY)) {
            throw new IllegalArgumentException("포는 상대 포를 잡을 수 없습니다.");
        }
    }

    private void validateCannonInIntermediatePath(final MoveInfos moveInfos) {
        if (moveInfos.hasSamePieceCategoryInPath(CATEGORY)) {
            throw new IllegalArgumentException("포는 다른 포를 지나칠 수 없습니다.");
        }
    }
}
