package domain.strategy;

import domain.coordinate.Direction;
import domain.coordinate.Path;
import domain.coordinate.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PalaceStrategyTest {

    @Test
    @DisplayName("궁성 경계에서 궁성 밖으로 나가는 경로는 제외된다.")
    void filtersOutsidePalace() {
        // given
        Strategy strategy = new PalaceStrategy(new StepStrategy(
                List.of(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT)));
        Position start = new Position(2, 3); // 상단 궁성 좌하단 모서리

        // when
        List<Path> paths = strategy.getPaths(start);
        List<Position> destinations = paths.stream()
                .flatMap(path -> path.getPositions().stream())
                .toList();

        // then
        assertThat(destinations).doesNotContain(
                new Position(3, 3),  // 아래는 궁성 밖
                new Position(2, 2)   // 왼쪽은 궁성 밖
        );
    }

    @Test
    @DisplayName("하단 궁성 경계에서 궁성 밖으로 나가는 경로는 제외된다.")
    void filtersOutsideBottomPalace() {
        // given
        Strategy strategy = new PalaceStrategy(new StepStrategy(
                List.of(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT)));
        Position start = new Position(9, 5); // 하단 궁성 우하단 모서리

        // when
        List<Path> paths = strategy.getPaths(start);
        List<Position> destinations = paths.stream()
                .flatMap(path -> path.getPositions().stream())
                .toList();

        // then
        assertThat(destinations).doesNotContain(
                new Position(9, 6)  // 오른쪽은 궁성 밖
        );
    }
}