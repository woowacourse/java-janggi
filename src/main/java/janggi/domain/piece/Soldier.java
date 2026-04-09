package janggi.domain.piece;

import janggi.domain.path.PieceOnPath;
import janggi.domain.position.Movement;
import janggi.domain.Team;

public class Soldier extends PalacePiece {
    private static final int MAX_MOVE_DISTANCE = 1;

    public Soldier(Team team) {
        super(team);
    }

    @Override
    public PieceType getType() {
        return PieceType.SOLDIER;
    }

    @Override
    public void validateCanMove(PieceOnPath pieceOnPath, Piece endPiece) {
        validateSameTeam(endPiece);
    }

    @Override
    protected boolean isNormalMove(Movement movement) {
        validateBackStep(movement);
        return isOneStepStraightMove(movement);
    }

    @Override
    protected boolean isPalaceMove(Movement movement) {
        validateBackStep(movement);
        return isOneStepMove(movement)
                && palace.hasRoute(movement.getFrom(), movement.getTo());
    }

    private boolean isOneStepStraightMove(Movement movement) {
        int absRowDiff = Math.abs(movement.calculateRowDiff());
        int absColumnDiff = Math.abs(movement.calculateColumnDiff());
        return (absRowDiff == MAX_MOVE_DISTANCE && absColumnDiff == 0)
                || (absRowDiff == 0 && absColumnDiff == MAX_MOVE_DISTANCE);
    }

    private boolean isOneStepMove(Movement movement) {
        int absRowDiff = Math.abs(movement.calculateRowDiff());
        int absColumnDiff = Math.abs(movement.calculateColumnDiff());
        return absRowDiff <= MAX_MOVE_DISTANCE && absColumnDiff <= MAX_MOVE_DISTANCE;
    }

    private void validateBackStep(Movement movement) {
        if (team.isBackward(movement.calculateRowDiff())) {
            throw new IllegalArgumentException("[ERROR] 졸은 뒷 방향으로 이동할 수 없습니다.");
        }
    }
}
