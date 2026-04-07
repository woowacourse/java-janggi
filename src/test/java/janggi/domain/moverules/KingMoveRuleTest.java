package janggi.domain.moverules;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.common.Direction;
import janggi.domain.common.Team;
import janggi.domain.piece.moverules.KingMoveRule;
import janggi.domain.piece.moverules.MoveRule;
import janggi.domain.route.Route;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class KingMoveRuleTest {

    @Test
    @DisplayName("왕은 직선과 대각선으로 갈 수 있다.")
    void 왕의_이동규칙() {
        //given
        MoveRule moveRule = new KingMoveRule();
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
        List<Route> kingRoutes = moveRule.findRoutes(team);

        //then
        assertThat(kingRoutes).isEqualTo(routes);
    }
}
