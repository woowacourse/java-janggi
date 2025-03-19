package janggi.domain.piece;

import static janggi.domain.Team.RED;
import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Route;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SoldierTest {

    @DisplayName("기물이 움직이는 지 확인한다.")
    @Test
    void moveTest() {

        // given
        final Piece soldier = new Soldier(new Position(1, 1), RED);
        final Position newPosition = new Position(2, 2);

        // when
        soldier.move(newPosition);

        // then
        assertThat(soldier.isSamePosition(newPosition)).isTrue();
    }

    @DisplayName("졸이 올바른 루트를 계산하는지 확인한다.")
    @Test
    void calculateRoutesTest() {

        // given
        final Piece solider = new Soldier(new Position(1, 1), RED);
        final List<Route> soliderRoutes = solider.calculateRoutes();

        final Route route1 = new Route(new HashSet<>(), new Position(0, 1));
        final Route route2 = new Route(new HashSet<>(), new Position(2, 1));
        final Route route3 = new Route(new HashSet<>(), new Position(1, 0));

        // when
        final List<Route> expected = List.of(route2, route1, route3);

        // then
        assertThat(soliderRoutes).isEqualTo(expected);
    }
}
