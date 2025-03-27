package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.board.Board;
import janggi.board.point.Point;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PalaceRestrictedPieceTest {

    @DisplayName("궁 밖에서 이동하는지 확인한다.")
    @ParameterizedTest
    @CsvSource({
            "0, 0, 0, 1, true",
            "0, 1, 0, 0, true",
            "3, 5, 4, 5, true",
            "4, 5, 3, 5, true",
            "3, 9, 4, 8, false",
            "5, 9, 4, 8, false",
            "3, 7, 5, 9, false",
            "5, 9, 3, 7, false"
    })
    void isBothOutsidePalaceTest(int fromX, int fromY, int toX, int toY, boolean expected) {
        // given
        Board board = new Board();
        TestPalaceRestrictedPiece palaceRestrictedPiece = new TestPalaceRestrictedPiece(Camp.CHU, board);
        Point fromPoint = new Point(fromX, fromY);
        Point toPoint = new Point(toX, toY);

        // when
        boolean isBothOutsidePalace = palaceRestrictedPiece.isBothOutsidePalace(fromPoint, toPoint);

        // then
        assertThat(isBothOutsidePalace)
                .isSameAs(expected);
    }

    static class TestPalaceRestrictedPiece extends PalaceRestrictedPiece {

        public TestPalaceRestrictedPiece(Camp camp, Board board) {
            super(camp, board);
        }

        @Override
        public void validateMove(Point fromPoint, Point toPoint) {
        }

        @Override
        protected boolean canCapture(Piece otherPiece) {
            return false;
        }

        @Override
        public PieceSymbol getPieceSymbol() {
            return null;
        }

        @Override
        public int getPoint() {
            return 0;
        }
    }
}
