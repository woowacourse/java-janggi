package janggi.domain.moverules;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.common.Direction;
import janggi.domain.common.Team;
import janggi.domain.piece.moverules.MoveRule;
import janggi.domain.piece.moverules.ZolMoveRule;
import janggi.domain.route.Route;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ZolMoveRuleTest {

    @Test
    @DisplayName("초나라 졸은 위양옆으로 갈 수 있다")
    void 초나라_졸의_이동규칙() {
        //given
        MoveRule moveRule = new ZolMoveRule();
        Team cho = Team.CHO;
        Route route1 = new Route(List.of(Direction.UP));
        Route route2 = new Route(List.of(Direction.LEFT));
        Route route3 = new Route(List.of(Direction.RIGHT));

        List<Route> routes = List.of(route1, route2, route3);

        //when
        List<Route> zolPaths = moveRule.findRoutes(cho);

        //then
        assertThat(zolPaths).isEqualTo(routes);
    }

    @Test
    @DisplayName("한나라 졸은 아래양옆으로 갈 수 있다")
    void 한나라_졸의_이동규칙() {
        //given
        MoveRule moveRule = new ZolMoveRule();
        Team han = Team.HAN;
        Route route1 = new Route(List.of(Direction.DOWN));
        Route route2 = new Route(List.of(Direction.LEFT));
        Route route3 = new Route(List.of(Direction.RIGHT));

        List<Route> routes = List.of(route1, route2, route3);

        //when
        List<Route> zolPaths = moveRule.findRoutes(han);

        //then
        assertThat(zolPaths).isEqualTo(routes);
    }
}
