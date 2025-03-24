package janggi.domain.piece;

import static janggi.domain.Team.RED;
import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.piece.direction.Position;
import janggi.domain.piece.direction.Route;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HorseTest {

    @DisplayName("마 기물이 올바른 루트를 계산하는지 확인한다.")
    @Test
    void calculateIndependentRoutesTest() {

        // given
        final Piece horse = new Horse(new Position(4, 4), RED);
        final Set<Route> soliderRoutes = horse.calculateIndependentRoutes();

        final Route route1 = new Route(List.of(
                new Position(4, 5),
                new Position(3, 6)));
        final Route route2 = new Route(List.of(
                new Position(4, 5),
                new Position(5, 6)));
        final Route route3 = new Route(List.of(
                new Position(5, 4),
                new Position(6, 5)));
        final Route route4 = new Route(List.of(
                new Position(5, 4),
                new Position(6, 3)));
        final Route route5 = new Route(List.of(
                new Position(4, 3),
                new Position(3, 2)));
        final Route route6 = new Route(List.of(
                new Position(4, 3),
                new Position(5, 2)));
        final Route route7 = new Route(List.of(
                new Position(3, 4),
                new Position(2, 5)));
        final Route route8 = new Route(List.of(
                new Position(3, 4),
                new Position(2, 3)));

        // when
        final Set<Route> expected = Set.of(route2, route1, route3, route4, route5, route6, route7, route8);

        // then
        assertThat(soliderRoutes).isEqualTo(expected);
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

}
