package janggi.domain.piece;

import janggi.domain.Team;
import janggi.domain.path.Path;
import janggi.domain.position.Position;

import java.util.List;

public class Elephant extends MoveablePiece {
    private static final int ONE_DIRECTION_MIN_DIFF = 2;
    private static final int ONE_DIRECTION_MAX_DIFF = 3;

    public Elephant(Team team) {
        super(team);
    }

    @Override
    public boolean isSamePiece(Piece other) {
        return other instanceof Elephant;
    }

    @Override
    public PieceType getType() {
        return PieceType.ELEPHANT;
    }

    @Override
    public Path getPath(Position from, Position to) {
        validateMove(from, to);
        return findPath(from, to);
    }

    @Override
    public boolean canMove(List<Piece> piecesOnPath, Piece endPiece) {
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

    private void validateAllPieceEmpty(List<Piece> piecesOnPath) {
        if (!piecesOnPath.stream().allMatch(Piece::isEmptyPiece)) {
            throw new IllegalArgumentException("[ERROR] 상의 이동 경로에 기물이 있을 수 없습니다.");
        }
    }
}
