package janggi.piece.pieces;

import janggi.piece.Team;
import janggi.position.Position;
import janggi.position.Route;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GeneralTest {
    @Test
    @DisplayName("궁 기물은 궁성 내에서만 이동할 수 있다.")
    void test() {
        // given
        General general = new General(Team.CHO);

        // when
        List<Route> routes = general.calculateRoutes(new Position(4, 8));

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
