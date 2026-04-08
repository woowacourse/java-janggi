package domain.path;

import domain.movement.Direction;
import domain.vo.Position;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LinearPiecePathsTest {

    private final LinearPiecePaths paths = new LinearPiecePaths();

    @DisplayName("궁성이 아닌 위치에서는 상하좌우 경로만 반환한다.")
    @Test
    void 궁성이_아닌_위치에서는_상하좌우_경로만_반환한다() {
        List<List<Direction>> actual = toDirections(paths.getPaths(Position.of(1, 5)));

        assertThat(actual).containsExactly(
            List.of(Direction.UP),
            List.of(Direction.DOWN),
            List.of(Direction.RIGHT),
            List.of(Direction.LEFT)
        );
    }

    @DisplayName("궁성 중앙에서는 대각선 경로를 포함해 반환한다.")
    @Test
    void 궁성_중앙에서는_대각선_경로를_포함해_반환한다() {
        List<List<Direction>> actual = toDirections(paths.getPaths(Position.of(5, 2)));

        assertThat(actual).containsExactly(
            List.of(Direction.UP),
            List.of(Direction.DOWN),
            List.of(Direction.RIGHT),
            List.of(Direction.LEFT),
            List.of(Direction.UP_LEFT),
            List.of(Direction.UP_RIGHT),
            List.of(Direction.DOWN_LEFT),
            List.of(Direction.DOWN_RIGHT)
        );
    }

    @DisplayName("궁성 코너에서는 대각선 한 칸/두 칸 경로를 함께 반환한다.")
    @Test
    void 궁성_코너에서는_대각선_한칸_두칸_경로를_함께_반환한다() {
        List<List<Direction>> actual = toDirections(paths.getPaths(Position.of(4, 1)));

        assertThat(actual).containsExactly(
            List.of(Direction.UP),
            List.of(Direction.DOWN),
            List.of(Direction.RIGHT),
            List.of(Direction.LEFT),
            List.of(Direction.DOWN_RIGHT),
            List.of(Direction.DOWN_RIGHT, Direction.DOWN_RIGHT)
        );
    }

    private List<List<Direction>> toDirections(List<Path> paths) {
        return paths.stream()
            .map(Path::getPath)
            .toList();
    }
}
