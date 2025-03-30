package janggi.domain.piece;

import static janggi.domain.Team.BLUE;
import static janggi.domain.Team.RED;
import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.piece.direction.Position;
import janggi.domain.piece.direction.Route;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChariotTest {

    private Set<Route> allRoutes;
    private Piece chariot;

    @BeforeEach
    void setUp() {
        chariot = new Chariot(new Position(4, 4), RED);
        allRoutes = new HashSet<>();

        allRoutes.add(new Route(List.of(
                new Position(5, 4))));
        allRoutes.add(new Route(List.of(
                new Position(5, 4),
                new Position(6, 4))));
        allRoutes.add(new Route(List.of(
                new Position(5, 4),
                new Position(6, 4),
                new Position(7, 4))));
        allRoutes.add(new Route(List.of(
                new Position(5, 4),
                new Position(6, 4),
                new Position(7, 4),
                new Position(8, 4))));

        allRoutes.add(new Route(List.of(
                new Position(3, 4))));
        allRoutes.add(new Route(List.of(
                new Position(3, 4),
                new Position(2, 4))));
        allRoutes.add(new Route(List.of(
                new Position(3, 4),
                new Position(2, 4),
                new Position(1, 4))));
        allRoutes.add(new Route(List.of(
                new Position(3, 4),
                new Position(2, 4),
                new Position(1, 4),
                new Position(0, 4))));

        allRoutes.add(new Route(List.of(
                new Position(4, 5))));
        allRoutes.add(new Route(List.of(
                new Position(4, 5),
                new Position(4, 6))));
        allRoutes.add(new Route(List.of(
                new Position(4, 5),
                new Position(4, 6),
                new Position(4, 7))));
        allRoutes.add(new Route(List.of(
                new Position(4, 5),
                new Position(4, 6),
                new Position(4, 7),
                new Position(4, 8))));
        allRoutes.add(new Route(List.of(
                new Position(4, 5),
                new Position(4, 6),
                new Position(4, 7),
                new Position(4, 8),
                new Position(4, 9))));

        allRoutes.add(new Route(List.of(
                new Position(4, 3))));
        allRoutes.add(new Route(List.of(
                new Position(4, 3),
                new Position(4, 2))));
        allRoutes.add(new Route(List.of(
                new Position(4, 3),
                new Position(4, 2),
                new Position(4, 1))));
        allRoutes.add(new Route(List.of(
                new Position(4, 3),
                new Position(4, 2),
                new Position(4, 1),
                new Position(4, 0))));
    }

    @DisplayName("차 기물이 올바른 루트를 계산하는지 확인한다.")
    @Test
    void calculateIndependentRoutesTest() {

        // given
        final Set<Route> chariotRoutes = chariot.calculateIndependentRoutes();

        // when
        final Set<Route> expected = Set.copyOf(allRoutes);

        // then
        assertThat(chariotRoutes).isEqualTo(expected);
    }


    @DisplayName("다른 기물을 통해 차 기물이 이동 가능한 경로를 얻는다.")
    @Test
    void isValidRouteTest() {

        // given
        final Piece chariot = new Chariot(new Position(4, 4), RED);
        final List<Piece> otherPieces = new ArrayList<>();
        otherPieces.add(new Soldier(new Position(4, 6), RED));
        otherPieces.add(new Soldier(new Position(4, 8), RED));
        otherPieces.add(new Soldier(new Position(6, 4), BLUE));
        otherPieces.add(new Soldier(new Position(7, 4), BLUE));

        // when
        final Set<Route> chariotRoutes = chariot.getPossibleRoutes(otherPieces);

        // then
        assertThat(chariotRoutes.size()).isEqualTo(11);
    }

    @DisplayName("차 기물은 다른 팀 기물을 공격할 수 있다.")
    @Test
    void cannonCanAttackOtherTeamTest() {

        // given
        final Route route = new Route(List.of(
                new Position(6, 4),
                new Position(7, 4),
                new Position(8, 4)
        ));
        final Piece soldier = new Soldier(new Position(8, 4), BLUE);
        final List<Piece> otherPieces = List.of(soldier);

        // when
        final boolean result = chariot.isValidRoute(route, otherPieces);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("차 기물은 같은 팀 기물을 공격하지 못한다.")
    @Test
    void chariotCanNotAttackSameTeamTest() {

        // given
        final Route route = new Route(List.of(
                new Position(6, 4),
                new Position(7, 4),
                new Position(8, 4)
        ));
        final Piece soldier = new Soldier(new Position(8, 4), RED);
        final List<Piece> otherPieces = List.of(soldier);

        // when
        final boolean result = chariot.isValidRoute(route, otherPieces);

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("차 기물은 도착지를 제외한 경로에 다른 기물이 있으면 갈 수 없다.")
    @Test
    void chariotCanNotMoveIfPieceInRouteTest() {

        // given
        final Route route = new Route(List.of(
                new Position(6, 4),
                new Position(7, 4),
                new Position(8, 4)
        ));
        final Piece soldier = new Soldier(new Position(7, 4), RED);
        final List<Piece> otherPieces = List.of(soldier);

        // when
        final boolean result = chariot.isValidRoute(route, otherPieces);

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("차 기물은 도착지를 제외한 경로에 다른 기물이 없으면 갈 수 있다.")
    @Test
    void chariotCanMoveIfPieceInRouteTest() {

        // given
        final Route route = new Route(List.of(
                new Position(6, 4),
                new Position(7, 4),
                new Position(8, 4)
        ));
        final List<Piece> otherPieces = List.of();

        // when
        final boolean result = chariot.isValidRoute(route, otherPieces);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("차 기물은 궁성이며 대각선에 있을 경우 대각선으로 움직일 수 있다.")
    @Test
    void cannonCannotMoveDiagonalIfInPalace() {

        // given
        final Piece chariot = new Chariot(new Position(3, 0), RED);
        final Piece soldier1 = new Soldier(new Position(4, 0), RED);
        final Piece soldier2 = new Soldier(new Position(3, 1), RED);
        final Piece soldier3 = new Soldier(new Position(2, 0), RED);
        final List<Piece> otherPieces = List.of(soldier1, soldier2, soldier3);

        // when
        final Set<Route> possibleRoutes = chariot.getPossibleRoutes(otherPieces);

        // then
        assertThat(possibleRoutes.size()).isEqualTo(2);
    }

    @DisplayName("차 기물이 궁성에 있을 때 대각선으로 이동할 수 있는 점이 아니라면 대각선으로 움직일 수 없다.")
    @Test
    void chariotCanNotMoveDiagonalIfInNotSpecialPosition() {

        // given
        final Piece chariot = new Chariot(new Position(4, 0), RED);
        final Piece soldier1 = new Soldier(new Position(4, 1), RED);
        final Piece soldier2 = new Soldier(new Position(3, 0), RED);
        final Piece soldier3 = new Soldier(new Position(5, 0), RED);
        final List<Piece> otherPieces = List.of(soldier1, soldier2, soldier3);

        // when
        final Set<Route> possibleRoutes = chariot.getPossibleRoutes(otherPieces);

        // then
        assertThat(possibleRoutes.size()).isEqualTo(0);
    }
}
