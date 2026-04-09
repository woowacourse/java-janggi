package janggi.domain.moverules;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.common.Direction;
import janggi.domain.common.Team;
import janggi.domain.piece.moverules.MoveRule;
import janggi.domain.piece.moverules.SangMoveRule;
import janggi.domain.route.Route;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SangMoveRuleTest {

    @Test
    @DisplayName("상은 직선으로 한 번 대각선으로 두 번 갈 수 있다")
    void 상의_이동규칙() {
        //given
        MoveRule moveRule = new SangMoveRule();
        Team team = Team.CHO;
        Route route1 = new Route(List.of(Direction.UP, Direction.UP_LEFT, Direction.UP_LEFT));
        Route route2 = new Route(List.of(Direction.UP, Direction.UP_RIGHT, Direction.UP_RIGHT));
        Route route3 = new Route(List.of(Direction.RIGHT, Direction.UP_RIGHT, Direction.UP_RIGHT));
        Route route4 = new Route(List.of(Direction.RIGHT, Direction.DOWN_RIGHT, Direction.DOWN_RIGHT));
        Route route5 = new Route(List.of(Direction.DOWN, Direction.DOWN_RIGHT, Direction.DOWN_RIGHT));
        Route route6 = new Route(List.of(Direction.DOWN, Direction.DOWN_LEFT, Direction.DOWN_LEFT));
        Route route7 = new Route(List.of(Direction.LEFT, Direction.DOWN_LEFT, Direction.DOWN_LEFT));
        Route route8 = new Route(List.of(Direction.LEFT, Direction.UP_LEFT, Direction.UP_LEFT));
        List<Route> routes = List.of(route1, route2, route3, route4, route5, route6, route7, route8);

        //when
        List<Route> sangRoutes = moveRule.findRoutes(team);

        //then
        assertThat(sangRoutes).isEqualTo(routes);
    }
}
