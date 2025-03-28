package janggi.domain.piece;

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
}
