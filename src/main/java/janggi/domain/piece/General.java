package janggi.domain.piece;

import janggi.domain.Team;
import janggi.domain.position.Position;
import java.util.List;

public class General extends MoveablePiece {
    private static final String PIECE_NAME = "장";
    private static final int MAX_MOVE_DISTANCE = 1;

    public General(Team team) {
        super(team);
    }

    @Override
    public boolean isSamePiece(Piece other) {
        return other instanceof General;
    }

    @Override
    public String getDisplayName() {
        return PIECE_NAME;
    }

    @Override
    public List<Position> getPath(Position from, Position to) {
        validateMove(from, to);
        return List.of();
    }

    @Override
    public boolean canMove(List<Piece> piecesOnPath, Piece endPiece) {
        validateSameTeam(endPiece);
        return true;
    }

    private void validateMove(Position from, Position to) {
        int absRowDiff = Math.abs(from.calculateRowDiff(to));
        int absColumnDiff = Math.abs(from.calculateColumnDiff(to));

        if (absRowDiff > MAX_MOVE_DISTANCE || absColumnDiff > MAX_MOVE_DISTANCE) {
            throw new IllegalArgumentException("[ERROR] 장은 해당 위치로 이동할 수 없습니다.");
        }
    }
}
