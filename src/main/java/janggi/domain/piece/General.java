package janggi.domain.piece;

import janggi.domain.path.Path;
import janggi.domain.path.PieceOnPath;
import janggi.domain.position.Movement;
import janggi.domain.team.Team;

public class General extends MoveablePiece {
    private static final int MAX_MOVE_DISTANCE = 1;

    public General(Team team) {
        super(team);
    }

    @Override
    public PieceType getType() {
        return PieceType.GENERAL;
    }

    @Override
    public Path getPath(Movement movement) {
        validateMove(movement);
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
            throw new IllegalArgumentException("[ERROR] 장은 해당 위치로 이동할 수 없습니다.");
        }
    }
}
