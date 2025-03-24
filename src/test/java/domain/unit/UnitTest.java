package domain.unit;

import static org.assertj.core.api.Assertions.assertThat;

import domain.position.Position;
import domain.position.Route;
import domain.unit.rule.CannonUnitRule;
import domain.unit.rule.ChariotUnitRule;
import domain.unit.rule.ElephantUnitRule;
import domain.unit.rule.HorseUnitRule;
import domain.unit.rule.SoldierUnitRule;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UnitTest {
    @Test
    @DisplayName("기물(상)의 이동 가능한 모든 경로를 반환한다")
    void test1() {
        // given
        Unit unit = Unit.of(Team.CHO, new ElephantUnitRule());

        // when
        List<Route> routesOfCenter = unit.calculateRoutes(Position.of(4, 5));
        List<Route> routesOfCorner = unit.calculateRoutes(Position.of(0, 0));

        // then
        assertThat(routesOfCenter).hasSize(8);
        assertThat(routesOfCorner).hasSize(2);
    }

    @Test
    @DisplayName("기물(마)의 이동 가능한 모든 경로를 반환한다")
    void test2() {
        // given
        Unit unit = Unit.of(Team.CHO, new HorseUnitRule());

        // when
        List<Route> routesOfCenter = unit.calculateRoutes(Position.of(4, 5));
        List<Route> routesOfCorner = unit.calculateRoutes(Position.of(0, 0));

        // then
        assertThat(routesOfCenter).hasSize(8);
        assertThat(routesOfCorner).hasSize(2);
    }

    @Test
    @DisplayName("기물(차)의 이동 가능한 모든 경로를 반환한다")
    void test3() {
        // given
        Unit unit = Unit.of(Team.CHO, new ChariotUnitRule());

        // when
        List<Route> routesOfCenter = unit.calculateRoutes(Position.of(4, 5));
        List<Route> routesOfCorner = unit.calculateRoutes(Position.of(0, 0));

        // then
        assertThat(routesOfCenter).hasSize(17);
        assertThat(routesOfCorner).hasSize(17);
    }

    @Test
    @DisplayName("기물(포)의 이동 가능한 모든 경로를 반환한다")
    void test4() {
        // given
        Unit unit = Unit.of(Team.CHO, new CannonUnitRule());

        // when
        List<Route> routesOfCenter = unit.calculateRoutes(Position.of(4, 5));
        List<Route> routesOfCorner = unit.calculateRoutes(Position.of(0, 0));

        // then
        assertThat(routesOfCenter).hasSize(13);
        assertThat(routesOfCorner).hasSize(15);
    }

    @Test
    @DisplayName("기물(졸)의 이동 가능한 모든 경로를 반환한다")
    void test5() {
        // given
        Unit unit = Unit.of(Team.CHO, new SoldierUnitRule());

        // when
        List<Route> routesOfCenter = unit.calculateRoutes(Position.of(4, 5));
        List<Route> routesOfCorner = unit.calculateRoutes(Position.of(0, 0));

        // then
        assertThat(routesOfCenter).hasSize(4);
        assertThat(routesOfCorner).hasSize(2);
    }
}
