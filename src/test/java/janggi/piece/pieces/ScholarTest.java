package janggi.piece.pieces;

import janggi.piece.Team;
import janggi.position.Position;
import janggi.position.Route;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ScholarTest {
    @Test
    @DisplayName("사 기물은 궁성 내에서만 움직일 수 있다.")
    void test() {
        // given
        Scholar scholar = new Scholar(Team.CHO);

        // when
        List<Route> routes = scholar.calculateRoutes(new Position(4, 8));

        // then
        Assertions.assertThat(routes).hasSize(8);
        Assertions.assertThat(routes).containsOnly(
                Route.of(List.of(new Position(3, 7))),
                Route.of(List.of(new Position(4, 7))),
                Route.of(List.of(new Position(5, 7))),

                Route.of(List.of(new Position(3, 8))),
                Route.of(List.of(new Position(5, 8))),

                Route.of(List.of(new Position(3, 9))),
                Route.of(List.of(new Position(4, 9))),
                Route.of(List.of(new Position(5, 9)))
        );
    }
}
