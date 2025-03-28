package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.board.point.Point;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PalaceAffectedPieceTest {

    @DisplayName("궁 밖에 있는지 확인한다.")
    @ParameterizedTest
    @CsvSource({
            "3, 9, false", "4, 9, false", "5, 9, false",
            "3, 8, false", "4, 8, false", "5, 8, false",
            "3, 7, false", "4, 7, false", "5, 7, false",
            "3, 2, false", "4, 2, false", "5, 2, false",
            "3, 1, false", "4, 1, false", "5, 1, false",
            "3, 0, false", "4, 0, false", "5, 0, false",

            "3, 5, true", "4, 5, true", "5, 5, true"
    })
    void isInsidePalaceTest(int x, int y, boolean expected) {
        // given
        TestPalaceAffectedPiece palaceAffectedPiece = new TestPalaceAffectedPiece(Camp.CHU);

        // when
        boolean isInsidePalace = palaceAffectedPiece.isOutsidePalace(new Point(x, y));

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
        TestPalaceAffectedPiece palaceAffectedPiece = new TestPalaceAffectedPiece(Camp.CHU);
        Point fromPoint = new Point(fromX, fromY);
        Point toPoint = new Point(toX, toY);

        // when
        boolean isDiagonalPalaceMove = palaceAffectedPiece.isDiagonalPalaceMove(fromPoint, toPoint);

        // then
        assertThat(isDiagonalPalaceMove)
                .isSameAs(expected);
    }

    @DisplayName("궁에 영향을 받는 기물은 궁 안과 밖에서의 이동을 구분한다.")
    @ParameterizedTest
    @CsvSource({
            "4, 0, 4, 1, 궁 안에서의 이동입니다.",
            "4, 4, 4, 5, 궁 밖에서의 이동입니다."
    })
    void validatePalaceMoveTest(int fromX, int fromY, int toX, int toY, String expectedMessage) {
        // given
        TestPalaceAffectedPiece palaceAffectedPiece = new TestPalaceAffectedPiece(Camp.CHU);
        Point fromPoint = new Point(fromX, fromY);
        Point toPoint = new Point(toX, toY);

        // when & then
        assertThatThrownBy(() -> palaceAffectedPiece.validateMove(fromPoint, toPoint, Set.of()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage(expectedMessage);
    }

    static class TestPalaceAffectedPiece extends PalaceAffectedPiece {

        public TestPalaceAffectedPiece(Camp camp) {
            super(camp);
        }

        @Override
        public Set<Point> findRoute(Point fromPoint, Point toPoint) {
            return Set.of();
        }

        @Override
        protected void validatePalaceMove(Point fromPoint, Point toPoint) {
            throw new IllegalStateException("궁 안에서의 이동입니다.");
        }

        @Override
        protected void validateNonPalaceMove(Point fromPoint, Point toPoint) {
            throw new IllegalStateException("궁 밖에서의 이동입니다.");
        }

        @Override
        protected void validateObstacleOnRoute(Set<Piece> piecesOnRoute) {
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
