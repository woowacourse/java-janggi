package domain.unit;

import static org.assertj.core.api.Assertions.assertThat;

import domain.position.Position;
import domain.position.Routes;
import domain.unit.move.ElephantMovingStrategy;
import domain.unit.move.HorseMovingStrategy;
import domain.unit.move.OneStepMovingStrategy;
import domain.unit.move.StraightMovingStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UnitTest {
    @Test
    @DisplayName("기물(상)의 이동 가능한 모든 경로를 반환한다")
    void test1() {
        // given
        Unit unit = Unit.of(Team.CHO, new ElephantMovingStrategy(), UnitType.ELEPHANT);

        // when
        Routes routesOfCenter = unit.calculateRoutes(Position.of(4, 5));
        Routes routesOfCorner = unit.calculateRoutes(Position.of(0, 0));

        // then
        assertThat(routesOfCenter.getRoutes()).hasSize(8);
        assertThat(routesOfCorner.getRoutes()).hasSize(2);
    }

    @Test
    @DisplayName("기물(마)의 이동 가능한 모든 경로를 반환한다")
    void test2() {
        // given
        Unit unit = Unit.of(Team.CHO, new HorseMovingStrategy(), UnitType.HORSE);

        // when
        Routes routesOfCenter = unit.calculateRoutes(Position.of(4, 5));
        Routes routesOfCorner = unit.calculateRoutes(Position.of(0, 0));

        // then
        assertThat(routesOfCenter.getRoutes()).hasSize(8);
        assertThat(routesOfCorner.getRoutes()).hasSize(2);
    }

    @Test
    @DisplayName("기물(차)의 이동 가능한 모든 경로를 반환한다")
    void test3() {
        // given
        Unit unit = Unit.of(Team.CHO, new StraightMovingStrategy(), UnitType.CHARIOT);

        // when
        Routes routesOfCenter = unit.calculateRoutes(Position.of(4, 5));
        Routes routesOfCorner = unit.calculateRoutes(Position.of(0, 0));

        // then
        assertThat(routesOfCenter.getRoutes()).hasSize(17);
        assertThat(routesOfCorner.getRoutes()).hasSize(17);
    }

    @Test
    @DisplayName("기물(포)의 이동 가능한 모든 경로를 반환한다")
    void test4() {
        // given
        Unit unit = Unit.of(Team.CHO, new StraightMovingStrategy(), UnitType.CANNON);

        // when
        Routes routesOfCenter = unit.calculateRoutes(Position.of(4, 5));
        Routes routesOfCorner = unit.calculateRoutes(Position.of(0, 0));

        // then
        assertThat(routesOfCenter.getRoutes()).hasSize(17);
        assertThat(routesOfCorner.getRoutes()).hasSize(17);
    }

    @Test
    @DisplayName("기물(졸)의 이동 가능한 모든 경로를 반환한다")
    void test5() {
        // given
        Unit unit = Unit.of(Team.CHO, new OneStepMovingStrategy(), UnitType.SOLDIER);

        // when
        Routes routesOfCenter = unit.calculateRoutes(Position.of(4, 5));
        Routes routesOfCorner = unit.calculateRoutes(Position.of(0, 0));

        // then
        assertThat(routesOfCenter.getRoutes()).hasSize(4);
        assertThat(routesOfCorner.getRoutes()).hasSize(2);
    }

    @Test
    @DisplayName("차가 궁성에 있을 때의 경로를 반환한다.")
    void test6() {
        // given
        Unit chariot = Unit.of(Team.CHO, new StraightMovingStrategy(), UnitType.CHARIOT);

        // when
        Routes routesOfPalace = chariot.calculateRoutes(Position.of(3, 0));
        Routes routesOfPalaceCenter = chariot.calculateRoutes(Position.of(4, 1));
        Routes oppositeRoutesOfPalace = chariot.calculateRoutes(Position.of(5, 7));
        Routes oppositeRoutesOfPalaceCenter = chariot.calculateRoutes(Position.of(4, 8));

        // then
        assertThat(routesOfPalace.getRoutes()).hasSize(19);
        assertThat(routesOfPalaceCenter.getRoutes()).hasSize(21);
        assertThat(oppositeRoutesOfPalace.getRoutes()).hasSize(19);
        assertThat(oppositeRoutesOfPalaceCenter.getRoutes()).hasSize(21);
    }

    @Test
    @DisplayName("포가 궁성에 있을 때의 경로를 반환한다.")
    void test7() {
        // given
        Unit cannon = Unit.of(Team.CHO, new StraightMovingStrategy(), UnitType.CANNON);

        // when
        Routes routesOfPalace = cannon.calculateRoutes(Position.of(3, 0));
        Routes routesOfPalaceCenter = cannon.calculateRoutes(Position.of(4, 1));
        Routes oppositeRoutesOfPalace = cannon.calculateRoutes(Position.of(5, 7));
        Routes oppositeRoutesOfPalaceCenter = cannon.calculateRoutes(Position.of(4, 8));

        // then
        assertThat(routesOfPalace.getRoutes()).hasSize(19);
        assertThat(routesOfPalaceCenter.getRoutes()).hasSize(21);
        assertThat(oppositeRoutesOfPalace.getRoutes()).hasSize(19);
        assertThat(oppositeRoutesOfPalaceCenter.getRoutes()).hasSize(21);
    }

    @Test
    @DisplayName("졸병이 궁성에 있을 때의 경로를 반환한다.")
    void test8() {
        // given
        Unit soldier = Unit.of(Team.CHO, new OneStepMovingStrategy(), UnitType.SOLDIER);

        // when
        Routes routesOfPalace = soldier.calculateRoutes(Position.of(3, 2));
        Routes routesOfPalaceCenter = soldier.calculateRoutes(Position.of(4, 1));

        // then
        assertThat(routesOfPalace.getRoutes()).hasSize(5);
        assertThat(routesOfPalaceCenter.getRoutes()).hasSize(8);
    }

    @Test
    @DisplayName("궁의 경로를 반환한다")
    void test9() {
        // given
        Unit general = Unit.of(Team.CHO, new OneStepMovingStrategy(), UnitType.GENERAL);

        // when
        Routes routesOfCenter = general.calculateRoutes(Position.of(4, 8));
        Routes routesOfCorner = general.calculateRoutes(Position.of(5, 9));

        // then
        assertThat(routesOfCenter.getRoutes()).hasSize(8);
        assertThat(routesOfCorner.getRoutes()).hasSize(3);
    }

    @Test
    @DisplayName("사의 경로를 반환한다")
    void test10() {
        // given
        Unit guard = Unit.of(Team.CHO, new OneStepMovingStrategy(), UnitType.GENERAL);

        // when
        Routes routesOfCenter = guard.calculateRoutes(Position.of(4, 8));
        Routes routesOfCorner = guard.calculateRoutes(Position.of(5, 9));

        // then
        assertThat(routesOfCenter.getRoutes()).hasSize(8);
        assertThat(routesOfCorner.getRoutes()).hasSize(3);
    }
}
