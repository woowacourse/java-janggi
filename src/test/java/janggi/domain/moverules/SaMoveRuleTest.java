package janggi.domain.moverules;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.common.Direction;
import janggi.domain.common.Team;
import janggi.domain.piece.moverules.MoveRule;
import janggi.domain.piece.moverules.SaMoveRule;
import janggi.domain.route.Route;
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
        Route route1 = new Route(List.of(Direction.UP));
        Route route2 = new Route(List.of(Direction.RIGHT));
        Route route3 = new Route(List.of(Direction.DOWN));
        Route route4 = new Route(List.of(Direction.LEFT));
        Route route5 = new Route(List.of(Direction.UP_LEFT));
        Route route6 = new Route(List.of(Direction.UP_RIGHT));
        Route route7 = new Route(List.of(Direction.DOWN_LEFT));
        Route route8 = new Route(List.of(Direction.DOWN_RIGHT));
        List<Route> routes = List.of(route1, route2, route3, route4, route5, route6, route7, route8);

        //when
        List<Route> saRoutes = moveRule.findRoutes(team);

        //then
        assertThat(saRoutes).isEqualTo(routes);
    }
}
