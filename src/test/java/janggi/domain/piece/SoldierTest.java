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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class SoldierTest {

    private Piece soldier;

    @BeforeEach
    void setUp() {
        soldier = new Soldier(new Position(1, 1), RED);
    }

    @DisplayName("졸이 올바른 루트를 계산하는지 확인한다.")
    @Test
    void calculateIndependentRoutesTest() {

        // given
        final Set<Route> soldierRoutes = soldier.calculateIndependentRoutes();

        final Route route1 = new Route(List.of(new Position(0, 1)));
        final Route route2 = new Route(List.of(new Position(2, 1)));
        final Route route3 = new Route(List.of(new Position(1, 0)));

        // when
        final Set<Route> expected = Set.of(route2, route1, route3);

        // then
        assertThat(soldierRoutes).isEqualTo(expected);
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
        final List<Piece> otherPieces = new ArrayList<>();
        otherPieces.add(new Soldier(new Position(1, 0), RED));
        otherPieces.add(new Soldier(new Position(0, 1), RED));
        otherPieces.add(new Soldier(new Position(2, 0), BLUE));

        // when
        final Set<Route> soldierRoutes = soldier.getPossibleRoutes(otherPieces);

        // then
        assertThat(soldierRoutes.size()).isEqualTo(1);
    }

    @DisplayName("졸 기물은 다른 팀을 공격할 수 있다.")
    @Test
    void soldierCanAttackOtherTeamTest() {

        // given
        final Route route = new Route(List.of(
                new Position(2, 1)
        ));
        final Piece otherSoldier = new Soldier(new Position(2, 1), BLUE);
        final List<Piece> otherPieces = List.of(otherSoldier);

        // when
        final boolean result = soldier.isValidRoute(route, otherPieces);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("졸 기물은 같은 팀을 공격할 수 없다.")
    @Test
    void soldierCanNotAttackSameTeamTest() {

        // given
        final Route route = new Route(List.of(
                new Position(1, 0)
        ));
        final Piece soldier = new Soldier(new Position(1, 0), RED);
        final List<Piece> otherPieces = List.of(soldier);

        // when
        final boolean result = soldier.isValidRoute(route, otherPieces);

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("졸 기물은 경로 도착지에 말이 없으면 갈 수 있다.")
    @Test
    void soldierCanMoveIfRouteInPieceTest() {

        // given
        final Route route = new Route(List.of(
                new Position(1, 0)
        ));
        final List<Piece> otherPieces = List.of();

        // when
        final boolean result = soldier.isValidRoute(route, otherPieces);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("졸 기물이 궁성에 있을 때 대각선으로 이동할 수 있는 점이 아니라면 대각선으로 움직일 수 없다.")
    @Test
    void soldierCannotMoveDiagonalIfInNotSpecialPosition() {

        // given
        final Piece soldier = new Soldier(new Position(4, 2), RED);
        final Piece soldier1 = new Soldier(new Position(4, 1), RED);
        final Piece soldier2 = new Soldier(new Position(3, 2), RED);
        final Piece soldier3 = new Soldier(new Position(5, 2), RED);
        final List<Piece> otherPieces = List.of(soldier1, soldier2, soldier3);

        // when
        final Set<Route> possibleRoutes = soldier.getPossibleRoutes(otherPieces);

        // then
        assertThat(possibleRoutes.size()).isEqualTo(0);
    }
}
