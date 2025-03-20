package janggi.domain.piece;

import static janggi.domain.Team.RED;
import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Route;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CannonTest {

    @DisplayName("차 기물이 올바른 루트를 계산하는지 확인한다.")
    @Test
    void calculateRoutesTest() {

        // given
        final Piece cannon = new Cannon(new Position(4, 4), RED);
        final Set<Route> cannonRoutes = cannon.calculateRoutes();

        final List<Route> downRoutes = new ArrayList<>();
        for (int i = 5; i <= 9; i++) {
            List<Position> positions = new ArrayList<>();
            for (int j = 5; j <= i; j++) {
                positions.add(new Position(4, j));
            }
            downRoutes.add(new Route(positions));
        }

        final List<Route> upRoutes = new ArrayList<>();
        for (int i = 3; i >= 0; i--) {
            List<Position> positions = new ArrayList<>();
            for (int j = 3; j >= i; j--) {
                positions.add(new Position(4, j));
            }
            upRoutes.add(new Route(positions));
        }

        final List<Route> rightRoutes = new ArrayList<>();
        for (int i = 5; i <= 8; i++) {
            List<Position> positions = new ArrayList<>();
            for (int j = 5; j <= i; j++) {
                positions.add(new Position(j, 4));
            }
            rightRoutes.add(new Route(positions));
        }

        final List<Route> leftRoutes = new ArrayList<>();
        for (int i = 3; i >= 0; i--) {
            List<Position> positions = new ArrayList<>();
            for (int j = 3; j >= i; j--) {
                positions.add(new Position(j, 4));
            }
            leftRoutes.add(new Route(positions));
        }

        final List<Route> allRoutes = new ArrayList<>();
        allRoutes.addAll(downRoutes);
        allRoutes.addAll(upRoutes);
        allRoutes.addAll(rightRoutes);
        allRoutes.addAll(leftRoutes);

        // when
        final Set<Route> expected = Set.copyOf(allRoutes);

        // then
        assertThat(cannonRoutes).isEqualTo(expected);
    }
}
