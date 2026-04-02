package janggi.domain.piece;

import janggi.domain.Team;
import janggi.domain.path.Path;
import janggi.domain.path.PieceOnPath;
import janggi.domain.position.Position;

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
    public Path getPath(Position from, Position to) {
        validateMove(from, to);
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

        if (absRowDiff > MAX_MOVE_DISTANCE || absColumnDiff > MAX_MOVE_DISTANCE) {
            throw new IllegalArgumentException("[ERROR] 사는 해당 위치로 이동할 수 없습니다.");
        }
    }
}
