package janggi.domain.piece;

import janggi.domain.Team;
import janggi.domain.path.Path;
import janggi.domain.path.PieceOnPath;
import janggi.domain.position.Movement;
import janggi.domain.position.Position;

public class Elephant extends MoveablePiece {
    private static final int ONE_DIRECTION_MIN_DIFF = 2;
    private static final int ONE_DIRECTION_MAX_DIFF = 3;

    public Elephant(Team team) {
        super(team);
    }

    @Override
    public PieceType getType() {
        return PieceType.ELEPHANT;
    }

    @Override
    public Path getPath(Movement movement) {
        validateMove(movement);
        return findPath(movement.getFrom(), movement.getTo());
    }

    @Override
    public void validateCanMove(PieceOnPath pieceOnPath, Piece endPiece) {
        validateAllPieceEmpty(pieceOnPath);
        validateSameTeam(endPiece);
    }

    private void validateMove(Movement movement) {
        int absRowDiff = Math.abs(movement.calculateRowDiff());
        int absColumnDiff = Math.abs(movement.calculateColumnDiff());

        boolean isValidMove = (absRowDiff == ONE_DIRECTION_MIN_DIFF && absColumnDiff == ONE_DIRECTION_MAX_DIFF)
                || (absRowDiff == ONE_DIRECTION_MAX_DIFF && absColumnDiff == ONE_DIRECTION_MIN_DIFF);

        if (!isValidMove) {
            throw new IllegalArgumentException("[ERROR] 상은 해당 경로로 이동할 수 없습니다.");
        }
    }

    private Path findPath(Position from, Position to) {
        Path path = new Path();
        Position next = from.nextStraight(to);
        path.add(next);
        path.add(next.nextDiagonal(to));
        return path;
    }

    private void validateAllPieceEmpty(PieceOnPath pieceOnPath) {
        if (pieceOnPath.countNonEmpty() != 0) {
            throw new IllegalArgumentException("[ERROR] 상의 이동 경로에 기물이 있을 수 없습니다.");
        }
    }
}
