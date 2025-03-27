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

class ElephantTest {

    private Piece elephant;
    private Set<Route> allRoutes;

    @BeforeEach
    void setUp() {
        elephant = new Elephant(new Position(4, 4), RED);
        allRoutes = new HashSet<>();
        allRoutes.add(new Route(List.of(
                new Position(4, 5),
                new Position(3, 6),
                new Position(2, 7))));
        allRoutes.add(new Route(List.of(
                new Position(4, 5),
                new Position(5, 6),
                new Position(6, 7))));
        allRoutes.add(new Route(List.of(
                new Position(5, 4),
                new Position(6, 5),
                new Position(7, 6))));
        allRoutes.add(new Route(List.of(
                new Position(5, 4),
                new Position(6, 3),
                new Position(7, 2))));
        allRoutes.add(new Route(List.of(
                new Position(4, 3),
                new Position(3, 2),
                new Position(2, 1))));
        allRoutes.add(new Route(List.of(
                new Position(4, 3),
                new Position(5, 2),
                new Position(6, 1))));
        allRoutes.add(new Route(List.of(
                new Position(3, 4),
                new Position(2, 5),
                new Position(1, 6))));
        allRoutes.add(new Route(List.of(
                new Position(3, 4),
                new Position(2, 3),
                new Position(1, 2))));

    }

    @DisplayName("상 기물이 올바른 루트를 계산하는지 확인한다.")
    @Test
    void calculateIndependentRoutesTest() {

        // given
        final Set<Route> elephantRoutes = elephant.calculateIndependentRoutes();

        // when

        // then
        assertThat(allRoutes).isEqualTo(elephantRoutes);
    }

    @DisplayName("다른 기물을 통해 상 기물이 이동 가능한 경로를 얻는다.")
    @Test
    void isValidRouteTest() {

        // given
        final Piece elephant = new Elephant(new Position(4, 4), RED);
        final List<Piece> otherPieces = new ArrayList<>();
        otherPieces.add(new Soldier(new Position(4, 6), RED));
        otherPieces.add(new Soldier(new Position(4, 8), RED));
        otherPieces.add(new Soldier(new Position(6, 4), BLUE));
        otherPieces.add(new Soldier(new Position(7, 4), BLUE));

        // when
        final Set<Route> elephantRoutes = elephant.getPossibleRoutes(otherPieces);

        // then
        assertThat(elephantRoutes.size()).isEqualTo(8);
    }

    @DisplayName("상 기물은 다른 팀을 공격할 수 있다.")
    @Test
    void elephantCanAttackOtherTeamTest() {

        // given
        final Route route = new Route(List.of(
                new Position(4, 5),
                new Position(5, 6),
                new Position(5, 7)
        ));
        final Piece soldier = new Soldier(new Position(5, 7), BLUE);
        final List<Piece> otherPieces = List.of(soldier);

        // when
        final boolean result = elephant.isValidRoute(route, otherPieces);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("상 기물은 같은 팀을 공격할 수 없다.")
    @Test
    void elephantCanNotAttackSameTeamTest() {

        // given
        final Route route = new Route(List.of(
                new Position(4, 5),
                new Position(5, 6),
                new Position(6, 7)
        ));
        final Piece soldier = new Soldier(new Position(6, 7), RED);
        final List<Piece> otherPieces = List.of(soldier);

        // when
        final boolean result = elephant.isValidRoute(route, otherPieces);

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("상 기물은 도착지를 제외한 경로에 다른 기물이 없으면 갈 수 있다.")
    @Test
    void elephantCanMoveIfRouteInPieceTest() {

        // given
        final Route route = new Route(List.of(
                new Position(4, 5),
                new Position(5, 6),
                new Position(6, 7)
        ));
        final List<Piece> otherPieces = List.of();

        // when
        final boolean result = elephant.isValidRoute(route, otherPieces);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("상 기물은 도착지를 제외한 경로에 다른 기물이 있으면 갈 수 없다.")
    @Test
    void elephantCanNotMoveSameTeamTest() {

        // given
        final Route route = new Route(List.of(
                new Position(4, 5),
                new Position(5, 6),
                new Position(6, 7)
        ));
        final Piece soldier = new Soldier(new Position(5, 6), RED);
        final List<Piece> otherPieces = List.of(soldier);

        // when
        final boolean result = elephant.isValidRoute(route, otherPieces);

        // then
        assertThat(result).isFalse();
    }
}
