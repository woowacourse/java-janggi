package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.ReplaceUnderBar;
import janggi.domain.Side;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.*;

@ReplaceUnderBar
class PieceTest {

    private static final Side DEFAULT_SIDE = Side.HAN;
    private static final Position DEFAULT_POSITION = new Position(1, 2);

    @ParameterizedTest
    @EnumSource(value = Side.class)
    void 진영을_가진다(Side side) {
        Piece piece = new RawPiece(side, DEFAULT_POSITION);
        assertThat(piece.getSide()).isEqualTo(side);
    }

    @Test
    void 위치를_가진다() {
        Piece piece = new RawPiece(DEFAULT_SIDE, new Position(1, 2));

        assertThat(piece.getPosition()).isEqualTo(new Position(1, 2));
    }

    @Test
    void 현재_위치로_움직일_수_없다() {
        RawPiece piece = new RawPiece(DEFAULT_SIDE, DEFAULT_POSITION);

        assertThatIllegalArgumentException()
            .isThrownBy(() -> piece.move(DEFAULT_POSITION.getX(), DEFAULT_POSITION.getY()))
            .withMessage("현재 위치로 이동할 수 없습니다.");
    }

    private static class RawPiece extends Piece {

        private RawPiece(Side side, Position position) {
            super(side, position);
        }

        @Override
        protected void validateMovable(int x, int y) {
            return;
        }
    }
}
