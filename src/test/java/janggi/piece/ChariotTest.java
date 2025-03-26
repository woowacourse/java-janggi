package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import janggi.board.Point;
import janggi.camp.Camp;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ChariotTest {

    @DisplayName("차는 상하좌우로 움직이지 않은 경우 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource({"2, 2", "4, 2", "2, 4", "4, 4"})
    void shouldThrowException_WhenInvalidMove(int toX, int toY) {
        // given
        Chariot chariot = new Chariot(Camp.HAN);
        Point from = new Point(3, 3);
        Point to = new Point(toX, toY);

        // when & then
        assertThatCode(() -> chariot.validateMove(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("차는 수평 혹은 수직으로만 움직여야 합니다.");
    }

    @DisplayName("차는 상하좌우 무제한으로 움직일 수 있다.")
    @ParameterizedTest
    @CsvSource({"3, 0", "3, 5", "0, 3", "5, 3"})
    void validateMoveTest(int toX, int toY) {
        // given
        Chariot chariot = new Chariot(Camp.HAN);
        Point from = new Point(3, 3);
        Point to = new Point(toX, toY);

        // when & then
        assertThatCode(() -> chariot.validateMove(from, to))
                .doesNotThrowAnyException();
    }

    @DisplayName("차는 상하좌우로 움직일 때 기물에 막힌 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenBlocked() {
        // given
        Chariot chariot = new Chariot(Camp.CHU);
        Set<Piece> piecesOnRoute = Set.of(new Soldier(Camp.CHU));

        // when & then
        assertThatCode(() -> chariot.validateRouteObstacles(piecesOnRoute))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("차는 기물을 넘어 이동할 수 없습니다.");
    }

    @DisplayName("특정 진영을 선택할 수 없는 경우 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource({"CHU, HAN", "HAN, CHU"})
    void shouldThrowException_WhenSelectOtherCampPiece(Camp camp, Camp otherCamp) {
        // given
        Chariot chariot = new Chariot(otherCamp);

        // when & then
        assertThatCode(() -> chariot.validateSelect(camp))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("다른 진영의 기물을 선택할 수 없습니다.");
    }

    @DisplayName("출발 좌표부터 도착 좌표까지의 경로를 찾는다.")
    @Test
    void findRouteTest() {
        // given
        Piece chariot = new Chariot(Camp.CHU);
        Point from = new Point(5, 5);
        Point to = new Point(5, 9);

        // when & then
        assertThat(chariot.findRoute(from, to))
                .containsExactlyInAnyOrder(new Point(5, 6), new Point(5, 7), new Point(5, 8));
    }
}
