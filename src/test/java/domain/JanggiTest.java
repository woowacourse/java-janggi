package domain;

import domain.unit.Point;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JanggiTest {

    @Test
    @DisplayName("졸의 이동경로를 구한다.")
    void test1() {
        // given
        Janggi janggi = new Janggi();

        // when

        List<Route> routes = janggi.searchAvailableRoutes(new Point(0, 3));

        // then
        Assertions.assertThat(routes).hasSize(2);
    }

    @Test
    @DisplayName("포의 이동경로를 구한다.")
    void test2() {
        // given
        Janggi janggi = new Janggi();

        // when
        List<Route> routes = janggi.searchAvailableRoutes(new Point(1, 7));

        // then
        Assertions.assertThat(routes).hasSize(0);
    }

    @Test
    @DisplayName("경로 중에 기물이 있다면 거짓이다")
    void test3() {
        // given
        Janggi janggi = new Janggi();

        // when
        boolean isFalse = janggi.isAvailableRoute(Route.of(List.of
                (new Point(0, 1), new Point(0, 2), new Point(0, 3), new Point(0, 4))));

        // then
        Assertions.assertThat(isFalse).isFalse();
    }

    @Test
    @DisplayName("경로 중에 기물이 없다면 참이다")
    void test4() {
        // given
        Janggi janggi = new Janggi();

        // when
        boolean isTrue = janggi.isAvailableRoute(Route.of(List.of
                (new Point(0, 1), new Point(0, 2), new Point(0, 4))));

        // then
        Assertions.assertThat(isTrue).isTrue();
    }
}
