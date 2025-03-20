package janggi.domain.piece;

import static janggi.domain.Team.RED;
import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Route;
import janggi.domain.Team;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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
        final Set<Route> soliderRoutes = solider.calculateRoutes();

        final Route route1 = new Route(new ArrayList<>(List.of(new Position(0, 1))));
        final Route route2 = new Route(new ArrayList<>(List.of(new Position(2, 1))));
        final Route route3 = new Route(new ArrayList<>(List.of(new Position(1, 0))));

        // when
        final Set<Route> expected = Set.of(route2, route1, route3);

        // then
        assertThat(soliderRoutes).isEqualTo(expected);
    }

    @DisplayName("같은 팀이면 true를 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {
            "RED,RED,true", "RED,BLUE,false"
    })
    void isSameTeamTest(Team firstTeam, Team secondTeam, boolean expected) {

        // given
        Piece piece1 = new Soldier(new Position(1, 1), firstTeam);

        // when & then
        assertThat(piece1.isSameTeam(secondTeam)).isEqualTo(expected);
    }
}
