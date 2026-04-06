package janggi.domain.moveRules;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Direction;
import janggi.domain.Route;
import janggi.domain.Team;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SangMoveRuleTest {

    @Test
    @DisplayName("상은 직선으로 한 번 대각선으로 두 번 갈 수 있다")
    void 상_이동규칙() {
        //given
        MoveRule moveRule = new SangMoveRule();
        Team team = Team.CHO;
        Route route1 = new Route(List.of(Direction.NORTH, Direction.NORTH_WEST, Direction.NORTH_WEST));
        Route route2 = new Route(List.of(Direction.NORTH, Direction.NORTH_EAST, Direction.NORTH_EAST));
        Route route3 = new Route(List.of(Direction.EAST, Direction.NORTH_EAST, Direction.NORTH_EAST));
        Route route4 = new Route(List.of(Direction.EAST, Direction.SOUTH_EAST, Direction.SOUTH_EAST));
        Route route5 = new Route(List.of(Direction.SOUTH, Direction.SOUTH_EAST, Direction.SOUTH_EAST));
        Route route6 = new Route(List.of(Direction.SOUTH, Direction.SOUTH_WEST, Direction.SOUTH_WEST));
        Route route7 = new Route(List.of(Direction.WEST, Direction.SOUTH_WEST, Direction.SOUTH_WEST));
        Route route8 = new Route(List.of(Direction.WEST, Direction.NORTH_WEST, Direction.NORTH_WEST));
        List<Route> routes = List.of(route1, route2, route3, route4, route5, route6, route7, route8);

        //when
        List<Route> sangRoutes = moveRule.findRoutes(team);

        //then
        assertThat(sangRoutes).isEqualTo(routes);
    }
}
