package janggi.domain.piece;

import janggi.domain.Team;
import janggi.domain.position.Position;
import java.util.List;

public class Guard extends MoveablePiece {
    private static final String PIECE_NAME = "사";

    public Guard(Team team) {
        super(team);
    }

    @Override
    public boolean isSamePiece(Piece other) {
        return other instanceof Guard;
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
        if (!from.hasOffsetPairs(to, 0, 1)) {
            throw new IllegalArgumentException("[ERROR] 사는 해당 위치로 이동할 수 없습니다.");
        }
    }
}
