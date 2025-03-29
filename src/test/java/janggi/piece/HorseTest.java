package janggi.piece;

import janggi.piece.pieces.Horse;
import janggi.position.Position;
import janggi.position.Route;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HorseTest {
    @Test
    @DisplayName("말은 직선-대각선으로만 움직일 수 있다")
    void test1() {
        // given
        Horse horseUnitRule = new Horse(Team.CHO);

        // when
        List<Route> routes = horseUnitRule.calculateRoutes(new Position(0, 0));

        // then
        Assertions.assertThat(routes).isNotEmpty();
        Assertions.assertThat(routes).containsOnly(
                Route.of(List.of(new Position(1, 0), new Position(2, 1))),
                Route.of(List.of(new Position(0, 1), new Position(1, 2)))
        );
    }
}
