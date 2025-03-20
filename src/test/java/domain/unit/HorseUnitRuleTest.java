package domain.unit;


import domain.Direction;
import domain.position.Point;
import domain.position.Route;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HorseUnitRuleTest {
    @Test
    @DisplayName("말")
    void test1() {
        // given
        HorseUnitRule horseUnitRule = new HorseUnitRule();

        // when
        List<Route> routes = new ArrayList<>();
        horseUnitRule.dfs(0, Direction.NONE, new ArrayList<>(), Point.of(2, 9), routes);
        for (Route route : routes) {
            System.out.println(route);
        }

        // then


    }

    @Test
    @DisplayName("코끼리")
    void test2() {
        // given
        ElephantUnitRule elephantUnitRule = new ElephantUnitRule();

        // when
        List<Route> routes = new ArrayList<>();
        elephantUnitRule.dfs(0, Direction.NONE, new ArrayList<>(), Point.of(1, 9), routes);
        for (Route route : routes) {
            System.out.println(route);
        }

        // then


    }

}