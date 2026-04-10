package domain;

import java.util.List;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MovableRoutesTest {

    @Test
    void 이동_가능_경로_목록과_왕_목적지_여부를_담는다() {
        List<Route> routes = List.of(
                new Route(Position.of(0, 0), Position.of(1, 0), List.of()));

        MovableRoutes movable = new MovableRoutes(routes, true);

        assertThat(movable.routes()).isEqualTo(routes);
        assertThat(movable.hasKingDestination()).isTrue();
    }

    @Test
    void 왕_목적지가_없으면_왕_목적지_여부는_거짓이다() {
        MovableRoutes movable = new MovableRoutes(List.of(), false);

        assertThat(movable.routes()).isEmpty();
        assertThat(movable.hasKingDestination()).isFalse();
    }
}
