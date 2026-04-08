package domain.strategy;

import domain.vo.Position;
import domain.path.Path;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static domain.movement.Direction.DOWN_RIGHT;
import static domain.movement.Direction.RIGHT;
import static domain.movement.Direction.UP;
import static domain.movement.Direction.UP_RIGHT;
import static org.assertj.core.api.Assertions.assertThat;

class LinearMovementStrategyTest {

    @DisplayName("직선 경로는 LinearMovement로 계산해 반환한다.")
    @Test
    void 직선_경로는_LinearMovement로_계산해_반환한다() {
        PieceMoveStrategy strategy = new LinearMovementStrategy();
        List<Path> paths = List.of(new Path(List.of(UP)), new Path(List.of(RIGHT)));

        List<Position> actual = strategy.findRoute(paths, Position.of(5, 5), Position.of(5, 2));

        assertThat(actual).containsExactly(
            Position.of(5, 4),
            Position.of(5, 3),
            Position.of(5, 2)
        );
    }

    @DisplayName("대각선 단일 경로는 PathMovement로 계산해 반환한다.")
    @Test
    void 대각선_단일_경로는_PathMovement로_계산해_반환한다() {
        PieceMoveStrategy strategy = new LinearMovementStrategy();
        List<Path> paths = List.of(new Path(List.of(UP_RIGHT)));

        List<Position> actual = strategy.findRoute(paths, Position.of(5, 2), Position.of(6, 1));

        assertThat(actual).containsExactly(Position.of(6, 1));
    }

    @DisplayName("대각선 복수 경로도 PathMovement로 계산해 반환한다.")
    @Test
    void 대각선_복수_경로도_PathMovement로_계산해_반환한다() {
        PieceMoveStrategy strategy = new LinearMovementStrategy();
        List<Path> paths = List.of(new Path(List.of(DOWN_RIGHT, DOWN_RIGHT)));

        List<Position> actual = strategy.findRoute(paths, Position.of(4, 1), Position.of(6, 3));

        assertThat(actual).containsExactly(
            Position.of(5, 2),
            Position.of(6, 3)
        );
    }

    @DisplayName("어떤 경로로도 목적지에 도달할 수 없으면 예외가 발생한다.")
    @Test
    void 어떤_경로로도_목적지에_도달할_수_없으면_예외가_발생한다() {
        PieceMoveStrategy strategy = new LinearMovementStrategy();
        List<Path> paths = List.of(new Path(List.of(UP)), new Path(List.of(RIGHT)));

        Assertions.assertThatThrownBy(() -> strategy.findRoute(paths, Position.of(5, 5), Position.of(4, 4)))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("이동할 수 없는 목적지입니다.");
    }
}
