package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import janggi.domain.Point;
import janggi.domain.status.Team;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MaTest {

    @Test
    @DisplayName("마가 전진할 때, 경유지는 한 곳이다.")
    void straight_back_route() {
        // given
        Piece ma = new Ma(Team.CHO);
        Point from = Point.of(0,0);
        Point to = Point.of(2, 1);

        // when
        List<Point> route = ma.getRoute(from, to);

        // then
        assertThat(route.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("마가 좌우로 움직일 때, 경유지는 한 곳이다.")
    void left_right_route() {
        // given
        Piece ma = new Ma(Team.CHO);
        Point from = Point.of(0,0);
        Point to = Point.of(1, 2);

        // when
        List<Point> route = ma.getRoute(from, to);

        // then
        assertThat(route.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("이동할 수 없는 위치를 목적지로 할 경우 예외 발생")
    void destination_exception() {
        // given
        Piece ma = new Ma(Team.CHO);
        Point from = Point.of(0,0);
        Point to = Point.of(1, 1);

        // when & then
        assertThatThrownBy(() -> ma.getRoute(from, to))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
