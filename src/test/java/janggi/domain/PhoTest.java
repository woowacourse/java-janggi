package janggi.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class PhoTest {

    @ParameterizedTest
    @CsvSource(value = {"0:5", "8:5", "5:0", "5:9"}, delimiter = ':')
    @DisplayName("포가 움직일 때, 경유지는 두 곳이다.")
    void straight_back_route(int column, int row) {
        // given
        Piece pho = new Pho(Team.CHO);
        Point from = Point.of(5,5);
        Point to = Point.of(column, row);

        // when
        List<Point> route = pho.getRoute(from, to);

        // then
        assertThat(route.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("이동할 수 없는 위치를 목적지로 할 경우 예외 발생")
    void destination_exception() {
        // given
        Piece pho = new Pho(Team.CHO);
        Point from = Point.of(0, 0);
        Point to = Point.of(1, 1);
        // when & then
        assertThatThrownBy(() -> pho.getRoute(from, to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("경유지에 뛰어넘을 기물이 1개만 있으면 움직일 수 있다.")
    void can_pho_move() {

    }

    @Test
    @DisplayName("경유지에 뛰어넘을 기물이 2개 이상 있으면 움직일 수 없다.")
    void can_not_pho_move() {

    }

    @Test
    @DisplayName("경유지에 뛰어넘을 기물이 포일 경우 움직일 수 없다.")
    void huddle_is_pho_can_not_move() {

    }
}
