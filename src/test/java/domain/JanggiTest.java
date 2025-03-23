package domain;

import static org.assertj.core.api.Assertions.assertThat;

import domain.position.Position;
import domain.position.Route;
import domain.unit.Team;
import domain.unit.Unit;
import domain.unit.rule.CannonUnitRule;
import domain.unit.rule.ChariotUnitRule;
import domain.unit.rule.ElephantUnitRule;
import domain.unit.rule.HorseUnitRule;
import domain.unit.rule.SoldierUnitRule;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JanggiTest {
    @Test
    @DisplayName("기물(상)의 타기물을 고려한 이동경로를 구한다")
    void test1() {
        // given
        Unit target = Unit.of(Team.CHO, new ElephantUnitRule());
        Position targetPosition = new Position(4, 5);
        Map<Position, Unit> units = Map.of(
                targetPosition, target,
                new Position(4, 4), Unit.of(Team.CHO, new SoldierUnitRule())
        );
        Janggi janggi = Janggi.of(units, Map.of(), Team.CHO);

        // when
        List<Route> routes = janggi.findMovableRoutesFrom(targetPosition);

        // then
        assertThat(routes).hasSize(6);
    }

    @Test
    @DisplayName("기물(마)의 타기물을 고려한 이동경로를 구한다")
    void test2() {
        // given
        Unit target = Unit.of(Team.CHO, new HorseUnitRule());
        Position targetPosition = new Position(4, 5);
        Map<Position, Unit> units = Map.of(
                targetPosition, target,
                new Position(4, 4), Unit.of(Team.CHO, new SoldierUnitRule())
        );
        Janggi janggi = Janggi.of(units, Map.of(), Team.CHO);

        // when
        List<Route> routes = janggi.findMovableRoutesFrom(targetPosition);

        // then
        assertThat(routes).hasSize(6);
    }

    @Test
    @DisplayName("기물(차)의 타기물을 고려한 이동경로를 구한다")
    void test3() {
        // given
        Unit target = Unit.of(Team.CHO, new ChariotUnitRule());
        Position targetPosition = new Position(4, 5);
        Map<Position, Unit> units = Map.of(
                targetPosition, target,
                new Position(4, 4), Unit.of(Team.CHO, new SoldierUnitRule())
        );
        Janggi janggi = Janggi.of(units, Map.of(), Team.CHO);

        // when
        List<Route> routes = janggi.findMovableRoutesFrom(targetPosition);

        // then
        assertThat(routes).hasSize(12);
    }

    @Test
    @DisplayName("기물(포)의 타기물을 고려한 이동경로를 구한다")
    void test4() {
        // given
        Unit target = Unit.of(Team.CHO, new CannonUnitRule());
        Position targetPosition = new Position(4, 5);
        Map<Position, Unit> units = Map.of(
                targetPosition, target,
                new Position(4, 4), Unit.of(Team.CHO, new SoldierUnitRule())
        );
        Janggi janggi = Janggi.of(units, Map.of(), Team.CHO);

        // when
        List<Route> routes = janggi.findMovableRoutesFrom(targetPosition);

        // then
        assertThat(routes).hasSize(4);
    }

    @Test
    @DisplayName("기물(졸)의 타기물을 고려한 이동경로를 구한다")
    void test5() {
        // given
        Unit target = Unit.of(Team.CHO, new SoldierUnitRule());
        Position targetPosition = new Position(4, 5);
        Map<Position, Unit> units = Map.of(
                targetPosition, target,
                new Position(4, 4), Unit.of(Team.CHO, new SoldierUnitRule())
        );
        Janggi janggi = Janggi.of(units, Map.of(), Team.CHO);

        // when
        List<Route> routes = janggi.findMovableRoutesFrom(targetPosition);

        // then
        assertThat(routes).hasSize(2);
    }
}
