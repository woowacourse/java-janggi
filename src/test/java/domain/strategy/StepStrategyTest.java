package domain.strategy;

import domain.coordinate.Path;
import domain.coordinate.Position;
import domain.coordinate.Topology;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class StepStrategyTest {

    private static final Topology DEFAULT_TOPOLOGY = new Topology(Map.of());

    @Test
    @DisplayName("주어진 방향들로 1칸씩 이동하는 경로를 생성한다.")
    void getPathsTest() {
        // given
        StepStrategy strategy = new StepStrategy();
        Position start = new Position(4, 4);

        // when
        List<Path> paths = strategy.getPaths(start, DEFAULT_TOPOLOGY);
        List<Position> destinations = paths.stream()
                .map(path -> path.getPositions().getFirst())
                .toList();

        // then
        assertThat(destinations).containsOnly(
                new Position(3, 4),
                new Position(5, 4),
                new Position(4, 3),
                new Position(4, 5)
        );
    }

    @Test
    @DisplayName("보드 경계에서 범위 밖 방향은 경로에서 제외된다.")
    void edgeFilterTest() {
        // given
        StepStrategy strategy = new StepStrategy();
        Position start = new Position(0, 0);

        // when
        List<Path> paths = strategy.getPaths(start, DEFAULT_TOPOLOGY);
        List<Position> destinations = paths.stream()
                .map(path -> path.getPositions().getFirst())
                .toList();

        // then
        assertThat(destinations).containsOnly(
                new Position(1, 0),
                new Position(0, 1)
        );
    }

    @Test
    @DisplayName("각 경로는 정확히 1개의 위치를 포함한다.")
    void singleStepPathTest() {
        // given
        StepStrategy strategy = new StepStrategy();
        Position start = new Position(5, 4);

        // when
        List<Path> paths = strategy.getPaths(start, DEFAULT_TOPOLOGY);

        // then
        assertThat(paths).allMatch(path -> path.getPositions().size() == 1);
    }
}
