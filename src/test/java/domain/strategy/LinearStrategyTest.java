package domain.strategy;

import domain.coordinate.Path;
import domain.coordinate.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LinearStrategyTest {

    @Test
    @DisplayName("중앙 위치에서 상/하/좌/우 4방향의 경로를 생성한다.")
    void getPathsTest() {
        // given
        LinearStrategy strategy = new LinearStrategy();
        Position start = new Position(4, 4);

        // when
        List<Path> paths = strategy.getPaths(start);

        // then
        assertThat(paths).hasSize(4);
    }

    @Test
    @DisplayName("각 경로는 시작 위치에서 보드 끝까지의 위치를 포함한다.")
    void pathContainsAllPositionsToEdgeTest() {
        // given
        LinearStrategy strategy = new LinearStrategy();
        Position start = new Position(4, 4);

        // when
        List<Path> paths = strategy.getPaths(start);
        List<Position> allPositions = paths.stream()
                .flatMap(path -> path.getPositions().stream())
                .toList();

        // then
        assertThat(allPositions).contains(
                new Position(3, 4), new Position(0, 4),
                new Position(5, 4), new Position(9, 4),
                new Position(4, 3), new Position(4, 0),
                new Position(4, 5), new Position(4, 8)
        );
        assertThat(allPositions).doesNotContain(start);
    }

    @Test
    @DisplayName("보드 모서리에서는 범위 밖 방향의 경로가 비어있다.")
    void edgePositionTest() {
        // given
        LinearStrategy strategy = new LinearStrategy();
        Position start = new Position(0, 0);

        // when
        List<Path> paths = strategy.getPaths(start);
        long emptyPathCount = paths.stream()
                .filter(path -> path.getPositions().isEmpty())
                .count();

        // then
        assertThat(emptyPathCount).isEqualTo(2);
    }
}
