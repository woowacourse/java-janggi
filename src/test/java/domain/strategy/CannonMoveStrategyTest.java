package domain.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Position;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CannonMoveStrategyTest {


    @Test
    @DisplayName("포 기물은 현재 위치 기준 상하좌우 방향으로 바로 1칸을 제외한 나머지 위치로 이동할 수 있어야 한다.")
    void cannon_move_test() {
        Position current = Position.of(4, 4);
        Position target = Position.of(9, 4);
        CannonMoveStrategy moveStrategy = CannonMoveStrategy.of(current);

        assertThat(moveStrategy.isMoveAble(target)).isTrue();
    }

    @Test
    @DisplayName("포 기물은 이동 경로에 기물이 단 하나만 포함되는지 여부를 판단할 수 있어야 한다.(경로에 기물 1개)")
    void cannon_valid_path_test() {
        Position current = Position.of(9, 4);
        Position target = Position.of(4, 4);
        List<Position> piecePositions = List.of(Position.of(5, 4));
        CannonMoveStrategy moveStrategy = CannonMoveStrategy.of(current);

        assertThat(moveStrategy.isPathRestricted(target, piecePositions)).isFalse();
    }

    @Test
    @DisplayName("포 기물은 이동 경로에 기물이 단 하나만 포함되는지 여부를 판단할 수 있어야 한다.(경로에 기물 1개 x)")
    void cannon_invalid_path_test() {
        Position current = Position.of(4, 4);
        Position target = Position.of(4, 9);
        List<Position> piecePositions = List.of(Position.of(4, 8), Position.of(4, 7));
        CannonMoveStrategy moveStrategy = CannonMoveStrategy.of(current);

        assertThat(moveStrategy.isPathRestricted(target, piecePositions)).isTrue();
    }
}
