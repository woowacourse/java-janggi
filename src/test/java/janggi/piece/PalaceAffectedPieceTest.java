package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.board.Board;
import janggi.board.point.Point;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PalaceAffectedPieceTest {

    @DisplayName("궁 안에 있는지 확인한다.")
    @ParameterizedTest
    @CsvSource({
            "3, 9, true", "4, 9, true", "5, 9, true",
            "3, 8, true", "4, 8, true", "5, 8, true",
            "3, 7, true", "4, 7, true", "5, 7, true",
            "3, 2, true", "4, 2, true", "5, 2, true",
            "3, 1, true", "4, 1, true", "5, 1, true",
            "3, 0, true", "4, 0, true", "5, 0, true",

            "3, 5, false", "4, 5, false", "5, 5, false"
    })
    void isInsidePalaceTest(int x, int y, boolean expected) {
        // given
        Board board = new Board();
        TestPalaceAffectedPiece palaceAffectedPiece = new TestPalaceAffectedPiece(Camp.CHU, board);

        // when
        boolean isInsidePalace = palaceAffectedPiece.isInsidePalace(new Point(x, y));

        // then
        assertThat(isInsidePalace)
                .isSameAs(expected);
    }

    @DisplayName("궁 안에서 대각선으로 움직일 수 있는지 확인한다.")
    @ParameterizedTest
    @CsvSource({
            "3, 9, 4, 8, true",
            "5, 9, 4, 8, true",
            "3, 7, 5, 9, true",
            "5, 9, 3, 7, true",
            "3, 8, 4, 9, false",
            "4, 9, 5, 8, false",
            "5, 8, 4, 7, false",
            "4, 7, 3, 8, false"
    })
    void isDiagonalPalaceMoveTest(int fromX, int fromY, int toX, int toY, boolean expected) {
        // given
        Board board = new Board();
        TestPalaceAffectedPiece palaceAffectedPiece = new TestPalaceAffectedPiece(Camp.CHU, board);
        Point fromPoint = new Point(fromX, fromY);
        Point toPoint = new Point(toX, toY);

        // when
        boolean isDiagonalPalaceMove = palaceAffectedPiece.isDiagonalPalaceMove(fromPoint, toPoint);

        // then
        assertThat(isDiagonalPalaceMove)
                .isSameAs(expected);
    }

    static class TestPalaceAffectedPiece extends PalaceAffectedPiece {

        public TestPalaceAffectedPiece(Camp camp, Board board) {
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
