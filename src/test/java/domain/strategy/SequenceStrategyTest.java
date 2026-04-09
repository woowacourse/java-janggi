package domain.strategy;

import domain.coordinate.Direction;
import domain.coordinate.DirectionSequence;
import domain.coordinate.Path;
import domain.coordinate.Position;
import domain.coordinate.Topology;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class SequenceStrategyTest {

    private static final Topology DEFAULT_TOPOLOGY = new Topology(Map.of());

    @Test
    @DisplayName("방향 시퀀스에 따라 경유지를 포함한 경로를 생성한다.")
    void getPathsTest() {
        // given
        SequenceStrategy strategy = new SequenceStrategy(List.of(
                DirectionSequence.of(Direction.UP, Direction.UP_LEFT),
                DirectionSequence.of(Direction.UP, Direction.UP_RIGHT)
        ));
        Position start = new Position(4, 4);

        // when
        List<Path> paths = strategy.getPaths(start, DEFAULT_TOPOLOGY);

        // then
        assertThat(paths).hasSize(2);
        assertThat(paths.get(0).getPositions()).containsExactly(
                new Position(3, 4), new Position(2, 3)
        );
        assertThat(paths.get(1).getPositions()).containsExactly(
                new Position(3, 4), new Position(2, 5)
        );
    }

    @Test
    @DisplayName("시퀀스 도중 보드 범위를 벗어나면 해당 경로는 제외된다.")
    void outOfBoundsSequenceTest() {
        // given
        SequenceStrategy strategy = new SequenceStrategy(List.of(
                DirectionSequence.of(Direction.UP, Direction.UP_LEFT),
                DirectionSequence.of(Direction.DOWN, Direction.DOWN_LEFT)
        ));
        Position start = new Position(0, 0);

        // when
        List<Path> paths = strategy.getPaths(start, DEFAULT_TOPOLOGY);

        // then
        assertThat(paths).isEmpty();
    }

    @Test
    @DisplayName("마의 이동 패턴으로 8방향 경로를 생성한다.")
    void horsePatternTest() {
        // given
        SequenceStrategy strategy = new SequenceStrategy(List.of(
                DirectionSequence.of(Direction.UP, Direction.UP_LEFT),
                DirectionSequence.of(Direction.UP, Direction.UP_RIGHT),
                DirectionSequence.of(Direction.DOWN, Direction.DOWN_LEFT),
                DirectionSequence.of(Direction.DOWN, Direction.DOWN_RIGHT),
                DirectionSequence.of(Direction.LEFT, Direction.UP_LEFT),
                DirectionSequence.of(Direction.LEFT, Direction.DOWN_LEFT),
                DirectionSequence.of(Direction.RIGHT, Direction.UP_RIGHT),
                DirectionSequence.of(Direction.RIGHT, Direction.DOWN_RIGHT)
        ));
        Position start = new Position(4, 4);

        // when
        List<Path> paths = strategy.getPaths(start, DEFAULT_TOPOLOGY);

        // then
        assertThat(paths).hasSize(8);
        List<Position> destinations = paths.stream()
                .map(path -> path.getPositions().getLast())
                .toList();
        assertThat(destinations).containsOnly(
                new Position(2, 3), new Position(2, 5),
                new Position(6, 3), new Position(6, 5),
                new Position(3, 2), new Position(5, 2),
                new Position(3, 6), new Position(5, 6)
        );
    }
}