package janggi.piece.pieces;

import janggi.piece.Team;
import janggi.position.Position;
import janggi.position.Route;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ElephantTest {
    @Test
    @DisplayName("상은 직선-대각선-대각선으로만 움직일 수 있다")
    void test1() {
        // given
        Elephant elephantUnitRule = new Elephant(Team.CHO);

        // when
        List<Route> routes = elephantUnitRule.calculateRoutes(new Position(0, 0));

        // then
        Assertions.assertThat(routes).isNotEmpty();
        Assertions.assertThat(routes).containsOnly(
                Route.of(List.of(new Position(1, 0), new Position(2, 1), new Position(3, 2))),
                Route.of(List.of(new Position(0, 1), new Position(1, 2), new Position(2, 3)))
        );
    }
}
