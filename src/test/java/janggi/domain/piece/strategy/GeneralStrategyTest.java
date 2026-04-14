package janggi.domain.piece.strategy;

import janggi.domain.Path;
import janggi.domain.JanggiPosition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GeneralStrategyTest {

    @DisplayName("장이 궁전 중앙이면 8방향의 경로를 반환한다")
    @Test
    void findMoveablePaths_InTheCenterPalace_ReturnEightRoutes() {
        MoveStrategy strategy = new GeneralStrategy();
        List<Path> paths = strategy.findMovablePaths(JanggiPosition.of(1, 4));

        List<JanggiPosition> destinations = paths.stream()
                .map(Path::destination)
                .toList();
        assertThat(destinations.contains(JanggiPosition.of(0, 3))).isTrue();
        assertThat(destinations.contains(JanggiPosition.of(0, 4))).isTrue();
        assertThat(destinations.contains(JanggiPosition.of(0, 5))).isTrue();
        assertThat(destinations.contains(JanggiPosition.of(1, 3))).isTrue();
        assertThat(destinations.contains(JanggiPosition.of(1, 5))).isTrue();
        assertThat(destinations.contains(JanggiPosition.of(2, 3))).isTrue();
        assertThat(destinations.contains(JanggiPosition.of(2, 4))).isTrue();
        assertThat(destinations.contains(JanggiPosition.of(2, 5))).isTrue();
    }

    @DisplayName("장이 궁전 중앙이 아니면 3방향의 경로를 반환한다")
    @ParameterizedTest
    @CsvSource({
            "0, 3",
            "0, 4",
            "0, 5",
            "1, 3",
            "1, 5",
            "2, 3",
            "2, 4",
            "2, 5",
    })
    void findMoveablePaths_NotInTheCenterPalace_ReturnEightRoutes(int row, int col) {
        MoveStrategy strategy = new GeneralStrategy();
        List<Path> paths = strategy.findMovablePaths(JanggiPosition.of(row, col));
        assertThat(paths).hasSize(3);
    }
}
