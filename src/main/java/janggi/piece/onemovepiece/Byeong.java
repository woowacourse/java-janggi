package janggi.piece.onemovepiece;

import janggi.piece.Piece;
import janggi.piece.PieceProfile;
import janggi.piece.PieceType;
import janggi.piece.Team;
import janggi.position.Position;
import java.util.List;

public class Byeong extends Piece {

    public Byeong(final Team team, final Position position) {
        super(new PieceProfile(PieceType.BYEONG, team), position);
    }

    @Override
    public List<Position> makeRoute(final Position position) {
        return List.of();
    }

    @Override
    public boolean isMove(final Position position) {
        final int dx = getBoardPosition().getRow() - position.getRow();
        final int dy = getBoardPosition().getCol() - position.getCol();

        if (dx == 0 && Math.abs(dy) == 1 || dx == -1 && dy == 0) {
            return true;
        }

        throw new IllegalArgumentException("[ERROR] 병이 움직일 수 없는 위치 입니다.");
    }
}
