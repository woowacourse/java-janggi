package domain.strategy;

import domain.vo.Position;
import domain.path.Path;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static domain.movement.Direction.RIGHT;
import static domain.movement.Direction.UP;
import static org.assertj.core.api.Assertions.assertThat;

class PathMovementStrategyTest {

    @DisplayName("경로 중 목적지에 도달하는 경로를 찾으면 해당 경로를 반환한다.")
    @Test
    void 경로_중_목적지에_도달하는_경로를_찾으면_해당_경로를_반환한다() {
        PieceMoveStrategy strategy = new PathMovementStrategy();
        List<Path> paths = List.of(new Path(List.of(UP)), new Path(List.of(RIGHT)));

        List<Position> actual = strategy.findRoute(paths, Position.of(5, 5), Position.of(6, 5));

        assertThat(actual).containsExactly(Position.of(6, 5));
    }

    @DisplayName("목적지에 도달하는 경로가 없으면 예외가 발생한다.")
    @Test
    void 목적지에_도달하는_경로가_없으면_예외가_발생한다() {
        PieceMoveStrategy strategy = new PathMovementStrategy();
        List<Path> paths = List.of(new Path(List.of(UP)), new Path(List.of(RIGHT)));

        Assertions.assertThatThrownBy(() -> strategy.findRoute(paths, Position.of(5, 5), Position.of(4, 5)))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("이동할 수 없는 목적지입니다.");
    }
}
