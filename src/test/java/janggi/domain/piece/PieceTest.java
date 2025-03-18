package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

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
        Piece piece = new RawPiece(side, DEFAULT_POSITION);
        assertThat(piece.getSide()).isEqualTo(side);
    }

    @Test
    void 위치를_가진다() {
        Piece piece = new RawPiece(DEFAULT_SIDE, new Position(1, 2));

        assertThat(piece.getPosition()).isEqualTo(new Position(1, 2));
    }

    private static class RawPiece extends Piece {

        private RawPiece(Side side, Position position) {
            super(side, position);
        }

        @Override
        protected void validateMovable(int x, int y) {
            throw new IllegalStateException("이 메서드는 테스트할 수 없습니다.");
        }
    }
}
