package domain.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Position;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CannonMoveStrategyTest {


    @Test
    @DisplayName("포 기물은 현재 위치 기준 상하좌우 방향으로 바로 1칸을 제외한 나머지 위치로 이동할 수 있어야 한다.")
    void cannon_can_move_test() {
        Position current = new Position(4, 4);
        Position destination = new Position(9, 4);
        CannonMoveStrategy moveStrategy = new CannonMoveStrategy();

        assertThat(moveStrategy.canMoveTo(current, destination)).isTrue();
    }

    @Test
    @DisplayName("포 기물의 현재 위치 기준 상하좌우 방향으로 바로 1칸은 이동할 수 없다.")
    void cannon_cannot_move_test() {
        Position current = new Position(4, 4);
        Position destination = new Position(4, 5);
        CannonMoveStrategy moveStrategy = new CannonMoveStrategy();

        assertThat(moveStrategy.canMoveTo(current, destination)).isFalse();
    }

    @Test
    @DisplayName("포 기물은 이동 경로에 기물이 단 하나만 포함되는지 여부를 판단할 수 있어야 한다.(경로에 기물 1개)")
    void cannon_can_move_hasValidPathTo_valid_path_test() {
        Position current = new Position(9, 4);
        Position destination = new Position(4, 4);
        List<Position> obstacles = List.of(new Position(5, 4));
        CannonMoveStrategy moveStrategy = new CannonMoveStrategy();

        assertThat(moveStrategy.hasValidPathTo(current, destination, obstacles)).isTrue();
    }

    @Test
    @DisplayName("포 기물은 이동 경로에 기물이 단 하나만 포함되는지 여부를 판단할 수 있어야 한다.(경로에 기물 1개 x)")
    void cannon_cannot_move_hasValidPathTo_invalid_path_test() {
        Position current = new Position(4, 4);
        Position destination = new Position(4, 9);
        List<Position> obstacles = List.of(new Position(4, 8), new Position(4, 7));
        CannonMoveStrategy moveStrategy = new CannonMoveStrategy();

        assertThat(moveStrategy.hasValidPathTo(current, destination, obstacles)).isFalse();
    }
}
