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

class GuardTest {

    private Piece guard;
    private Set<Route> allRoutes;

    @BeforeEach
    void setUp() {
        guard = new Guard(new Position(4, 1), RED);
        allRoutes = new HashSet<>();
        allRoutes.add(new Route(List.of(new Position(4, 0))));
        allRoutes.add(new Route(List.of(new Position(3, 1))));
        allRoutes.add(new Route(List.of(new Position(5, 1))));
        allRoutes.add(new Route(List.of(new Position(4, 2))));
        allRoutes.add(new Route(List.of(new Position(3, 2))));
        allRoutes.add(new Route(List.of(new Position(5, 2))));
        allRoutes.add(new Route(List.of(new Position(3, 0))));
        allRoutes.add(new Route(List.of(new Position(5, 0))));
    }

    @DisplayName("사 기물이 올바른 루트를 계산하는지 확인한다.")
    @Test
    void calculateIndependentRoutesTest() {

        // given
        final Set<Route> soliderRoutes = guard.calculateIndependentRoutes();

        // when

        // then
        assertThat(soliderRoutes).isEqualTo(allRoutes);
    }

    @DisplayName("다른 기물을 통해 사 기물이 이동 가능한 경로를 얻는다.")
    @Test
    void isValidRouteTest() {

        // given
        final List<Piece> otherPieces = new ArrayList<>();
        otherPieces.add(new Soldier(new Position(4, 2), RED));
        otherPieces.add(new Soldier(new Position(3, 1), RED));
        otherPieces.add(new Soldier(new Position(5, 1), BLUE));
        otherPieces.add(new Soldier(new Position(4, 0), BLUE));

        // when
        final Set<Route> guardRoutes = guard.getPossibleRoutes(otherPieces);

        // then
        assertThat(guardRoutes.size()).isEqualTo(6);
    }

    @DisplayName("사 기물은 다른 팀을 공격할 수 있다.")
    @Test
    void guardCanAttackOtherTeamTest() {

        // given
        final Route route = new Route(List.of(
                new Position(4, 2)
        ));
        final Piece soldier = new Soldier(new Position(4, 2), BLUE);
        final List<Piece> otherPieces = List.of(soldier);

        // when
        final boolean result = guard.isValidRoute(route, otherPieces);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("사 기물은 같은 팀을 공격할 수 없다.")
    @Test
    void guardCanNotAttackSameTeamTest() {

        // given
        final Route route = new Route(List.of(
                new Position(4, 2)
        ));
        final Piece soldier = new Soldier(new Position(4, 2), RED);
        final List<Piece> otherPieces = List.of(soldier);

        // when
        final boolean result = guard.isValidRoute(route, otherPieces);

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("사 기물은 경로 도착지에 말이 없으면 갈 수 있다.")
    @Test
    void guardCanMoveIfRouteInPieceTest() {

        // given
        final Route route = new Route(List.of(
                new Position(4, 2)
        ));
        final List<Piece> otherPieces = List.of();

        // when
        final boolean result = guard.isValidRoute(route, otherPieces);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("사 기물은 궁성 밖으로 나갈 수 없다.")
    @Test
    void guardCanNotMoveOutOfPalace() {

        // given
        final Piece guard = new Guard(new Position(3, 2), RED);
        final List<Piece> otherPieces = List.of();
        final Set<Route> guardRoutes = guard.getPossibleRoutes(otherPieces);

        // when

        // then
        assertThat(guardRoutes.size()).isEqualTo(5);
    }

    @DisplayName("사 기물이 궁성에 있을 때 대각선으로 이동할 수 있는 점이 아니라면 대각선으로 움직일 수 없다.")
    @Test
    void guardCanNotMoveDiagonalIfInNotSpecialPosition() {

        // given
        final Piece guard = new Guard(new Position(4, 0), RED);
        final Piece soldier1 = new Soldier(new Position(4, 1), RED);
        final Piece soldier2 = new Soldier(new Position(3, 0), RED);
        final Piece soldier3 = new Soldier(new Position(5, 0), RED);
        final List<Piece> otherPieces = List.of(soldier1, soldier2, soldier3);

        // when
        final Set<Route> possibleRoutes = guard.getPossibleRoutes(otherPieces);

        // then
        assertThat(possibleRoutes.size()).isEqualTo(0);
    }
}
