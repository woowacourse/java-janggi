package janggi.piece;

import janggi.piece.pieces.Cannon;
import janggi.position.Position;
import janggi.position.Route;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CannonTest {
    @Test
    @DisplayName("포는 수평/수직으로만 움직일 수 있다")
    void test1() {
        // given
        Cannon cannonUnitRule = new Cannon(Team.CHO);

        // when
        List<Route> routes = cannonUnitRule.calculateRoutes(new Position(0, 0));

        // then
        Assertions.assertThat(routes).isNotEmpty();
        Assertions.assertThat(routes).containsOnly(
                Route.of(List.of(
                        new Position(1, 0), new Position(2, 0), new Position(3, 0), new Position(4, 0),
                        new Position(5, 0), new Position(6, 0), new Position(7, 0), new Position(8, 0))),
                Route.of(List.of(
                        new Position(1, 0), new Position(2, 0), new Position(3, 0), new Position(4, 0),
                        new Position(5, 0), new Position(6, 0), new Position(7, 0))),
                Route.of(List.of(
                        new Position(1, 0), new Position(2, 0), new Position(3, 0), new Position(4, 0),
                        new Position(5, 0), new Position(6, 0))),
                Route.of(List.of(
                        new Position(1, 0), new Position(2, 0), new Position(3, 0), new Position(4, 0),
                        new Position(5, 0))),
                Route.of(List.of(
                        new Position(1, 0), new Position(2, 0), new Position(3, 0), new Position(4, 0))),
                Route.of(List.of(
                        new Position(1, 0), new Position(2, 0), new Position(3, 0))),
                Route.of(List.of(new Position(1, 0), new Position(2, 0))),
                Route.of(List.of(new Position(1, 0))),
                Route.of(List.of(
                        new Position(0, 1), new Position(0, 2), new Position(0, 3), new Position(0, 4),
                        new Position(0, 5), new Position(0, 6), new Position(0, 7), new Position(0, 8),
                        new Position(0, 9))),
                Route.of(List.of(
                        new Position(0, 1), new Position(0, 2), new Position(0, 3), new Position(0, 4),
                        new Position(0, 5), new Position(0, 6), new Position(0, 7), new Position(0, 8))),
                Route.of(List.of(
                        new Position(0, 1), new Position(0, 2), new Position(0, 3), new Position(0, 4),
                        new Position(0, 5), new Position(0, 6), new Position(0, 7))),
                Route.of(List.of(
                        new Position(0, 1), new Position(0, 2), new Position(0, 3), new Position(0, 4),
                        new Position(0, 5), new Position(0, 6))),
                Route.of(List.of(
                        new Position(0, 1), new Position(0, 2), new Position(0, 3), new Position(0, 4),
                        new Position(0, 5))),
                Route.of(List.of(
                        new Position(0, 1), new Position(0, 2), new Position(0, 3), new Position(0, 4))),
                Route.of(List.of(
                        new Position(0, 1), new Position(0, 2), new Position(0, 3))),
                Route.of(List.of(
                        new Position(0, 1), new Position(0, 2))),
                Route.of(List.of(new Position(0, 1))));
    }
}
