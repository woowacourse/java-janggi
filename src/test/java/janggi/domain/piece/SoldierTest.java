package janggi.domain.piece;

import static janggi.domain.Team.RED;
import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Team;
import janggi.domain.position.Position;
import janggi.domain.position.Route;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class SoldierTest {

    Piece soldier;

    @BeforeEach
    void makeSolider() {
        soldier = new Soldier(new Position(1, 1), RED);
    }

    @DisplayName("해당 기물이 졸 기물인지 확인한다.")
    @Test
    void isSoliderTest() {
        Assertions.assertThat(soldier.isSameType(PieceType.SOLDIER)).isTrue();
    }

    @DisplayName("기물이 움직이는 지 확인한다.")
    @Test
    void moveTest() {
        // given
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
        final Set<Route> soliderRoutes = soldier.calculateRoutes();

        final Route route1 = new Route(new ArrayList<>(List.of(new Position(0, 1))));
        final Route route2 = new Route(new ArrayList<>(List.of(new Position(2, 1))));
        final Route route3 = new Route(new ArrayList<>(List.of(new Position(1, 0))));

        // when
        final Set<Route> expected = Set.of(route2, route1, route3);

        // then
        assertThat(soliderRoutes).isEqualTo(expected);
    }

    @DisplayName("졸이 (3, 7) 위치에서 올바른 루트를 계산하는지 확인한다.")
    @Test
    void calculatePalaceRoutesTest1() {
        // given
        soldier = new Soldier(new Position(3, 7), Team.BLUE);
        final Set<Route> soliderRoutes = soldier.calculateRoutes();

        final Route route1 = new Route(new ArrayList<>(List.of(new Position(2, 7))));
        final Route route2 = new Route(new ArrayList<>(List.of(new Position(4, 7))));
        final Route route3 = new Route(new ArrayList<>(List.of(new Position(3, 8))));
        final Route route4 = new Route(new ArrayList<>(List.of(new Position(4, 8))));

        // when
        final Set<Route> expected = Set.of(route2, route1, route3, route4);

        // then
        assertThat(soliderRoutes).isEqualTo(expected);
    }

    @DisplayName("졸이 (4, 8) 위치에서 올바른 루트를 계산하는지 확인한다.")
    @Test
    void calculatePalaceRoutesTest2() {
        // given
        soldier = new Soldier(new Position(4, 8), Team.BLUE);
        final Set<Route> soliderRoutes = soldier.calculateRoutes();

        final Route route1 = new Route(new ArrayList<>(List.of(new Position(3, 9))));
        final Route route2 = new Route(new ArrayList<>(List.of(new Position(4, 9))));
        final Route route3 = new Route(new ArrayList<>(List.of(new Position(5, 9))));
        final Route route4 = new Route(new ArrayList<>(List.of(new Position(3, 8))));
        final Route route5 = new Route(new ArrayList<>(List.of(new Position(5, 8))));

        // when
        final Set<Route> expected = Set.of(route2, route1, route3, route4, route5);

        // then
        assertThat(soliderRoutes).isEqualTo(expected);
    }

    @DisplayName("같은 팀이면 참을 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {
            "RED,true", "BLUE,false"
    })
    void isSameTeamTest(Team secondTeam, boolean expected) {
        assertThat(soldier.isSameTeam(secondTeam)).isEqualTo(expected);
    }
}
