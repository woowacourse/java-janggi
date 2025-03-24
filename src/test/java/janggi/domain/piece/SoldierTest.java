package janggi.domain.piece;

import static janggi.domain.Team.BLUE;
import static janggi.domain.Team.RED;
import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Team;
import janggi.domain.piece.direction.Position;
import janggi.domain.piece.direction.Route;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class SoldierTest {

    @DisplayName("졸 기물이 올바른 루트를 계산하는지 확인한다.")
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
    void calculateIndependentRoutesTest() {

        // given
        final Piece solider = new Soldier(new Position(1, 1), RED);
        final Set<Route> soliderRoutes = solider.calculateIndependentRoutes();

        final Route route1 = new Route(List.of(new Position(0, 1)));
        final Route route2 = new Route(List.of(new Position(2, 1)));
        final Route route3 = new Route(List.of(new Position(1, 0)));

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
    void isSameTeamTest(final Team firstTeam, final Team secondTeam, final boolean expected) {

        // given
        final Piece piece1 = new Soldier(new Position(1, 1), firstTeam);

        // when & then
        assertThat(piece1.isSameTeam(secondTeam)).isEqualTo(expected);
    }

    @DisplayName("다른 기물을 통해 졸 기물이 이동 가능한 경로를 얻는다.")
    @Test
    void isValidRouteTest() {

        // given
        final Piece soldier = new Soldier(new Position(4, 4), RED);
        final List<Piece> otherPieces = new ArrayList<>();
        otherPieces.add(new Soldier(new Position(4, 5), RED));
        otherPieces.add(new Soldier(new Position(3, 4), RED));
        otherPieces.add(new Soldier(new Position(4, 3), BLUE));
        otherPieces.add(new Soldier(new Position(5, 4), BLUE));

        // when
        final Set<Route> soldierRoutes = soldier.getPossibleRoutes(otherPieces);

        // then
        assertThat(soldierRoutes.size()).isEqualTo(2);
    }
}
