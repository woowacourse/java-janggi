package janggi.domain.piece;

import java.util.Map;

public class None extends Piece {
    public None() {
        super("ㅁ", null, Team.NONE);
    }

    @Override
    public Piece move(final Map<Position, Piece> pieces, final Position positionToMove) {
        throw new IllegalArgumentException("빈칸은 움직일 수 없습니다.");
    }

    @Override
    public void validatePositionToMove(Map<Position, Piece> pieces, Position positionToMove) {
        throw new IllegalArgumentException("빈칸은 움직일 수 없습니다.");

    }

    @Override
    public Piece from(Position position) {
        throw new IllegalArgumentException("빈칸은 움직일 수 없습니다.");
    }

    @Override
    public boolean isNone() {
        return true;
    }

    @Override
    public int getScore() {
        return 0;
    }
}
