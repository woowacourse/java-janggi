package domain.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Position;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChariotMoveStrategyTest {

    @Test
    @DisplayName("차 기물은 현재 위치 기준 모든 상하좌우 범위 내에 위치로 이동할 수 있어야 한다.")
    void chariot_move_test() {
        Position current = new Position(4, 4);
        Position target = new Position(9, 4);
        ChariotMoveStrategy moveStrategy = new ChariotMoveStrategy();

        assertThat(moveStrategy.canMoveTo(current, target)).isTrue();
    }

    @Test
    @DisplayName("차 기물은 제자리 이동이 불가능하다.")
    void chariot_cannot_move_same_position_test() {
        Position current = new Position(4, 4);
        Position target = new Position(4, 4);
        ChariotMoveStrategy moveStrategy = new ChariotMoveStrategy();

        assertThat(moveStrategy.canMoveTo(current, target)).isFalse();
    }

    @Test
    @DisplayName("차 기물은 현재 위치 기준 모든 상하좌우 범위 이동 이외에 이동할 수 없어야 한다.")
    void chariot_cannot_move_test() {
        Position current = new Position(4, 4);
        Position target = new Position(9, 5);
        ChariotMoveStrategy moveStrategy = new ChariotMoveStrategy();

        assertThat(moveStrategy.canMoveTo(current, target)).isFalse();
    }

    @Test
    @DisplayName("차 기물은 이동 경로에 기물 위치가 포함되는 여부를 반환할 수 있어야 한다.(막힘)")
    void chariot_blocked_route_test() {
        Position current = new Position(4, 4);
        Position target = new Position(4, 9);
        List<Position> piecePositions = List.of(new Position(4, 8));
        ChariotMoveStrategy moveStrategy = new ChariotMoveStrategy();

        assertThat(moveStrategy.hasValidPathTo(current, target, piecePositions)).isFalse();
    }

    @Test
    @DisplayName("차 기물은 이동 경로에 기물 위치가 포함되는 여부를 반환할 수 있어야 한다.(안 막힘)")
    void chariot_non_blocked_route_test() {
        Position current = new Position(4, 4);
        Position target = new Position(4, 9);
        List<Position> piecePositions = List.of(new Position(4, 3));
        ChariotMoveStrategy moveStrategy = new ChariotMoveStrategy();

        assertThat(moveStrategy.hasValidPathTo(current, target, piecePositions)).isTrue();
    }


}
