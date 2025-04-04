package janggi.piece.pieces;

import janggi.piece.Team;
import janggi.position.Position;
import janggi.position.Route;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChariotTest {
    @Test
    @DisplayName("차는 수평/수직으로만 움직일 수 있다")
    void test1() {
        // given
        Chariot chariotUnitRule = new Chariot(Team.CHO);

        // when
        List<Route> routes = chariotUnitRule.calculateRoutes(new Position(0, 0));

        // then
        Assertions.assertThat(routes).isNotEmpty();
        Assertions.assertThat(routes).containsOnly(
                Route.of(List.of(new Position(1, 0), new Position(2, 0), new Position(3, 0), new Position(4, 0), new Position(5, 0), new Position(6, 0), new Position(7, 0), new Position(8, 0))),
                Route.of(List.of(new Position(1, 0), new Position(2, 0), new Position(3, 0), new Position(4, 0), new Position(5, 0), new Position(6, 0), new Position(7, 0))),
                Route.of(List.of(new Position(1, 0), new Position(2, 0), new Position(3, 0), new Position(4, 0), new Position(5, 0), new Position(6, 0))),
                Route.of(List.of(new Position(1, 0), new Position(2, 0), new Position(3, 0), new Position(4, 0), new Position(5, 0))),
                Route.of(List.of(new Position(1, 0), new Position(2, 0), new Position(3, 0), new Position(4, 0))),
                Route.of(List.of(new Position(1, 0), new Position(2, 0), new Position(3, 0))),
                Route.of(List.of(new Position(1, 0), new Position(2, 0))),
                Route.of(List.of(new Position(1, 0))),
                Route.of(List.of(new Position(0, 1), new Position(0, 2), new Position(0, 3), new Position(0, 4), new Position(0, 5), new Position(0, 6), new Position(0, 7), new Position(0, 8), new Position(0, 9))),
                Route.of(List.of(new Position(0, 1), new Position(0, 2), new Position(0, 3), new Position(0, 4), new Position(0, 5), new Position(0, 6), new Position(0, 7), new Position(0, 8))),
                Route.of(List.of(new Position(0, 1), new Position(0, 2), new Position(0, 3), new Position(0, 4), new Position(0, 5), new Position(0, 6), new Position(0, 7))),
                Route.of(List.of(new Position(0, 1), new Position(0, 2), new Position(0, 3), new Position(0, 4), new Position(0, 5), new Position(0, 6))),
                Route.of(List.of(new Position(0, 1), new Position(0, 2), new Position(0, 3), new Position(0, 4), new Position(0, 5))),
                Route.of(List.of(new Position(0, 1), new Position(0, 2), new Position(0, 3), new Position(0, 4))),
                Route.of(List.of(new Position(0, 1), new Position(0, 2), new Position(0, 3))),
                Route.of(List.of(new Position(0, 1), new Position(0, 2))),
                Route.of(List.of(new Position(0, 1))));
    }

    @Test
    @DisplayName("차는 궁성 내 꼭짓점에 있을 때 궁성 내에 한해 대각선으로 이동할 수 있다.")
    void test2() {
        // given
        Chariot chariot = new Chariot(Team.CHO);

        // when
        List<Route> routes = chariot.calculateRoutes(new Position(3, 7));

        // then
        Assertions.assertThat(routes).hasSize(19);
        Assertions.assertThat(routes).containsOnly(
                Route.of(List.of(new Position(0, 7), new Position(1, 7), new Position(2, 7))),
                Route.of(List.of(new Position(1, 7), new Position(2, 7))),
                Route.of(List.of(new Position(2, 7))),
                Route.of(List.of(new Position(4, 7))),
                Route.of(List.of(new Position(4, 7), new Position(5, 7))),
                Route.of(List.of(new Position(4, 7), new Position(5, 7), new Position(6, 7))),
                Route.of(List.of(new Position(4, 7), new Position(5, 7), new Position(6, 7), new Position(7, 7))),
                Route.of(List.of(new Position(4, 7), new Position(5, 7), new Position(6, 7), new Position(7, 7), new Position(8, 7))),
                Route.of(List.of(new Position(3, 0), new Position(3, 1), new Position(3, 2), new Position(3, 3), new Position(3, 4), new Position(3, 5), new Position(3, 6))),
                Route.of(List.of(new Position(3, 1), new Position(3, 2), new Position(3, 3), new Position(3, 4), new Position(3, 5), new Position(3, 6))),
                Route.of(List.of(new Position(3, 2), new Position(3, 3), new Position(3, 4), new Position(3, 5), new Position(3, 6))),
                Route.of(List.of(new Position(3, 3), new Position(3, 4), new Position(3, 5), new Position(3, 6))),
                Route.of(List.of(new Position(3, 4), new Position(3, 5), new Position(3, 6))),
                Route.of(List.of(new Position(3, 5), new Position(3, 6))),
                Route.of(List.of(new Position(3, 6))),
                Route.of(List.of(new Position(3, 8))),
                Route.of(List.of(new Position(3, 8), new Position(3, 9))),
                Route.of(List.of(new Position(4, 8))),
                Route.of(List.of(new Position(4, 8), new Position(5, 9)))
        );
    }

    @Test
    @DisplayName("차는 궁성 내 모서리에 있을 때 수직/수평으로만 움직인다.")
    void test3() {
        // given
        Chariot chariot = new Chariot(Team.CHO);

        // when
        List<Route> routes = chariot.calculateRoutes(new Position(3, 8));

        // then
        Assertions.assertThat(routes).hasSize(17);
        Assertions.assertThat(routes).containsOnly(
                Route.of(List.of(new Position(0, 8), new Position(1, 8), new Position(2, 8))),
                Route.of(List.of(new Position(1, 8), new Position(2, 8))),
                Route.of(List.of(new Position(2, 8))),
                Route.of(List.of(new Position(4, 8))),
                Route.of(List.of(new Position(4, 8), new Position(5, 8))),
                Route.of(List.of(new Position(4, 8), new Position(5, 8), new Position(6, 8))),
                Route.of(List.of(new Position(4, 8), new Position(5, 8), new Position(6, 8), new Position(7, 8))),
                Route.of(List.of(new Position(4, 8), new Position(5, 8), new Position(6, 8), new Position(7, 8), new Position(8, 8))),
                Route.of(List.of(new Position(3, 0), new Position(3, 1), new Position(3, 2), new Position(3, 3), new Position(3, 4), new Position(3, 5), new Position(3, 6), new Position(3, 7))),
                Route.of(List.of(new Position(3, 1), new Position(3, 2), new Position(3, 3), new Position(3, 4), new Position(3, 5), new Position(3, 6), new Position(3, 7))),
                Route.of(List.of(new Position(3, 2), new Position(3, 3), new Position(3, 4), new Position(3, 5), new Position(3, 6), new Position(3, 7))),
                Route.of(List.of(new Position(3, 3), new Position(3, 4), new Position(3, 5), new Position(3, 6), new Position(3, 7))),
                Route.of(List.of(new Position(3, 4), new Position(3, 5), new Position(3, 6), new Position(3, 7))),
                Route.of(List.of(new Position(3, 5), new Position(3, 6), new Position(3, 7))),
                Route.of(List.of(new Position(3, 6), new Position(3, 7))),
                Route.of(List.of(new Position(3, 7))),
                Route.of(List.of(new Position(3, 9)))
        );
    }

    @Test
    @DisplayName("차는 궁성 내 중앙에 있을 때 궁성 내에 한해 대각선으로 이동할 수 있다.")
    void test4() {
        // given
        Chariot chariot = new Chariot(Team.CHO);

        // when
        List<Route> routes = chariot.calculateRoutes(new Position(4, 8));

        // then
        Assertions.assertThat(routes).hasSize(21);
        Assertions.assertThat(routes).containsOnly(
                Route.of(List.of(new Position(0, 8), new Position(1, 8), new Position(2, 8), new Position(3, 8))),
                Route.of(List.of(new Position(1, 8), new Position(2, 8), new Position(3, 8))),
                Route.of(List.of(new Position(2, 8), new Position(3, 8))),
                Route.of(List.of(new Position(3, 8))),
                Route.of(List.of(new Position(5, 8))),
                Route.of(List.of(new Position(5, 8), new Position(6, 8))),
                Route.of(List.of(new Position(5, 8), new Position(6, 8), new Position(7, 8))),
                Route.of(List.of(new Position(5, 8), new Position(6, 8), new Position(7, 8), new Position(8, 8))),
                Route.of(List.of(new Position(4, 0), new Position(4, 1), new Position(4, 2), new Position(4, 3), new Position(4, 4), new Position(4, 5), new Position(4, 6), new Position(4, 7))),
                Route.of(List.of(new Position(4, 1), new Position(4, 2), new Position(4, 3), new Position(4, 4), new Position(4, 5), new Position(4, 6), new Position(4, 7))),
                Route.of(List.of(new Position(4, 2), new Position(4, 3), new Position(4, 4), new Position(4, 5), new Position(4, 6), new Position(4, 7))),
                Route.of(List.of(new Position(4, 3), new Position(4, 4), new Position(4, 5), new Position(4, 6), new Position(4, 7))),
                Route.of(List.of(new Position(4, 4), new Position(4, 5), new Position(4, 6), new Position(4, 7))),
                Route.of(List.of(new Position(4, 5), new Position(4, 6), new Position(4, 7))),
                Route.of(List.of(new Position(4, 6), new Position(4, 7))),
                Route.of(List.of(new Position(4, 7))),
                Route.of(List.of(new Position(4, 9))),
                Route.of(List.of(new Position(5, 9))),
                Route.of(List.of(new Position(3, 9))),
                Route.of(List.of(new Position(3, 7))),
                Route.of(List.of(new Position(5, 7)))
        );
    }
}
