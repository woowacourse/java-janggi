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

class ElephantTest {

    @DisplayName("상은 직선으로 한 칸, 대각선으로 두 칸 움직이지 않은 경우 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource({"5, 7", "7, 5", "3, 5", "7, 7", "5, 5", "8, 6"})
    void shouldThrowException_WhenInvalidMove(int toX, int toY) {
        // given
        Elephant elephant = new Elephant(Camp.HAN);
        Point from = new Point(5, 5);
        Point to = new Point(toX, toY);

        // when & then
        assertThatCode(() -> elephant.validateMove(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상은 직선으로 한 칸, 대각선으로 두 칸 움직여야 합니다.");
    }

    @DisplayName("상은 직선으로 한 칸, 대각선으로 두 칸 움직일 수 있다.")
    @ParameterizedTest
    @CsvSource({"7, 8", "8, 7", "7, 2", "2, 7", "8, 3", "3, 8", "3, 2", "2, 3"})
    void validateMoveTest(int toX, int toY) {
        // given
        Elephant elephant = new Elephant(Camp.HAN);
        Point from = new Point(5, 5);
        Point to = new Point(toX, toY);

        // when & then
        assertThatCode(() -> elephant.validateMove(from, to))
                .doesNotThrowAnyException();
    }

    @DisplayName("상은 직선으로 한 칸, 대각선으로 두 칸 움직일 때 기물에 막힌 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenBlocked() {
        // given
        Elephant elephant = new Elephant(Camp.CHU);
        Set<Piece> piecesOnRoute = Set.of(new Soldier(Camp.CHU));

        // when & then
        assertThatCode(() -> elephant.validateRouteObstacles(piecesOnRoute))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상은 기물을 넘어서 이동할 수 없습니다.");
    }


    @DisplayName("출발 좌표부터 도착 좌표까지의 경로를 찾는다.")
    @Test
    void findRouteTest() {
        // given
        Piece elephant = new Elephant(Camp.CHU);
        Point from = new Point(5, 5);
        Point to = new Point(7, 8);

        // when & then
        assertThat(elephant.findRoute(from, to))
                .containsExactlyInAnyOrder(new Point(5, 6), new Point(6, 7));
    }
}
