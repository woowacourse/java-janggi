package domain.piece.route;

import domain.MovingPattern;
import domain.position.JanggiPosition;
import org.junit.jupiter.api.Test;

import java.util.List;

import static domain.MovingPattern.*;
import static org.assertj.core.api.Assertions.assertThat;

class RouteTest {

    @Test
    void 경로에_존재하는_모든_위치를_알_수_있다() {
        // given
        Route route = new Route(UP, DIAGONAL_UP_RIGHT);
        JanggiPosition origin = new JanggiPosition(7, 5);

        // when
        List<JanggiPosition> positions = route.getPositionsOnRouteFrom(origin);

        // then
        assertThat(positions).containsAll(
                List.of(new JanggiPosition(6, 5), new JanggiPosition(5, 6))
        );
    }

    @Test
    void 경로가_특정_방향으로만_이동하는지_확인할_수_있다() {
        // given
        Route route = new Route(UP);

        // when & then
        assertThat(route.isSameDirectionWith(
                new JanggiPosition(7, 5),
                new JanggiPosition(4, 5)
        )).isTrue();
    }

    @Test
    void 경로가_특정_방향으로_몇_번_이동하는지_확인할_수_있다() {
        // given
        Route route = new Route(UP);

        // when & then
        assertThat(route.getMoveCount(
                new JanggiPosition(7, 5),
                new JanggiPosition(4, 5)
        )).isEqualTo(3);
    }

    @Test
    void 경로를_따라_이동하면_도착지에_도착하는지_확인할_수_있다() {
        // given
        Route route = new Route(UP, UP, UP);

        // when & then
        assertThat(route.isReachableByRoute(
                new JanggiPosition(7, 5),
                new JanggiPosition(4, 5)
        )).isTrue();
    }

    @Test
    void 경로의_방향이_포함되는지_확인할_수_있다() {
        // given
        Route route = new Route(UP, UP);
        List<MovingPattern> directions = List.of(UP, DOWN, LEFT, RIGHT);

        // when & then
        assertThat(route.isDirectionContainsIn(directions))
                .isTrue();
    }
}
