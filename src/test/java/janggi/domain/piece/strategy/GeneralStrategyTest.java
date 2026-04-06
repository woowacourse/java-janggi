package janggi.domain.piece.strategy;

import janggi.domain.Path;
import janggi.domain.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GeneralStrategyTest {

    @DisplayName("장은는 현재 위치에서 앞, 뒤, 양 옆을 1칸씩의 좌표를 도착지점 후보로 반환한다")
    @Test
    void findMoveablePaths_GetCurrentPosition_ReturnAllPossibleRoutes() {
        MoveStrategy strategy = new GeneralStrategy();
        List<Path> paths = strategy.findMovablePaths(Position.of(4, 4));
        assertThat(paths.get(0).destination()).isEqualTo(Position.of(5, 4));
        assertThat(paths.get(1).destination()).isEqualTo(Position.of(3, 4));
        assertThat(paths.get(2).destination()).isEqualTo(Position.of(4, 5));
        assertThat(paths.get(3).destination()).isEqualTo(Position.of(4, 3));
    }
}
