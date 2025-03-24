package janggi.domain.piece;

import static janggi.domain.Team.RED;
import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.position.Position;
import janggi.domain.position.Route;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GeneralTest {
    Piece general;

    @BeforeEach
    void makeGeneral() {
        general = new General(new Position(1, 1), RED);
    }

    @DisplayName("해당 기물이 왕 기물인지 확인한다.")
    @Test
    void isGeneralTest() {
        Assertions.assertThat(general.isSameType(PieceType.GENERAL)).isTrue();
    }

    @DisplayName("왕 기물이 올바른 루트를 계산하는지 확인한다.")
    @Test
    void calculateRoutesTest() {

        // given
        final Set<Route> soliderRoutes = general.calculateRoutes();

        final Route route1 = new Route(new ArrayList<>(List.of(new Position(0, 1))));
        final Route route2 = new Route(new ArrayList<>(List.of(new Position(2, 1))));
        final Route route3 = new Route(new ArrayList<>(List.of(new Position(1, 0))));
        final Route route4 = new Route(new ArrayList<>(List.of(new Position(1, 2))));

        // when
        final Set<Route> expected = Set.of(route2, route1, route3, route4);

        // then
        assertThat(soliderRoutes).isEqualTo(expected);
    }

}
