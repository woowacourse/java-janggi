package domain.unit;

import static org.assertj.core.api.Assertions.assertThat;

import domain.position.Position;
import domain.unit.move.ElephantMovingStrategy;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UnitsTest {

    @Test
    @DisplayName("해당 위치가 비어있는지 비어있지 않은지 검사한다")
    void test1() {
        // given
        Unit unit = Unit.of(Team.CHO, new ElephantMovingStrategy(), UnitType.ELEPHANT);
        Position position = Position.of(4, 5);
        Map<Position, Unit> units = Map.of(position, unit);
        Units totalUnits = Units.of(units, Map.of());

        // when
        boolean notEmptyPosition = totalUnits.isNotEmptyPosition(Position.of(4, 5));
        boolean emptyPosition = totalUnits.isEmptyPosition(Position.of(4, 4));

        // then
        assertThat(notEmptyPosition).isTrue();
        assertThat(emptyPosition).isTrue();
    }

    @Test
    @DisplayName("해당 위치에 있는 장기말을 삭제한다")
    void test2() {
        // given
        Unit unit = Unit.of(Team.CHO, new ElephantMovingStrategy(), UnitType.ELEPHANT);
        Position position = Position.of(4, 5);
        Map<Position, Unit> units = Map.of(position, unit);
        Units totalUnits = Units.of(units, Map.of());

        // when
        totalUnits.removeUnitAt(Position.of(4, 5));

        // then
        assertThat(totalUnits.isEmptyPosition(Position.of(4, 5))).isTrue();
    }

    @Test
    @DisplayName("해당 위치의 장기말의 팀과 일치하는지 확인한다")
    void test3() {
        // given
        Unit unit = Unit.of(Team.CHO, new ElephantMovingStrategy(), UnitType.ELEPHANT);
        Position position = Position.of(4, 5);
        Map<Position, Unit> units = Map.of(position, unit);
        Units totalUnits = Units.of(units, Map.of());

        // when
        boolean same = totalUnits.isUnitTeamNotEqualAt(position, Team.HAN);
        boolean notSame = totalUnits.isUnitTeamNotEqualAt(position, Team.CHO);

        // then
        assertThat(same).isTrue();
        assertThat(notSame).isFalse();
    }

    @Test
    @DisplayName("장기말의 위치를 이동한다")
    void test5() {
        // given
        Unit unit = Unit.of(Team.CHO, new ElephantMovingStrategy(), UnitType.ELEPHANT);
        Position position = Position.of(4, 5);
        Map<Position, Unit> units = Map.of(position, unit);
        Units totalUnits = Units.of(units, Map.of());

        // when
        totalUnits.moveUnit(position, Position.of(4, 4));

        // then
        assertThat(totalUnits.isEmptyPosition(position)).isTrue();
        assertThat(totalUnits.isNotEmptyPosition(Position.of(4, 4))).isTrue();
    }
}
