package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
        Position targetPosition = Position.of(4, 5);
        Map<Position, Unit> units = Map.of(
                targetPosition, target,
                Position.of(4, 4), Unit.of(Team.CHO, new SoldierUnitRule())
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
        Position targetPosition = Position.of(4, 5);
        Map<Position, Unit> units = Map.of(
                targetPosition, target,
                Position.of(4, 4), Unit.of(Team.CHO, new SoldierUnitRule())
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
        Position targetPosition = Position.of(4, 5);
        Map<Position, Unit> units = Map.of(
                targetPosition, target,
                Position.of(4, 4), Unit.of(Team.CHO, new SoldierUnitRule())
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
        Position targetPosition = Position.of(4, 5);
        Map<Position, Unit> units = Map.of(
                targetPosition, target,
                Position.of(4, 4), Unit.of(Team.CHO, new SoldierUnitRule())
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
        Position targetPosition = Position.of(4, 5);
        Map<Position, Unit> units = Map.of(
                targetPosition, target,
                Position.of(4, 4), Unit.of(Team.CHO, new SoldierUnitRule())
        );
        Janggi janggi = Janggi.of(units, Map.of(), Team.CHO);

        // when
        List<Route> routes = janggi.findMovableRoutesFrom(targetPosition);

        // then
        assertThat(routes).hasSize(2);
    }

    @Test
    @DisplayName("기물을 이동할 때 도착지가 비어있는 곳이라면 이동에 성공한다")
    void test6() {
        // given
        Unit target = Unit.of(Team.CHO, new SoldierUnitRule());
        Position targetPosition = Position.of(4, 5);
        Map<Position, Unit> units = Map.of(
                targetPosition, target,
                Position.of(4, 4), Unit.of(Team.CHO, new SoldierUnitRule())
        );
        Janggi janggi = Janggi.of(units, Map.of(), Team.CHO);

        // when
        Position destination = Position.of(3, 5);
        janggi.doTurn(targetPosition, destination);

        // then
        assertThat(janggi.getUnits().get(destination)).isEqualTo(target);
        assertThat(janggi.getUnits().get(targetPosition)).isNull();
    }

    @Test
    @DisplayName("기물을 이동할 때 도착지가 적군이라면 이동에 성공하고 적군을 삭제한다")
    void test7() {
        // given
        Unit target = Unit.of(Team.CHO, new SoldierUnitRule());
        Position targetPosition = Position.of(4, 5);
        Map<Position, Unit> units = Map.of(
                targetPosition, target
        );
        Map<Position, Unit> oppositeUnits = Map.of(
                Position.of(4, 4), Unit.of(Team.HAN, new SoldierUnitRule())
        );
        Janggi janggi = Janggi.of(units, oppositeUnits, Team.CHO);

        // when
        Position destination = Position.of(4, 4);
        janggi.doTurn(targetPosition, destination);

        // then
        assertThat(janggi.getUnits()).hasSize(1);
        assertThat(janggi.getUnits().get(destination)).isEqualTo(target);
    }

    @Test
    @DisplayName("기물을 이동할 때 적군을 선택하면 예외가 발생한다.")
    void test8() {
        // given
        Unit target = Unit.of(Team.CHO, new SoldierUnitRule());
        Position targetPosition = Position.of(4, 5);
        Map<Position, Unit> units = Map.of(
                targetPosition, target
        );
        Position oppositePosition = Position.of(4, 4);
        Map<Position, Unit> oppositeUnits = Map.of(
                oppositePosition, Unit.of(Team.HAN, new SoldierUnitRule())
        );
        Janggi janggi = Janggi.of(units, oppositeUnits, Team.CHO);

        // when & then
        assertThatThrownBy(() -> janggi.doTurn(oppositePosition, targetPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(Janggi.PICK_OPPOSITE_UNIT_EXCEPTION);
    }

    @Test
    @DisplayName("기물을 이동할 때 빈 곳을 선택하면 예외가 발생한다.")
    void test9() {
        // given
        Unit target = Unit.of(Team.CHO, new SoldierUnitRule());
        Position targetPosition = Position.of(4, 5);
        Map<Position, Unit> units = Map.of(
                targetPosition, target
        );
        Position oppositePosition = Position.of(4, 4);
        Map<Position, Unit> oppositeUnits = Map.of(
                oppositePosition, Unit.of(Team.HAN, new SoldierUnitRule())
        );
        Janggi janggi = Janggi.of(units, oppositeUnits, Team.CHO);

        // when & then
        assertThatThrownBy(() -> janggi.doTurn(Position.of(0, 0), oppositePosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(Janggi.EMPTY_POINT_EXCEPTION);
    }

    @Test
    @DisplayName("기물을 이동할 때 기물의 규칙에 맞지 않는 도착지를 선택하면 예외가 발생한다.")
    void test10() {
        // given
        Unit target = Unit.of(Team.CHO, new SoldierUnitRule());
        Position targetPosition = Position.of(4, 5);
        Map<Position, Unit> units = Map.of(
                targetPosition, target
        );
        Janggi janggi = Janggi.of(units, Map.of(), Team.CHO);

        // when & then
        assertThatThrownBy(() -> janggi.doTurn(targetPosition, Position.of(0, 0)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(Janggi.CANNOT_MOVE_EXCEPTION);
    }
}
