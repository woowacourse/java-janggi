package janggi.piece;

import janggi.piece.pieces.Soldier;
import janggi.position.Position;
import janggi.position.Route;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SoldierTest {
    @Test
    @DisplayName("초나라 졸은 앞, 좌우 한칸만 움직일 수 있다")
    void test1() {
        // given
        Soldier soldierUnitRule = new Soldier(Team.CHO);

        // when
        List<Route> routes = soldierUnitRule.calculateRoutes(new Position(1, 9));

        // then
        Assertions.assertThat(routes).isNotEmpty();
        Assertions.assertThat(routes).containsOnly(
                Route.of(List.of(new Position(0, 9))),
                Route.of(List.of(new Position(1, 8))),
                Route.of(List.of(new Position(2, 9)))
        );
    }

    @Test
    @DisplayName("한나라 졸은 뒤, 좌우 한칸만 움직일 수 있다")
    void test2() {
        // given
        Soldier soldierUnitRule = new Soldier(Team.HAN);

        // when
        List<Route> routes = soldierUnitRule.calculateRoutes(new Position(1, 0));

        // then
        Assertions.assertThat(routes).isNotEmpty();
        Assertions.assertThat(routes).containsOnly(
                Route.of(List.of(new Position(2, 0))),
                Route.of(List.of(new Position(1, 1))),
                Route.of(List.of(new Position(0, 0)))
        );
    }
}
