package janggi.domain.piece;

import janggi.domain.Team;
import janggi.domain.path.Path;
import janggi.domain.path.PieceOnPath;
import janggi.domain.position.Position;

public class Horse extends MoveablePiece {
    private static final int ONE_DIRECTION_MIN_DIFF = 1;
    private static final int ONE_DIRECTION_MAX_DIFF = 2;

    public Horse(Team team) {
        super(team);
    }

    @Override
    public boolean isSamePiece(Piece other) {
        return other instanceof Horse;
    }

    @Override
    public PieceType getType() {
        return PieceType.HORSE;
    }

    @Override
    public Path getPath(Position from, Position to) {
        validateMove(from, to);
        return findPath(from, to);
    }

    @Override
    public boolean canMove(PieceOnPath piecesOnPath, Piece endPiece) {
        validateAllPieceEmpty(piecesOnPath);
        validateSameTeam(endPiece);
        return true;
    }

    private void validateMove(Position from, Position to) {
        int absRowDiff = Math.abs(from.calculateRowDiff(to));
        int absColumnDiff = Math.abs(from.calculateColumnDiff(to));

        boolean isValidMove = (absRowDiff == ONE_DIRECTION_MIN_DIFF && absColumnDiff == ONE_DIRECTION_MAX_DIFF)
                || (absRowDiff == ONE_DIRECTION_MAX_DIFF && absColumnDiff == ONE_DIRECTION_MIN_DIFF);

        if (!isValidMove) {
            throw new IllegalArgumentException("[ERROR] 마는 해당 경로로 이동할 수 없습니다.");
        }
    }

    private Path findPath(Position from, Position to) {
        Path path = new Path();
        path.add(from.nextStraight(to));
        return path;
    }

    private void validateAllPieceEmpty(PieceOnPath piecesOnPath) {
        if (!piecesOnPath.stream().allMatch(Piece::isEmptyPiece)) {
            throw new IllegalArgumentException("[ERROR] 마의 이동 경로에 기물이 있을 수 없습니다.");
        }
    }
}
