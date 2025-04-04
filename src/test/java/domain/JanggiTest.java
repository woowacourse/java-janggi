package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.position.Position;
import domain.position.Routes;
import domain.unit.Team;
import domain.unit.Unit;
import domain.unit.UnitType;
import domain.unit.Units;
import domain.unit.move.ElephantMovingStrategy;
import domain.unit.move.HorseMovingStrategy;
import domain.unit.move.OneStepMovingStrategy;
import domain.unit.move.StraightMovingStrategy;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JanggiTest {
    @Test
    @DisplayName("기물(상)의 타기물을 고려한 이동경로를 구한다")
    void test1() {
        // given
        Unit target = Unit.of(Team.CHO, new ElephantMovingStrategy(), UnitType.ELEPHANT);
        Position targetPosition = Position.of(4, 5);
        Map<Position, Unit> units = Map.of(
                targetPosition, target,
                Position.of(4, 4), Unit.of(Team.CHO, new OneStepMovingStrategy(), UnitType.SOLDIER)
        );
        Units totalUnits = Units.of(units, Map.of());
        Janggi janggi = Janggi.of(totalUnits);

        // when
        Routes routes = janggi.findMovableRoutesFrom(targetPosition);

        // then
        assertThat(routes.getRoutes()).hasSize(6);
    }

    @Test
    @DisplayName("기물(마)의 타기물을 고려한 이동경로를 구한다")
    void test2() {
        // given
        Unit target = Unit.of(Team.CHO, new HorseMovingStrategy(), UnitType.HORSE);
        Position targetPosition = Position.of(4, 5);
        Map<Position, Unit> units = Map.of(
                targetPosition, target,
                Position.of(4, 4), Unit.of(Team.CHO, new OneStepMovingStrategy(), UnitType.SOLDIER)
        );
        Units totalUnits = Units.of(units, Map.of());
        Janggi janggi = Janggi.of(totalUnits);

        // when
        Routes routes = janggi.findMovableRoutesFrom(targetPosition);

        // then
        assertThat(routes.getRoutes()).hasSize(6);
    }

    @Test
    @DisplayName("기물(차)의 타기물을 고려한 이동경로를 구한다")
    void test3() {
        // given
        Unit target = Unit.of(Team.CHO, new StraightMovingStrategy(), UnitType.CHARIOT);
        Position targetPosition = Position.of(4, 5);
        Map<Position, Unit> units = Map.of(
                targetPosition, target,
                Position.of(4, 4), Unit.of(Team.CHO, new OneStepMovingStrategy(), UnitType.SOLDIER)
        );
        Units totalUnits = Units.of(units, Map.of());
        Janggi janggi = Janggi.of(totalUnits);

        // when
        Routes routes = janggi.findMovableRoutesFrom(targetPosition);

        // then
        assertThat(routes.getRoutes()).hasSize(12);
    }

    @Test
    @DisplayName("기물(포)의 타기물을 고려한 이동경로를 구한다")
    void test4() {
        // given
        Unit target = Unit.of(Team.CHO, new StraightMovingStrategy(), UnitType.CANNON);
        Position targetPosition = Position.of(4, 5);
        Map<Position, Unit> units = Map.of(
                targetPosition, target,
                Position.of(4, 4), Unit.of(Team.CHO, new OneStepMovingStrategy(), UnitType.SOLDIER)
        );
        Units totalUnits = Units.of(units, Map.of());
        Janggi janggi = Janggi.of(totalUnits);

        // when
        Routes routes = janggi.findMovableRoutesFrom(targetPosition);

        // then
        assertThat(routes.getRoutes()).hasSize(4);
    }

    @Test
    @DisplayName("기물(졸)의 타기물을 고려한 이동경로를 구한다")
    void test5() {
        // given
        Unit target = Unit.of(Team.CHO, new OneStepMovingStrategy(), UnitType.SOLDIER);
        Position targetPosition = Position.of(4, 5);
        Map<Position, Unit> units = Map.of(
                targetPosition, target,
                Position.of(4, 4), Unit.of(Team.CHO, new OneStepMovingStrategy(), UnitType.SOLDIER)
        );
        Units totalUnits = Units.of(units, Map.of());
        Janggi janggi = Janggi.of(totalUnits);

        // when
        Routes routes = janggi.findMovableRoutesFrom(targetPosition);

        // then
        assertThat(routes.getRoutes()).hasSize(2);
    }

    @Test
    @DisplayName("기물을 이동할 때 도착지가 비어있는 곳이라면 이동에 성공한다")
    void test6() {
        // given
        Unit target = Unit.of(Team.CHO, new OneStepMovingStrategy(), UnitType.SOLDIER);
        Position targetPosition = Position.of(4, 5);
        Map<Position, Unit> units = Map.of(
                targetPosition, target,
                Position.of(4, 4), Unit.of(Team.CHO, new OneStepMovingStrategy(), UnitType.SOLDIER)
        );
        Units totalUnits = Units.of(units, Map.of());
        Janggi janggi = Janggi.of(totalUnits);

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
        Unit target = Unit.of(Team.CHO, new OneStepMovingStrategy(), UnitType.SOLDIER);
        Position targetPosition = Position.of(4, 5);
        Map<Position, Unit> units = Map.of(
                targetPosition, target
        );
        Map<Position, Unit> oppositeUnits = Map.of(
                Position.of(4, 4), Unit.of(Team.HAN, new OneStepMovingStrategy(), UnitType.SOLDIER)
        );
        Units totalUnits = Units.of(units, oppositeUnits);
        Janggi janggi = Janggi.of(totalUnits);

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
        Unit target = Unit.of(Team.CHO, new OneStepMovingStrategy(), UnitType.SOLDIER);
        Position targetPosition = Position.of(4, 5);
        Map<Position, Unit> units = Map.of(
                targetPosition, target
        );
        Position oppositePosition = Position.of(4, 4);
        Map<Position, Unit> oppositeUnits = Map.of(
                oppositePosition, Unit.of(Team.HAN, new OneStepMovingStrategy(), UnitType.SOLDIER)
        );
        Units totalUnits = Units.of(units, oppositeUnits);
        Janggi janggi = Janggi.of(totalUnits);

        // when & then
        assertThatThrownBy(() -> janggi.doTurn(oppositePosition, targetPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상대팀 말은 고를 수 없습니다.");
    }

    @Test
    @DisplayName("기물을 이동할 때 빈 곳을 선택하면 예외가 발생한다.")
    void test9() {
        // given
        Unit target = Unit.of(Team.CHO, new OneStepMovingStrategy(), UnitType.SOLDIER);
        Position targetPosition = Position.of(4, 5);
        Map<Position, Unit> units = Map.of(
                targetPosition, target
        );
        Position oppositePosition = Position.of(4, 4);
        Map<Position, Unit> oppositeUnits = Map.of(
                oppositePosition, Unit.of(Team.HAN, new OneStepMovingStrategy(), UnitType.SOLDIER)
        );
        Units totalUnits = Units.of(units, oppositeUnits);
        Janggi janggi = Janggi.of(totalUnits);

        // when & then
        assertThatThrownBy(() -> janggi.doTurn(Position.of(0, 0), oppositePosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치에 기물이 존재하지 않습니다.");
    }

    @Test
    @DisplayName("기물을 이동할 때 기물의 규칙에 맞지 않는 도착지를 선택하면 예외가 발생한다.")
    void test10() {
        // given
        Unit target = Unit.of(Team.CHO, new OneStepMovingStrategy(), UnitType.SOLDIER);
        Position targetPosition = Position.of(4, 5);
        Map<Position, Unit> units = Map.of(
                targetPosition, target
        );
        Units totalUnits = Units.of(units, Map.of());
        Janggi janggi = Janggi.of(totalUnits);

        // when & then
        assertThatThrownBy(() -> janggi.doTurn(targetPosition, Position.of(0, 0)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동할 수 없는 도착지입니다.");
    }

    @Test
    @DisplayName("두 진영 중 하나의 궁이 없다면 게임은 끝났다.")
    void test11() {
        // given
        Unit target = Unit.of(Team.CHO, new OneStepMovingStrategy(), UnitType.GENERAL);
        Position targetPosition = Position.of(4, 5);
        Map<Position, Unit> units = Map.of(
                targetPosition, target
        );
        Units totalUnits = Units.of(units, Map.of());
        Janggi janggi = Janggi.of(totalUnits);

        // when
        boolean isEnd = janggi.isEnd();

        // then
        assertThat(isEnd).isTrue();
    }

    @Test
    @DisplayName("두 궁이 모두 존재하면 게임은 진행중이다")
    void test12() {
        // given
        Unit target1 = Unit.of(Team.CHO, new OneStepMovingStrategy(), UnitType.GENERAL);
        Unit target2 = Unit.of(Team.HAN, new OneStepMovingStrategy(), UnitType.GENERAL);
        Unit soldier = Unit.of(Team.CHO, new OneStepMovingStrategy(), UnitType.SOLDIER);
        Position targetPosition1 = Position.of(4, 5);
        Position targetPosition2 = Position.of(4, 6);
        Position position = Position.of(4, 7);
        Map<Position, Unit> units = Map.of(
                targetPosition1, target1,
                targetPosition2, target2,
                position, soldier
        );
        Units totalUnits = Units.of(units, Map.of());
        Janggi janggi = Janggi.of(totalUnits);

        // when
        boolean isEnd = janggi.isEnd();

        // then
        assertThat(isEnd).isFalse();
    }

    @Test
    @DisplayName("포가 포를 잡을 수 없다")
    void test13() {
        // given
        Unit target = Unit.of(Team.CHO, new StraightMovingStrategy(), UnitType.CANNON);
        Position targetPosition = Position.of(0, 0);
        Map<Position, Unit> units = Map.of(
                targetPosition, target,
                Position.of(0, 1), Unit.of(Team.CHO, new OneStepMovingStrategy(), UnitType.SOLDIER)
        );
        Unit oppositeCannon = Unit.of(Team.CHO, new StraightMovingStrategy(), UnitType.CANNON);
        Position oppositePosition = Position.of(0, 3);
        Map<Position, Unit> oppositeUnits = Map.of(
                oppositePosition, oppositeCannon
        );
        Units totalUnits = Units.of(units, oppositeUnits);
        Janggi janggi = Janggi.of(totalUnits);

        // when
        Routes movableRoutesFrom = janggi.findMovableRoutesFrom(targetPosition);

        // then
        assertThat(movableRoutesFrom.getRoutes()).hasSize(1);
    }

    @Test
    @DisplayName("궁이 잡힌 상황의 우승자를 반환한다")
    void test14() {
        // given
        Unit choGeneral = Unit.of(Team.CHO, new OneStepMovingStrategy(), UnitType.GENERAL);
        Position targetPosition = Position.of(0, 0);
        Map<Position, Unit> units = Map.of(
                targetPosition, choGeneral,
                Position.of(0, 1), Unit.of(Team.CHO, new OneStepMovingStrategy(), UnitType.SOLDIER)
        );
        Unit oppositeCannon = Unit.of(Team.HAN, new StraightMovingStrategy(), UnitType.CANNON);
        Position oppositePosition = Position.of(0, 3);
        Map<Position, Unit> oppositeUnits = Map.of(
                oppositePosition, oppositeCannon
        );
        Units totalUnits = Units.of(units, oppositeUnits);
        Janggi janggi = Janggi.of(totalUnits);

        // when
        Team winner = janggi.getWinner();

        // then
        assertThat(winner).isEqualTo(Team.CHO);
    }
}
