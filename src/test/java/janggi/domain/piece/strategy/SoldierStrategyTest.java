package janggi.domain.piece.strategy;

import janggi.domain.Camp;
import janggi.domain.Path;
import janggi.domain.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SoldierStrategyTest {

    @DisplayName("병이 현재 위치에서 앞, 양 옆 1칸씩의 좌표를 도착지점 후보로 반환한다")
    @ParameterizedTest
    @EnumSource(Camp.class)
    void findMoveablePaths_GetCurrentPosition_ReturnAllPossibleRoutes(Camp camp) {
        MoveStrategy strategy = new SoldierStrategy(camp.forward());
        Position current = Position.of(4, 4);
        List<Path> paths = strategy.findMovablePaths(current);
        Position expectedForwardPosition = camp.forward().findNextPosition(current).get();

        assertThat(paths.get(0).destination()).isEqualTo(expectedForwardPosition);
        assertThat(paths.get(1).destination()).isEqualTo(Position.of(4, 3));
        assertThat(paths.get(2).destination()).isEqualTo(Position.of(4, 5));
    }

}
