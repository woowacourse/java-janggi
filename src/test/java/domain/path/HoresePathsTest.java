package domain.path;

import static org.assertj.core.api.Assertions.assertThat;

import domain.vo.Position;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HoresePathsTest {

    @DisplayName("마 경로는 좌표와 무관하게 동일한 8개 경로를 반환한다.")
    @Test
    void 마_경로는_좌표와_무관하게_동일한_여덟개_경로를_반환한다() {
        HoresePaths paths = new HoresePaths();

        List<List<Direction>> fromTop = toDirections(paths.getPaths(Position.of(1, 1)));
        List<List<Direction>> fromBottom = toDirections(paths.getPaths(Position.of(9, 10)));

        assertThat(fromTop).containsExactly(
            List.of(Direction.UP, Direction.UP_LEFT),
            List.of(Direction.UP, Direction.UP_RIGHT),
            List.of(Direction.RIGHT, Direction.UP_RIGHT),
            List.of(Direction.RIGHT, Direction.DOWN_RIGHT),
            List.of(Direction.DOWN, Direction.DOWN_LEFT),
            List.of(Direction.DOWN, Direction.DOWN_RIGHT),
            List.of(Direction.LEFT, Direction.UP_LEFT),
            List.of(Direction.LEFT, Direction.DOWN_LEFT)
        );
        assertThat(fromBottom).isEqualTo(fromTop);
    }

    private List<List<Direction>> toDirections(List<Path> paths) {
        return paths.stream()
            .map(Path::getPath)
            .toList();
    }
}
