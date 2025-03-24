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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChariotTest {

    @DisplayName("차 기물이 올바른 루트를 계산하는지 확인한다.")
    @Test
    void calculateIndependentRoutesTest() {

        // given
        final Piece chariot = new Chariot(new Position(4, 4), RED);
        final Set<Route> chariotRoutes = chariot.calculateIndependentRoutes();

        final Set<Route> allRoutes = new HashSet<>();

        allRoutes.add(new Route(List.of(new Position(5, 4))));
        allRoutes.add(new Route(List.of(new Position(5, 4), new Position(6, 4))));
        allRoutes.add(new Route(List.of(new Position(5, 4), new Position(6, 4), new Position(7, 4))));
        allRoutes.add(
                new Route(List.of(new Position(5, 4), new Position(6, 4), new Position(7, 4), new Position(8, 4))));

        allRoutes.add(new Route(List.of(new Position(3, 4))));
        allRoutes.add(new Route(List.of(new Position(3, 4), new Position(2, 4))));
        allRoutes.add(new Route(List.of(new Position(3, 4), new Position(2, 4), new Position(1, 4))));
        allRoutes.add(
                new Route(List.of(new Position(3, 4), new Position(2, 4), new Position(1, 4), new Position(0, 4))));

        allRoutes.add(new Route(List.of(new Position(4, 5))));
        allRoutes.add(new Route(List.of(new Position(4, 5), new Position(4, 6))));
        allRoutes.add(new Route(List.of(new Position(4, 5), new Position(4, 6), new Position(4, 7))));
        allRoutes.add(
                new Route(List.of(new Position(4, 5), new Position(4, 6), new Position(4, 7), new Position(4, 8))));
        allRoutes.add(new Route(List.of(new Position(4, 5), new Position(4, 6), new Position(4, 7), new Position(4, 8),
                new Position(4, 9))));

        allRoutes.add(new Route(List.of(new Position(4, 3))));
        allRoutes.add(new Route(List.of(new Position(4, 3), new Position(4, 2))));
        allRoutes.add(new Route(List.of(new Position(4, 3), new Position(4, 2), new Position(4, 1))));
        allRoutes.add(
                new Route(List.of(new Position(4, 3), new Position(4, 2), new Position(4, 1), new Position(4, 0))));

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
}
