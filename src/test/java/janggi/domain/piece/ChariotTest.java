package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Team;
import janggi.domain.position.Position;
import janggi.domain.position.Route;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChariotTest {
    Piece chariot;

    private static List<Route> getRoutes(int x, int y) {
        final List<Route> downRoutes = new ArrayList<>();
        for (int i = y + 1; i <= 9; i++) {
            List<Position> positions = new ArrayList<>();
            for (int j = y + 1; j <= i; j++) {
                positions.add(new Position(x, j));
            }
            downRoutes.add(new Route(positions));
        }

        final List<Route> upRoutes = new ArrayList<>();
        for (int i = y - 1; i >= 0; i--) {
            List<Position> positions = new ArrayList<>();
            for (int j = y - 1; j >= i; j--) {
                positions.add(new Position(x, j));
            }
            upRoutes.add(new Route(positions));
        }

        final List<Route> rightRoutes = new ArrayList<>();
        for (int i = x + 1; i <= 8; i++) {
            List<Position> positions = new ArrayList<>();
            for (int j = x + 1; j <= i; j++) {
                positions.add(new Position(j, y));
            }
            rightRoutes.add(new Route(positions));
        }

        final List<Route> leftRoutes = new ArrayList<>();
        for (int i = x - 1; i >= 0; i--) {
            List<Position> positions = new ArrayList<>();
            for (int j = x - 1; j >= i; j--) {
                positions.add(new Position(j, y));
            }
            leftRoutes.add(new Route(positions));
        }

        final List<Route> allRoutes = new ArrayList<>();
        allRoutes.addAll(downRoutes);
        allRoutes.addAll(upRoutes);
        allRoutes.addAll(rightRoutes);
        allRoutes.addAll(leftRoutes);
        return allRoutes;
    }

    @DisplayName("해당 기물이 차 기물인지 확인한다.")
    @Test
    void isChariotTest() {
        chariot = new Chariot(new Position(4, 4), Team.RED);
        Assertions.assertThat(chariot.isSameType(PieceType.CHARIOT)).isTrue();
    }

    @DisplayName("차 기물이 올바른 루트를 계산하는지 확인한다.")
    @Test
    void calculateRoutesTest() {

        // given
        chariot = new Chariot(new Position(4, 4), Team.RED);
        final Set<Route> chariotRoutes = chariot.calculateRoutes();

        final List<Route> allRoutes = getRoutes(4, 4);

        // when
        final Set<Route> expected = Set.copyOf(allRoutes);

        // then
        assertThat(chariotRoutes).isEqualTo(expected);
    }

    @DisplayName("차 기물이 궁성 내에서(3, 0) 올바른 루트를 계산하는지 확인한다.")
    @Test
    void calculateRoutesTestInPalace() {
        //given
        chariot = new Chariot(new Position(3, 0), Team.RED);

        //when
        Set<Route> chariotRoutes = chariot.calculateRoutes();
        List<Route> allRoutes = getRoutes(3, 0);

        allRoutes.add(new Route(List.of(new Position(4, 1))));
        allRoutes.add(new Route(List.of(new Position(4, 1), new Position(5, 2))));
        final Set<Route> expected = Set.copyOf(allRoutes);

        //then
        assertThat(chariotRoutes).isEqualTo(expected);
    }
}
