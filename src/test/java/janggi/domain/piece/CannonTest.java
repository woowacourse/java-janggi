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

class CannonTest {

    private Set<Route> allRoutes;
    private Piece cannon;

    @BeforeEach
    void setUp() {
        cannon = new Cannon(new Position(4, 4), RED);
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

    @DisplayName("포 기물이 올바른 루트를 계산하는지 확인한다.")
    @Test
    void calculateIndependentRoutesTest() {

        // given
        final Set<Route> cannonRoutes = cannon.calculateIndependentRoutes();

        // when
        final Set<Route> expected = Set.copyOf(allRoutes);

        // then
        assertThat(cannonRoutes).isEqualTo(expected);
    }

    @DisplayName("다른 기물을 통해 포 기물이 이동 가능한 경로를 얻는다.")
    @Test
    void isValidRouteTest() {

        // given
        final Piece cannon = new Cannon(new Position(4, 4), RED);
        final List<Piece> otherPieces = new ArrayList<>();
        otherPieces.add(new Soldier(new Position(4, 6), RED));
        otherPieces.add(new Soldier(new Position(4, 8), RED));
        otherPieces.add(new Soldier(new Position(6, 4), BLUE));
        otherPieces.add(new Soldier(new Position(7, 4), BLUE));

        // when
        final Set<Route> cannonRoutes = cannon.getPossibleRoutes(otherPieces);

        // then
        assertThat(cannonRoutes.size()).isEqualTo(2);
    }

    @DisplayName("포 기물의 경로는 목적지를 제외하고 경로에 하나의 기물이 있어야한다.")
    @Test
    void cannonsRouteHasToOnePieceTest() {

        // given
        final Route route = new Route(List.of(
                new Position(6, 4),
                new Position(7, 4),
                new Position(8, 4)
        ));
        final Piece soldier = new Soldier(new Position(7, 4), RED);
        final List<Piece> otherPieces = List.of(soldier);

        // when
        final boolean result = cannon.isValidRoute(route, otherPieces);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("포 기물은 같은 팀 기물을 공격하지 못한다.")
    @Test
    void cannonCanNotAttackSameTeamTest() {

        // given
        final Route route = new Route(List.of(
                new Position(6, 4),
                new Position(7, 4),
                new Position(8, 4)
        ));
        final Piece soldier1 = new Soldier(new Position(7, 4), RED);
        final Piece soldier2 = new Soldier(new Position(8, 4), RED);
        final List<Piece> otherPieces = List.of(soldier1, soldier2);

        // when
        final boolean result = cannon.isValidRoute(route, otherPieces);

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("포 기물의 경로는 목적지를 제외하고 경로에 하나의 기물이 있어야한다.")
    @Test
    void cannonsRouteHasOnePieceTest() {

        // given
        final Route route = new Route(List.of(
                new Position(6, 4),
                new Position(7, 4),
                new Position(8, 4)
        ));
        final Piece soldier = new Soldier(new Position(7, 4), RED);
        final List<Piece> otherPieces = List.of(soldier);

        // when
        final boolean result = cannon.isValidRoute(route, otherPieces);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("포 기물의 경로는 다른 기물이 없을 시 이동이 불가능하다.")
    @Test
    void cannonsRouteDoesHavePieceTest() {

        // given
        final Route route = new Route(List.of(
                new Position(6, 4),
                new Position(7, 4),
                new Position(8, 4)
        ));
        final List<Piece> otherPieces = List.of();

        // when
        final boolean result = cannon.isValidRoute(route, otherPieces);

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("포 기물은 포 기물을 공격하지 못한다.")
    @Test
    void cannonCanNotAttackCannonTest() {

        // given
        final Route route = new Route(List.of(
                new Position(6, 4),
                new Position(7, 4),
                new Position(8, 4)
        ));
        final Piece soldier = new Soldier(new Position(7, 4), RED);
        final Piece otherCannon = new Cannon(new Position(8, 4), BLUE);
        final List<Piece> otherPieces = List.of(soldier, otherCannon);

        // when
        final boolean result = cannon.isValidRoute(route, otherPieces);

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("포 기물은 포 기물을 넘지 못한다.")
    @Test
    void cannonCanNotJumpOtherCannonTest() {

        // given
        final Route route = new Route(List.of(
                new Position(6, 4),
                new Position(7, 4),
                new Position(8, 4)
        ));
        final Piece soldier = new Soldier(new Position(8, 4), BLUE);
        final Piece otherCannon = new Cannon(new Position(7, 4), BLUE);
        final List<Piece> otherPieces = List.of(soldier, otherCannon);

        // when
        final boolean result = cannon.isValidRoute(route, otherPieces);

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("포 기물은 궁성이며 대각선에 있을 경우 대각선으로 움직일 수 있다.")
    @Test
    void cannonCannotMoveDiagonalIfInPalace() {

        // given
        final Piece cannon = new Cannon(new Position(3, 0), RED);
        final Piece soldier = new Soldier(new Position(4, 1), RED);
        final List<Piece> otherPieces = List.of(soldier);

        // when
        final Set<Route> possibleRoutes = cannon.getPossibleRoutes(otherPieces);

        // then
        assertThat(possibleRoutes.size()).isEqualTo(1);
    }
}
