package janggi.domain.piece;

import janggi.domain.team.Team;
import janggi.domain.path.Path;
import janggi.domain.path.PieceOnPath;
import janggi.domain.position.Movement;

public class Soldier extends MoveablePiece {
    private static final int MAX_MOVE_DISTANCE = 1;

    public Soldier(Team team) {
        super(team);
    }

    @Override
    public PieceType getType() {
        return PieceType.SOLDIER;
    }

    @Override
    public Path getPath(Movement movement) {
        validateMove(movement);
        validateBackStep(movement);
        return new Path();
    }

    @Override
    public void validateCanMove(PieceOnPath piecesOnPath, Piece endPiece) {
        validateSameTeam(endPiece);
    }

    private void validateMove(Movement movement) {
        int absRowDiff = Math.abs(movement.calculateRowDiff());
        int absColumnDiff = Math.abs(movement.calculateColumnDiff());

        boolean isValidMove = (absRowDiff == MAX_MOVE_DISTANCE && absColumnDiff == 0)
                || (absRowDiff == 0 && absColumnDiff == MAX_MOVE_DISTANCE);

        if (!isValidMove) {
            throw new IllegalArgumentException("[ERROR] 졸은 해당 위치로 이동할 수 없습니다.");
        }
    }

    private void validateBackStep(Movement movement) {
        if (getTeam().isBackward(movement.calculateRowDiff())) {
            throw new IllegalArgumentException("[ERROR] 졸은 뒷 방향으로 이동할 수 없습니다.");
        }
    }
}
