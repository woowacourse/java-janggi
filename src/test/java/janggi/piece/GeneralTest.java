package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import janggi.board.point.Point;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class GeneralTest {

    @DisplayName("장군이 궁을 벗어난 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenInvalidMove() {
        // given
        General general = new General(Camp.HAN);
        Point fromPoint = new Point(3, 3);
        Point toPoint = new Point(3, 0);

        // when & then
        assertThatCode(() -> general.validateMove(fromPoint, toPoint, Set.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("궁 안에서만 이동할 수 있습니다.");
    }

    @DisplayName("장군이 상화좌우로 한 칸 혹은 대각선으로 한 칸만 움직이지 않은 경우 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource({
            "3, 0",
            "5, 2",
            "5, 0",
    })
    void shouldThrowException_WhenInvalidMove(int toX, int toY) {
        // given
        General general = new General(Camp.HAN);
        Point fromPoint = new Point(3, 2);
        Point toPoint = new Point(toX, toY);

        // when & then
        assertThatCode(() -> general.validateMove(fromPoint, toPoint, Set.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("장군은 직선 또는 대각선 한 칸만 이동할 수 있습니다.");
    }

    @DisplayName("장군은 상하좌우로 한 칸 혹은 대각선으로 한 칸만 움직일 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "3, 0, 3, 1",
            "3, 0, 4, 0",
            "3, 0, 4, 1",
            "4, 1, 5, 2"
    })
    void validateMoveTest(int fromX, int fromY, int toX, int toY) {
        // given
        General general = new General(Camp.HAN);
        Point fromPoint = new Point(fromX, fromY);
        Point toPoint = new Point(toX, toY);

        // when & then
        assertThatCode(() -> general.validateMove(fromPoint, toPoint, Set.of()))
                .doesNotThrowAnyException();
    }

    @DisplayName("장군의 경로를 반환한다.")
    @ParameterizedTest
    @CsvSource({
            "3, 0, 3, 1",
            "3, 0, 4, 0",
            "3, 0, 4, 1",
            "4, 1, 5, 2"
    })
    void findRouteTest(int fromX, int fromY, int toX, int toY) {
        // given
        General general = new General(Camp.HAN);
        Point fromPoint = new Point(fromX, fromY);
        Point toPoint = new Point(toX, toY);

        // when
        Set<Point> route = general.findRoute(fromPoint, toPoint);

        // then
        assertThat(route)
                .isEmpty();
    }

    @DisplayName("장군은 같은 진영의 기물을 잡을 수 없다.")
    @ParameterizedTest
    @CsvSource({
            "HAN, true",
            "CHU, false"
    })
    void canCatchTest(Camp camp, boolean expected) {
        // given
        General general = new General(camp);

        // when
        boolean canCapture = general.canCapture(new SoldierJol());

        // then
        assertThat(canCapture)
                .isSameAs(expected);
    }

    @DisplayName("자신의 기물 형태를 반환한다.")
    @Test
    void getPieceSymbolTest() {
        // given
        General general = new General(Camp.CHU);

        // when
        PieceSymbol pieceSymbol = general.getPieceSymbol();

        // then
        assertThat(pieceSymbol)
                .isSameAs(PieceSymbol.GENERAL);
    }

    @DisplayName("자신의 점수를 반환한다.")
    @Test
    void getPointTest() {
        // given
        General general = new General(Camp.CHU);

        // when
        int point = general.getPoint();

        // then
        assertThat(point)
                .isEqualTo(0);
    }
}
