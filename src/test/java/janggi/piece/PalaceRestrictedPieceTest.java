package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import janggi.board.point.Point;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PalaceRestrictedPieceTest {

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
        TestPalaceRestrictedPiece palaceRestrictedPiece = new TestPalaceRestrictedPiece(Camp.CHU);
        Point fromPoint = new Point(fromX, fromY);
        Point toPoint = new Point(toX, toY);

        // when
        boolean isDiagonalPalaceMove = palaceRestrictedPiece.isDiagonalPalaceMove(fromPoint, toPoint);

        // then
        assertThat(isDiagonalPalaceMove)
                .isSameAs(expected);
    }

    @DisplayName("궁에 고립된 기물은 궁 안에서 이동할 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "3, 9, 4, 8",
            "5, 9, 4, 8",
            "3, 7, 5, 9",
            "5, 9, 3, 7",
    })
    void validateMoveTest(int fromX, int fromY, int toX, int toY) {
        // given
        TestPalaceRestrictedPiece palaceRestrictedPiece = new TestPalaceRestrictedPiece(Camp.CHU);
        Point fromPoint = new Point(fromX, fromY);
        Point toPoint = new Point(toX, toY);

        // when & then
        assertThatCode(() -> palaceRestrictedPiece.validateMove(fromPoint, toPoint, Set.of()))
                .doesNotThrowAnyException();
    }

    @DisplayName("궁에 고립된 기물은 궁 밖으로 이동할 수 없다.")
    @ParameterizedTest
    @CsvSource({
//            "0, 0, 0, 1",
//            "0, 1, 0, 0",
//            "3, 5, 4, 5",
//            "4, 5, 3, 5",
            "3, 2, 0, 0",
//            "3, 5, 3, 2"
    })
    void shouldThrowException_WhenValidateMoveOutsidePalace(int fromX, int fromY, int toX, int toY) {
        // given
        TestPalaceRestrictedPiece palaceRestrictedPiece = new TestPalaceRestrictedPiece(Camp.CHU);
        Point fromPoint = new Point(fromX, fromY);
        Point toPoint = new Point(toX, toY);

        // when & then
        assertThatCode(() -> palaceRestrictedPiece.validateMove(fromPoint, toPoint, Set.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("궁 안에서만 이동할 수 있습니다.");
    }

    static class TestPalaceRestrictedPiece extends PalaceRestrictedPiece {

        public TestPalaceRestrictedPiece(Camp camp) {
            super(camp);
        }

        @Override
        public Set<Point> findRoute(Point fromPoint, Point toPoint) {
            return Set.of();
        }

        @Override
        protected void validatePalaceRestrictedMove(Point fromPoint, Point toPoint) {
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
