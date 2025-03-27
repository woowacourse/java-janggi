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

class HorseTest {

    private Piece horse;
    private Set<Route> allRoutes;

    @BeforeEach
    void setUp() {
        horse = new Horse(new Position(4, 4), RED);
        allRoutes = new HashSet<>();
        allRoutes.add(new Route(List.of(
                new Position(4, 5),
                new Position(3, 6))));
        allRoutes.add(new Route(List.of(
                new Position(4, 5),
                new Position(5, 6))));
        allRoutes.add(new Route(List.of(
                new Position(5, 4),
                new Position(6, 5))));
        allRoutes.add(new Route(List.of(
                new Position(5, 4),
                new Position(6, 3))));
        allRoutes.add(new Route(List.of(
                new Position(4, 3),
                new Position(3, 2))));
        allRoutes.add(new Route(List.of(
                new Position(4, 3),
                new Position(5, 2))));
        allRoutes.add(new Route(List.of(
                new Position(3, 4),
                new Position(2, 5))));
        allRoutes.add(new Route(List.of(
                new Position(3, 4),
                new Position(2, 3))));
    }

    @DisplayName("마 기물이 올바른 루트를 계산하는지 확인한다.")
    @Test
    void calculateIndependentRoutesTest() {

        // given
        final Set<Route> soliderRoutes = horse.calculateIndependentRoutes();

        // when

        // then
        assertThat(soliderRoutes).isEqualTo(allRoutes);
    }

    @DisplayName("다른 기물을 통해 마 기물이 이동 가능한 경로를 얻는다.")
    @Test
    void isValidRouteTest() {

        // given
        final Piece horse = new Horse(new Position(4, 4), RED);
        final List<Piece> otherPieces = new ArrayList<>();
        otherPieces.add(new Soldier(new Position(4, 5), RED));
        otherPieces.add(new Soldier(new Position(3, 4), RED));

        // when
        final Set<Route> horseRoutes = horse.getPossibleRoutes(otherPieces);

        // then
        assertThat(horseRoutes.size()).isEqualTo(4);
    }

    @DisplayName("마 기물은 다른 팀을 공격할 수 있다.")
    @Test
    void horseCanAttackOtherTeamTest() {

        // given
        final Route route = new Route(List.of(
                new Position(4, 5),
                new Position(5, 6)
        ));
        final Piece soldier = new Soldier(new Position(5, 6), BLUE);
        final List<Piece> otherPieces = List.of(soldier);

        // when
        final boolean result = horse.isValidRoute(route, otherPieces);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("마 기물은 같은 팀을 공격할 수 없다.")
    @Test
    void horseCanNotAttackSameTeamTest() {

        // given
        final Route route = new Route(List.of(
                new Position(4, 5),
                new Position(5, 6)
        ));
        final Piece soldier = new Soldier(new Position(5, 6), RED);
        final List<Piece> otherPieces = List.of(soldier);

        // when
        final boolean result = horse.isValidRoute(route, otherPieces);

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("마 기물은 도착지를 제외한 경로에 다른 기물이 없으면 갈 수 있다.")
    @Test
    void elephantCanMoveIfRouteInPieceTest() {

        // given
        final Route route = new Route(List.of(
                new Position(4, 5),
                new Position(5, 6)
        ));
        final List<Piece> otherPieces = List.of();

        // when
        final boolean result = horse.isValidRoute(route, otherPieces);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("상 기물은 도착지를 제외한 경로에 다른 기물이 있으면 갈 수 없다.")
    @Test
    void elephantCanNotMoveSameTeamTest() {

        // given
        final Route route = new Route(List.of(
                new Position(4, 5),
                new Position(5, 6)
        ));
        final Piece soldier = new Soldier(new Position(4, 5), RED);
        final List<Piece> otherPieces = List.of(soldier);

        // when
        final boolean result = horse.isValidRoute(route, otherPieces);

        // then
        assertThat(result).isFalse();
    }
}
