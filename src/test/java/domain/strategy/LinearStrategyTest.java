package domain.strategy;

import domain.board.BasicBoardInitializer;
import domain.coordinate.Path;
import domain.coordinate.Position;
import domain.coordinate.Topology;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class LinearStrategyTest {

    private static final Topology DEFAULT_TOPOLOGY = new Topology(Map.of());
    private static final Topology PALACE_TOPOLOGY = new BasicBoardInitializer().createTopology();

    @Test
    @DisplayName("중앙 위치에서 상/하/좌/우 4방향의 경로를 생성한다.")
    void getPathsTest() {
        // given
        LinearStrategy strategy = new LinearStrategy();
        Position start = new Position(4, 4);

        // when
        List<Path> paths = strategy.getPaths(start, DEFAULT_TOPOLOGY);

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
        List<Path> paths = strategy.getPaths(start, DEFAULT_TOPOLOGY);
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
        List<Path> paths = strategy.getPaths(start, DEFAULT_TOPOLOGY);
        long emptyPathCount = paths.stream()
                .filter(path -> path.getPositions().isEmpty())
                .count();

        // then
        assertThat(emptyPathCount).isEqualTo(2);
    }

    @Test
    @DisplayName("궁성 꼭짓점에서 대각선 방향 경로가 추가로 생성된다.")
    void palaceDiagonalPathTest() {
        // given
        LinearStrategy strategy = new LinearStrategy();
        Position start = new Position(0, 3); // 상단 궁성 좌상단 꼭짓점

        // when
        List<Path> paths = strategy.getPaths(start, PALACE_TOPOLOGY);

        // then — 기본 4방향 + 대각선 1방향 = 5개
        assertThat(paths).hasSize(5);
    }

    @Test
    @DisplayName("궁성 대각선 경로는 궁성 끝에서 멈춘다.")
    void palaceDiagonalStopsAtBoundary() {
        // given
        LinearStrategy strategy = new LinearStrategy();
        Position start = new Position(0, 3); // 상단 궁성 좌상단 꼭짓점

        // when
        List<Path> paths = strategy.getPaths(start, PALACE_TOPOLOGY);
        List<Position> allPositions = paths.stream()
                .flatMap(path -> path.getPositions().stream())
                .toList();

        // then — 대각선은 (1,4), (2,5)까지만
        assertThat(allPositions).contains(
                new Position(1, 4),
                new Position(2, 5)
        );
        assertThat(allPositions).doesNotContain(
                new Position(3, 6)  // 궁성 밖으로 이어지지 않음
        );
    }

    @Test
    @DisplayName("궁성 중앙에서는 4방향 대각선 경로가 모두 생성된다.")
    void palaceCenterDiagonalTest() {
        // given
        LinearStrategy strategy = new LinearStrategy();
        Position start = new Position(1, 4); // 상단 궁성 중앙

        // when
        List<Path> paths = strategy.getPaths(start, PALACE_TOPOLOGY);

        // then — 기본 4방향 + 대각선 4방향 = 8개
        assertThat(paths).hasSize(8);
    }
}
