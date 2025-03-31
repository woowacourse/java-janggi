package janggi.domain.piece.impl;

import janggi.domain.piece.Team;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Pieces;
import janggi.domain.piece.Position;
import java.util.function.Consumer;

public class None extends Piece {
    public None() {
        super("ㅁ", Team.NONE);
    }

    @Override
    public boolean isNone() {
        return true;
    }

    @Override
    public Consumer<Pieces> getMovableValidator(
            final Position beforePosition,
            final Position afterPosition) {
        throw new IllegalArgumentException("빈 칸은 이동할 수 없습니다.");
    }

    @Override
    public int getScore() {
        throw new IllegalStateException("빈 칸의 점수를 구할 수 없습니다");
    }
}
