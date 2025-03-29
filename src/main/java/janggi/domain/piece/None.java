package janggi.domain.piece;

import janggi.domain.Team;
import java.util.Map;

public class None extends Piece {

    public None(Position position) {
        super(PieceType.NONE, position, Team.NONE);
    }

    @Override
    public Piece from(Position position) {
        throw new IllegalArgumentException("빈칸은 움직일 수 없습니다.");
    }

    @Override
    public Piece move(final Map<Position, Piece> pieces, final Position positionToMove) {
        throw new IllegalArgumentException("빈칸은 움직일 수 없습니다.");
    }

    @Override
    public void validatePositionToMove(Map<Position, Piece> pieces, Position positionToMove) {
        throw new IllegalArgumentException("빈칸은 움직일 수 없습니다.");
    }
}
