package janggi.domain.moverules;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.common.Direction;
import janggi.domain.common.Team;
import janggi.domain.piece.moverules.ChaMoveRule;
import janggi.domain.piece.moverules.MoveRule;
import janggi.domain.route.Route;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChaMoveRuleTest {

    @Test
    @DisplayName("차는 직선으로 갈 수 있다")
    void 차의_이동규칙() {
        //given
        MoveRule moveRule = new ChaMoveRule();
        Team team = Team.CHO;
        Route route1 = new Route(List.of(Direction.UP));
        Route route2 = new Route(List.of(Direction.RIGHT));
        Route route3 = new Route(List.of(Direction.DOWN));
        Route route4 = new Route(List.of(Direction.LEFT));
        List<Route> routes = List.of(route1, route2, route3, route4);

        //when
        List<Route> chaRoutes = moveRule.findRoutes(team);

        //then
        assertThat(chaRoutes).isEqualTo(routes);
    }
}
