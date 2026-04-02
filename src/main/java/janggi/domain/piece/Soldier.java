package janggi.domain.piece;

import janggi.domain.Team;
import janggi.domain.path.Path;
import janggi.domain.path.PieceOnPath;
import janggi.domain.position.Position;

public class Soldier extends MoveablePiece {
    private static final int MAX_MOVE_DISTANCE = 1;

    public Soldier(Team team) {
        super(team);
    }

    @Override
    public boolean isSamePiece(Piece other) {
        return other instanceof Soldier;
    }

    @Override
    public PieceType getType() {
        return PieceType.SOLDIER;
    }

    @Override
    public Path getPath(Position from, Position to) {
        validateMove(from, to);
        validateBackStep(from, to);
        return new Path();
    }

    @Override
    public boolean canMove(PieceOnPath piecesOnPath, Piece endPiece) {
        validateSameTeam(endPiece);
        return true;
    }

    private void validateMove(Position from, Position to) {
        int absRowDiff = Math.abs(from.calculateRowDiff(to));
        int absColumnDiff = Math.abs(from.calculateColumnDiff(to));

        boolean isValidMove = (absRowDiff == MAX_MOVE_DISTANCE && absColumnDiff == 0)
                || (absRowDiff == 0 && absColumnDiff == MAX_MOVE_DISTANCE);

        if (!isValidMove) {
            throw new IllegalArgumentException("[ERROR] 졸은 해당 위치로 이동할 수 없습니다.");
        }
    }

    private void validateBackStep(Position from, Position to) {
        if (getTeam().isBackward(from.calculateRowDiff(to))) {
            throw new IllegalArgumentException("[ERROR] 졸은 뒷 방향으로 이동할 수 없습니다.");
        }
    }
}
