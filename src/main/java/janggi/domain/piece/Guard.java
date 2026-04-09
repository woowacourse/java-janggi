package janggi.domain.piece;

import janggi.domain.path.PieceOnPath;
import janggi.domain.position.Movement;
import janggi.domain.Team;

public class Guard extends PalacePiece {
    private static final int MAX_MOVE_DISTANCE = 1;

    public Guard(Team team) {
        super(team);
    }

    @Override
    public PieceType getType() {
        return PieceType.GUARD;
    }

    @Override
    public void validateCanMove(PieceOnPath pieceOnPath, Piece endPiece) {
        validateSameTeam(endPiece);
    }

    @Override
    protected boolean isNormalMove(Movement movement) {
        validateInPalace(movement);
        return isOneStepStraightMove(movement);
    }

    @Override
    protected boolean isPalaceMove(Movement movement) {
        return isOneStepMove(movement) && palace.hasRoute(movement.getFrom(), movement.getTo());
    }

    private void validateInPalace(Movement movement) {
        if (!palace.isPalaceMove(movement.getFrom(), movement.getTo())) {
            throw new IllegalArgumentException("[ERROR] 사는 궁성 내에서만 움직일 수 있습니다.");
        }
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
}
