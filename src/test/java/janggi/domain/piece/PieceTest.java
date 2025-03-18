package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import janggi.domain.Position;
import janggi.domain.ReplaceUnderBar;
import janggi.domain.Side;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

@ReplaceUnderBar
class PieceTest {

    private static final Side DEFAULT_SIDE = Side.HAN;
    private static final Position DEFAULT_POSITION = new Position(1, 2);

    @ParameterizedTest
    @EnumSource(value = Side.class)
    void 진영을_가진다(Side side) {
        Piece piece = FakePiece.moveable(side, DEFAULT_POSITION);
        assertThat(piece.getSide()).isEqualTo(side);
    }

    @Test
    void 위치를_가진다() {
        Piece piece = FakePiece.moveable(DEFAULT_SIDE, new Position(1, 2));

        assertThat(piece.getPosition()).isEqualTo(new Position(1, 2));
    }

    @Test
    void 현재_위치로_움직일_수_없다() {
        FakePiece piece = FakePiece.moveable(DEFAULT_SIDE, DEFAULT_POSITION);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> piece.move(DEFAULT_POSITION.getX(), DEFAULT_POSITION.getY()))
                .withMessage("현재 위치로 이동할 수 없습니다.");
    }

    @Test
    void 움직일_수_없는_위치로_움직일_수_없다() {
        FakePiece piece = FakePiece.unMoveable(DEFAULT_SIDE, DEFAULT_POSITION);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> piece.move(DEFAULT_POSITION.getX() + 1, DEFAULT_POSITION.getY() + 1))
                .withMessage("해당 위치로 이동할 수 없습니다.");
    }

    private static class FakePiece extends Piece {

        private final boolean isMoveable;

        private FakePiece(Side side, Position position, boolean isMoveable) {
            super(side, position);
            this.isMoveable = isMoveable;
        }

        private static FakePiece moveable(Side side, Position position) {
            return new FakePiece(side, position, true);
        }

        private static FakePiece unMoveable(Side side, Position position) {
            return new FakePiece(side, position, false);
        }

        @Override
        protected boolean isMoveable(int x, int y) {
            return this.isMoveable;
        }
    }
}
