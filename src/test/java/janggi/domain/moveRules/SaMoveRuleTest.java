package janggi.domain.moveRules;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Direction;
import janggi.domain.Route;
import janggi.domain.Team;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SaMoveRuleTest {

    @Test
    @DisplayName("사는 직선과 대각선으로 갈 수 있다")
    void 사의_이동규칙() {
        //given
        MoveRule moveRule = new SaMoveRule();
        Team team = Team.CHO;
        Route route1 = new Route(List.of(Direction.NORTH));
        Route route2 = new Route(List.of(Direction.EAST));
        Route route3 = new Route(List.of(Direction.SOUTH));
        Route route4 = new Route(List.of(Direction.WEST));
        Route route5 = new Route(List.of(Direction.NORTH_WEST));
        Route route6 = new Route(List.of(Direction.NORTH_EAST));
        Route route7 = new Route(List.of(Direction.SOUTH_WEST));
        Route route8 = new Route(List.of(Direction.SOUTH_EAST));
        List<Route> routes = List.of(route1, route2, route3, route4, route5, route6, route7, route8);

        //when
        List<Route> saRoutes = moveRule.findRoutes(team);

        //then
        assertThat(saRoutes).isEqualTo(routes);
    }
}
