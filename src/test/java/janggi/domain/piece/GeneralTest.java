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

class GeneralTest {

    private Piece general;

    @BeforeEach
    void setUp() {
        general = new General(new Position(4, 1), RED);
    }


    @DisplayName("왕 기물이 올바른 루트를 계산하는지 확인한다.")
    @Test
    void calculateIndependentRoutesTest() {

        // given
        final Set<Route> generalRoutes = general.calculateIndependentRoutes();
        final Set<Route> allRoutes = new HashSet<>();
        allRoutes.add(new Route(List.of(new Position(3, 1))));
        allRoutes.add(new Route(List.of(new Position(5, 1))));
        allRoutes.add(new Route(List.of(new Position(4, 0))));
        allRoutes.add(new Route(List.of(new Position(4, 2))));
        allRoutes.add(new Route(List.of(new Position(3, 2))));
        allRoutes.add(new Route(List.of(new Position(5, 2))));
        allRoutes.add(new Route(List.of(new Position(3, 0))));
        allRoutes.add(new Route(List.of(new Position(5, 0))));

        // when

        // then
        assertThat(generalRoutes).isEqualTo(allRoutes);
    }

    @DisplayName("다른 기물을 통해 왕 기물이 이동 가능한 경로를 얻는다.")
    @Test
    void isValidRouteTest() {

        // given
        final List<Piece> otherPieces = new ArrayList<>();
        otherPieces.add(new Soldier(new Position(3, 1), RED));
        otherPieces.add(new Soldier(new Position(5, 1), RED));
        otherPieces.add(new Soldier(new Position(4, 2), BLUE));
        otherPieces.add(new Soldier(new Position(3, 2), BLUE));

        // when
        final Set<Route> generaleRoutes = general.getPossibleRoutes(otherPieces);

        // then
        assertThat(generaleRoutes.size()).isEqualTo(6);
    }

    @DisplayName("왕 기물은 다른 팀을 공격할 수 있다.")
    @Test
    void generalCanAttackOtherTeamTest() {

        // given
        final Route route = new Route(List.of(
                new Position(4, 5)
        ));
        final Piece soldier = new Soldier(new Position(4, 5), BLUE);
        final List<Piece> otherPieces = List.of(soldier);

        // when
        final boolean result = general.isValidRoute(route, otherPieces);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("왕 기물은 같은 팀을 공격할 수 없다.")
    @Test
    void generalCanNotAttackSameTeamTest() {

        // given
        final Route route = new Route(List.of(
                new Position(4, 5)
        ));
        final Piece soldier = new Soldier(new Position(4, 5), RED);
        final List<Piece> otherPieces = List.of(soldier);

        // when
        final boolean result = general.isValidRoute(route, otherPieces);

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("왕 기물은 경로 도착지에 말이 없으면 갈 수 있다.")
    @Test
    void generalCanMoveIfRouteInPieceTest() {

        // given
        final Route route = new Route(List.of(
                new Position(4, 5)
        ));
        final List<Piece> otherPieces = List.of();

        // when
        final boolean result = general.isValidRoute(route, otherPieces);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("왕 기물은 궁성 밖으로 나갈 수 없다.")
    @Test
    void generalCanNotMoveOutOfPalace() {

        // given
        final Piece general = new General(new Position(3, 2), RED);
        final List<Piece> otherPieces = List.of();
        final Set<Route> guardRoutes = general.calculateIndependentRoutes();

        // when

        // then
        assertThat(guardRoutes.size()).isEqualTo(5);
    }
}
