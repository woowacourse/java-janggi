package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import janggi.board.point.Point;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class SoldierByeongTest {

    @DisplayName("병은 뒤로 갈 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenMoveBackward() {
        // given
        SoldierByeong soldierByeong = new SoldierByeong();

        // when & then
        assertThatCode(() -> soldierByeong.validateMove(new Point(4, 4), new Point(4, 5), Set.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("병은 뒤로 갈 수 없으며, 앞 또는 양 옆으로 한 칸만 움직일 수 있습니다.");
    }

    @DisplayName("병은 대각선으로 움직일 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenMoveDiagonal() {
        // given
        SoldierByeong soldierByeong = new SoldierByeong();

        // when & then
        assertThatCode(() -> soldierByeong.validateMove(new Point(4, 4), new Point(3, 3), Set.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("병은 뒤로 갈 수 없으며, 앞 또는 양 옆으로 한 칸만 움직일 수 있습니다.");
    }

    @DisplayName("병은 앞 또는 양 옆으로 한 칸만 움직일 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "3, 4",
            "5, 4",
            "4, 3"
    })
    void validateMoveTest(int toX, int toY) {
        // given
        SoldierByeong soldierByeong = new SoldierByeong();

        // when & then
        assertThatCode(() -> soldierByeong.validateMove(new Point(4, 4), new Point(toX, toY), Set.of()))
                .doesNotThrowAnyException();
    }

    @DisplayName("병은 같은 진영의 기물을 잡을 수 없다.")
    @ParameterizedTest
    @CsvSource({
            "CHU, true",
            "HAN, false"
    })
    void canCaptureTest(Camp camp, boolean expected) {
        // given
        SoldierByeong soldierByeong = new SoldierByeong();

        // when
        boolean canCapture = soldierByeong.canCapture(new Horse(camp));

        // then
        assertThat(canCapture)
                .isSameAs(expected);
    }

    @DisplayName("병은 같은 진영의 기물을 잡을 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenCatchSameCamp() {
        // given
        SoldierByeong soldierByeong = new SoldierByeong();

        // when & then
        assertThatCode(() -> soldierByeong.validateCatch(new SoldierByeong()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 기물을 잡을 수 없습니다.");
    }

    @DisplayName("병의 경로를 반환한다.")
    @ParameterizedTest
    @CsvSource({
            "3, 0, 3, 1",
            "3, 0, 4, 0",
            "3, 0, 4, 1",
            "4, 1, 5, 2"
    })
    void findRouteTest(int fromX, int fromY, int toX, int toY) {
        // given
        SoldierByeong soldierByeong = new SoldierByeong();
        Point fromPoint = new Point(fromX, fromY);
        Point toPoint = new Point(toX, toY);

        // when
        Set<Point> route = soldierByeong.findRoute(fromPoint, toPoint);

        // then
        assertThat(route)
                .isEmpty();
    }

    @DisplayName("병이 정상적으로 생성되는지 테스트한다.")
    @Test
    void createTest() {
        // when & then
        assertThatCode(SoldierByeong::new)
                .doesNotThrowAnyException();
    }

    @DisplayName("자신의 기물 형태를 반환한다.")
    @Test
    void getPieceSymbolTest() {
        // given
        SoldierByeong soldierByeong = new SoldierByeong();

        // when
        PieceSymbol pieceSymbol = soldierByeong.getPieceSymbol();

        // then
        assertThat(pieceSymbol)
                .isSameAs(PieceSymbol.SOLDIER_BYEONG);
    }

    @DisplayName("자신의 점수를 반환한다.")
    @Test
    void getPointTest() {
        // given
        SoldierByeong soldierByeong = new SoldierByeong();

        // when
        int point = soldierByeong.getPoint();

        // then
        assertThat(point)
                .isEqualTo(2);
    }

    @Nested
    class WithPalaceTest {

        @DisplayName("병은 궁 내부에서 허용된 대각선으로 한 칸 앞으로 이동할 수 있다.")
        @ParameterizedTest
        @CsvSource({
                "3, 2, 4, 1",
                "5, 2, 4, 1",
                "4, 1, 3, 0",
                "4, 1, 5, 0",
        })
        void isDiagonalPalaceMoveAllowedTest(int fromX, int fromY, int toX, int toY) {
            // given
            SoldierByeong soldierByeong = new SoldierByeong();
            Point fromPoint = new Point(fromX, fromY);
            Point toPoint = new Point(toX, toY);

            // when & then
            assertThatCode(() -> soldierByeong.validateMove(fromPoint, toPoint, Set.of()))
                    .doesNotThrowAnyException();
        }

        @DisplayName("병이 대각선으로 한 칸 앞으로 이동하려고 할 때, 허용되지 않은(대각선 경로가 없는) 경로인 경우 예외가 발생한다.")
        @ParameterizedTest
        @CsvSource({
                "3, 1, 4, 0",
                "4, 0, 5, 1",
                "5, 1, 4, 2",
                "4, 2, 3, 1",
        })
        void shouldThrowException_WhenDiagonalMoveOutsidePalace(int fromX, int fromY, int toX, int toY) {
            // given
            SoldierByeong soldierByeong = new SoldierByeong();
            Point fromPoint = new Point(fromX, fromY);
            Point toPoint = new Point(toX, toY);

            // when & then
            assertThatCode(() -> soldierByeong.validateMove(fromPoint, toPoint, Set.of()))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("병이 대각선으로 이동하려면, 허용된 지점에서만 가능합니다.");
        }

        @DisplayName("궁에서도 기존 규칙대로 움직일 수 있다.")
        @ParameterizedTest
        @CsvSource({
                "3, 2, 3, 1",
                "3, 1, 3, 0",
                "5, 2, 5, 1",
                "5, 1, 5, 0",
        })
        void validateMoveTest(int fromX, int fromY, int toX, int toY) {
            // given
            SoldierByeong soldierByeong = new SoldierByeong();
            Point fromPoint = new Point(fromX, fromY);
            Point toPoint = new Point(toX, toY);

            // when & then
            assertThatCode(() -> soldierByeong.validateMove(fromPoint, toPoint, Set.of()))
                    .doesNotThrowAnyException();
        }

        @DisplayName("궁에서 대각선 뒤로 움직일 경우 예외가 발생한다.")
        @ParameterizedTest
        @CsvSource({
                "4, 1, 3, 2",
                "4, 1, 5, 2",
                "3, 0, 4, 1",
                "5, 0, 4, 1"
        })
        void shouldThrowException_WhenMoveBackward(int fromX, int fromY, int toX, int toY) {
            // given
            SoldierByeong soldierByeong = new SoldierByeong();
            Point fromPoint = new Point(fromX, fromY);
            Point toPoint = new Point(toX, toY);

            // when & then
            assertThatCode(() -> soldierByeong.validateMove(fromPoint, toPoint, Set.of()))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("병은 대각선으로 이동할 수 있는 경우 뒤로 갈 수 없으며, 한 칸만 이동할 수 있습니다.");
        }

        @DisplayName("궁에서 대각선으로 여러 칸 움직일 경우 예외가 발생한다.")
        @ParameterizedTest
        @CsvSource({
                "3, 2, 5, 0",
                "5, 2, 3, 0",
                "3, 0, 5, 2",
                "5, 0, 3, 2"
        })
        void shouldThrowException_WhenMoveDiagonalMultipleSteps(int fromX, int fromY, int toX, int toY) {
            // given
            SoldierByeong soldierByeong = new SoldierByeong();
            Point fromPoint = new Point(fromX, fromY);
            Point toPoint = new Point(toX, toY);

            // when & then
            assertThatCode(() -> soldierByeong.validateMove(fromPoint, toPoint, Set.of()))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("병은 대각선으로 이동할 수 있는 경우 뒤로 갈 수 없으며, 한 칸만 이동할 수 있습니다.");
        }
    }
}
