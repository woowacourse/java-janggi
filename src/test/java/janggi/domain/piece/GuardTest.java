package janggi.domain.piece;

import static janggi.domain.Team.RED;
import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.position.Position;
import janggi.domain.position.Route;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GuardTest {

    Piece guard;

    @DisplayName("해당 기물이 사 기물인지 확인한다.")
    @Test
    void isGuardTest() {
        guard = new Guard(new Position(3, 2), RED);
        Assertions.assertThat(guard.isSameType(PieceType.GUARD)).isTrue();
    }

    @DisplayName("사 기물이 궁성 위치에서(3, 2)이 올바른 루트를 계산하는지 확인한다. (궁성 밖으로 나가는 경우도 포함)")
    @Test
    void calculateRoutesTest1() {

        // given
        guard = new Guard(new Position(3, 2), RED);
        final Set<Route> soliderRoutes = guard.calculateRoutes();

        final Route route1 = new Route(new ArrayList<>(List.of(new Position(3, 1))));
        final Route route2 = new Route(new ArrayList<>(List.of(new Position(4, 1))));
        final Route route3 = new Route(new ArrayList<>(List.of(new Position(4, 2))));
        final Route route4 = new Route(new ArrayList<>(List.of(new Position(3, 3))));
        final Route route5 = new Route(new ArrayList<>(List.of(new Position(2, 2))));

        // when
        final Set<Route> expected = Set.of(route2, route1, route3, route4, route5);

        // then
        assertThat(soliderRoutes).isEqualTo(expected);
    }

    @DisplayName("사 기물이 궁성 위치에서(4, 1)이 올바른 루트를 계산하는지 확인한다. (궁성 밖으로 나가는 경우도 포함)")
    @Test
    void calculateRoutesTest2() {

        // given
        guard = new Guard(new Position(4, 1), RED);
        final Set<Route> soliderRoutes = guard.calculateRoutes();

        final Route route1 = new Route(new ArrayList<>(List.of(new Position(3, 2))));
        final Route route2 = new Route(new ArrayList<>(List.of(new Position(4, 2))));
        final Route route3 = new Route(new ArrayList<>(List.of(new Position(5, 2))));
        final Route route4 = new Route(new ArrayList<>(List.of(new Position(3, 1))));
        final Route route5 = new Route(new ArrayList<>(List.of(new Position(5, 1))));
        final Route route6 = new Route(new ArrayList<>(List.of(new Position(3, 0))));
        final Route route7 = new Route(new ArrayList<>(List.of(new Position(4, 0))));
        final Route route8 = new Route(new ArrayList<>(List.of(new Position(5, 0))));

        // when
        final Set<Route> expected = Set.of(route2, route1, route3, route4, route5, route6, route7, route8);

        // then
        assertThat(soliderRoutes).isEqualTo(expected);
    }
}
