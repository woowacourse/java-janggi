package janggi.piece.pieces;

import janggi.piece.Team;
import janggi.position.Position;
import janggi.position.Route;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SoldierTest {
    @Test
    @DisplayName("초나라 졸은 뒤, 좌우 한칸만 움직일 수 있다")
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
    @DisplayName("한나라 졸은 앞, 좌우 한칸만 움직일 수 있다")
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

    @Test
    @DisplayName("초나라 졸은 궁성의 꼭짓점 혹은 중앙일 때 뒤, 대각선 뒤, 좌우 한칸만 움직일 수 있다")
    void test3() {
        // given
        Soldier soldierUnitRule = new Soldier(Team.CHO);

        // when
        List<Route> routes = soldierUnitRule.calculateRoutes(new Position(4, 8));

        // then
        Assertions.assertThat(routes).isNotEmpty();
        Assertions.assertThat(routes).containsOnly(
                Route.of(List.of(new Position(3, 8))),
                Route.of(List.of(new Position(5, 8))),
                Route.of(List.of(new Position(4, 7))),
                Route.of(List.of(new Position(3, 7))),
                Route.of(List.of(new Position(5, 7)))
        );
    }

    @Test
    @DisplayName("초나라 졸은 궁성의 모서리에서 뒤, 좌우 한칸만 움직일 수 있다")
    void test4() {
        // given
        Soldier soldierUnitRule = new Soldier(Team.CHO);

        // when
        List<Route> routes = soldierUnitRule.calculateRoutes(new Position(3, 8));

        // then
        Assertions.assertThat(routes).isNotEmpty();
        Assertions.assertThat(routes).containsOnly(
                Route.of(List.of(new Position(3, 7))),
                Route.of(List.of(new Position(2, 8))),
                Route.of(List.of(new Position(4, 8)))
        );
    }

    @Test
    @DisplayName("한나라 졸은 궁성의 꼭짓점 혹은 중앙일 때 앞, 대각선 앞, 좌우 한칸만 움직일 수 있다")
    void test5() {
        // given
        Soldier soldierUnitRule = new Soldier(Team.HAN);

        // when
        List<Route> routes = soldierUnitRule.calculateRoutes(new Position(4, 1));

        // then
        Assertions.assertThat(routes).isNotEmpty();
        Assertions.assertThat(routes).containsOnly(
                Route.of(List.of(new Position(3, 1))),
                Route.of(List.of(new Position(5, 1))),
                Route.of(List.of(new Position(4, 2))),
                Route.of(List.of(new Position(3, 2))),
                Route.of(List.of(new Position(5, 2)))
        );
    }

    @Test
    @DisplayName("한나라 졸은 궁성의 모서리에서 앞, 좌우 한칸만 움직일 수 있다")
    void test6() {
        // given
        Soldier soldierUnitRule = new Soldier(Team.HAN);

        // when
        List<Route> routes = soldierUnitRule.calculateRoutes(new Position(3, 1));

        // then
        Assertions.assertThat(routes).isNotEmpty();
        Assertions.assertThat(routes).containsOnly(
                Route.of(List.of(new Position(2, 1))),
                Route.of(List.of(new Position(4, 1))),
                Route.of(List.of(new Position(3, 2)))
        );
    }
}
