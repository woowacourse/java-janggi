package janggi.domain.piece;

import janggi.domain.Team;
import janggi.domain.path.Path;
import janggi.domain.path.PieceOnPath;
import janggi.domain.position.Movement;

public class Guard extends MoveablePiece {
    private static final int MAX_MOVE_DISTANCE = 1;

    public Guard(Team team) {
        super(team);
    }

    @Override
    public boolean isSamePiece(Piece other) {
        return other instanceof Guard;
    }

    @Override
    public PieceType getType() {
        return PieceType.GUARD;
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

        if (absRowDiff > MAX_MOVE_DISTANCE || absColumnDiff > MAX_MOVE_DISTANCE) {
            throw new IllegalArgumentException("[ERROR] 사는 해당 위치로 이동할 수 없습니다.");
        }
    }
}
