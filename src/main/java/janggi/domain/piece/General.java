package janggi.domain.piece;

import janggi.domain.path.Path;
import janggi.domain.path.PieceOnPath;
import janggi.domain.position.Movement;
import janggi.domain.team.Team;

public class General extends PalacePiece {
    private static final int MAX_MOVE_DISTANCE = 1;

    public General(Team team, Palace palace) {
        super(team, palace);
    }

    @Override
    public PieceType getType() {
        return PieceType.GENERAL;
    }

    @Override
    public Path getPath(Movement movement) {
        validateInPalace(movement);
        validateMove(movement);
        return findPath(movement);
    }

    @Override
    public void validateCanMove(PieceOnPath pieceOnPath, Piece endPiece) {
        validateSameTeam(endPiece);
    }

    private void validateInPalace(Movement movement) {
        if (!palace.isPalaceMove(movement.getFrom(), movement.getTo())) {
            throw new IllegalArgumentException("[ERROR] 장은 궁성 내에서만 움직일 수 있습니다.");
        }
    }

    private void validateMove(Movement movement) {
        if (!(isNormalMove(movement) || isPalaceRouteMove(movement))) {
            throw new IllegalArgumentException("[ERROR] 장은 해당 위치로 이동할 수 없습니다.");
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
}
