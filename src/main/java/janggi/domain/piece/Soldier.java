package janggi.domain.piece;

import janggi.domain.path.Path;
import janggi.domain.path.PieceOnPath;
import janggi.domain.position.Movement;
import janggi.domain.team.Team;

public class Soldier extends PalacePiece {
    private static final int MAX_MOVE_DISTANCE = 1;

    public Soldier(Team team, Palace palace) {
        super(team, palace);
    }

    @Override
    public PieceType getType() {
        return PieceType.SOLDIER;
    }

    @Override
    public Path getPath(Movement movement) {
        validateMove(movement);
        validateBackStep(movement);
        return findPath(movement);
    }

    @Override
    public void validateCanMove(PieceOnPath pieceOnPath, Piece endPiece) {
        validateSameTeam(endPiece);
    }

    private void validateMove(Movement movement) {
        if (!(isNormalMove(movement) || isPalaceRouteMove(movement))) {
            throw new IllegalArgumentException("[ERROR] 졸은 해당 위치로 이동할 수 없습니다.");
        }
    }

    private boolean isNormalMove(Movement movement) {
        int absRowDiff = Math.abs(movement.calculateRowDiff());
        int absColumnDiff = Math.abs(movement.calculateColumnDiff());

        return (absRowDiff == MAX_MOVE_DISTANCE && absColumnDiff == 0)
                || (absRowDiff == 0 && absColumnDiff == MAX_MOVE_DISTANCE);
    }

    private boolean isPalaceRouteMove(Movement movement) {
        int absRowDiff = Math.abs(movement.calculateRowDiff());
        int absColumnDiff = Math.abs(movement.calculateColumnDiff());

        boolean isOneStepMove = absRowDiff <= MAX_MOVE_DISTANCE && absColumnDiff <= MAX_MOVE_DISTANCE;

        return isOneStepMove && palace.hasRoute(movement.getFrom(), movement.getTo());
    }

    private void validateBackStep(Movement movement) {
        if (team.isBackward(movement.calculateRowDiff())) {
            throw new IllegalArgumentException("[ERROR] 졸은 뒷 방향으로 이동할 수 없습니다.");
        }
    }
}
