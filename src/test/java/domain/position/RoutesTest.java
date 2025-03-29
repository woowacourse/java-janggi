package domain.position;

import static org.assertj.core.api.Assertions.assertThat;

import domain.unit.Team;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RoutesTest {

    @Test
    @DisplayName("해당 목적지로 갈 수 있는 경로가 있는지 판단한다")
    void test1() {
        // given
        Route route = Route.of(List.of(
                Position.of(0, 0),
                Position.of(0, 1),
                Position.of(0, 2)));
        Routes routes = Routes.of(List.of(route));

        // when
        boolean hasRoute = routes.hasRouteTo(Position.of(0, 4), Position.of(0, 0));
        boolean hasNoRoute = routes.hasRouteTo(Position.of(0, 4), Position.of(0, 1));

        // then
        assertThat(hasRoute).isTrue();
        assertThat(hasNoRoute).isFalse();
    }

    @Test
    @DisplayName("조건에 맞게 경로를 필터링한다")
    void test2() {
        // given
        Route route1 = Route.of(List.of(
                Position.of(0, 2),
                Position.of(0, 1),
                Position.of(0, 0)));
        Route route2 = Route.of(List.of(
                Position.of(0, 4),
                Position.of(0, 5),
                Position.of(0, 6)));
        Routes routes = Routes.of(List.of(route1, route2));

        // when
        Routes filtered = routes.filterByCondition(route -> route.isMovingForward(Position.of(0, 3), Team.CHO));

        // then
        assertThat(filtered.getRoutes()).hasSize(1);
    }
}
