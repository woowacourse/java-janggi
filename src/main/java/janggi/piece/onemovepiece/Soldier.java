package janggi.piece.onemovepiece;

import janggi.piece.Piece;
import janggi.piece.PieceProfile;
import janggi.piece.PieceType;
import janggi.piece.Team;
import janggi.position.Position;
import java.util.List;

public class Soldier extends Piece {

    public Soldier(final Team team, final Position position) {
        super(new PieceProfile(PieceType.SOLDIER, team), position);
    }

    @Override
    public List<Position> makeRoute(final Position position) {
        return List.of();
    }

    @Override
    public void canMoveBy(final Position position) {
        if (isNotMove(position)) {
            throw new IllegalArgumentException("[ERROR] 병이 움직일 수 없는 위치입니다.");
        }
    }

    private boolean isNotMove(final Position position) {
        return !getBoardPosition().isBehind(position);
    }
}
