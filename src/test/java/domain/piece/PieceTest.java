package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class PieceTest {

    @Test
    void 기물_객체를_생성할_수_있다() {
        // given
        final Position position = Position.of(1, 2);

        // when
        final TestPiece piece = new TestPiece(1, 2);

        // then
        assertThat(piece.getPosition()).isEqualTo(position);
    }

    static class TestPiece extends Piece {
        public TestPiece(int row, int column) {
            super(row, column);
        }

        @Override
        public String getName() {
            return "";
        }
    }
}
