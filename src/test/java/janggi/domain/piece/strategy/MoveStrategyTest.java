package janggi.domain.piece.strategy;

import janggi.domain.Camp;
import janggi.domain.Path;
import janggi.domain.Position;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MoveStrategyTest {

    @Nested
    class 병_움직임_전략 {

        @ParameterizedTest
        @EnumSource(Camp.class)
        void 각_진형_병은_앞과_양_옆으로_움직인다(Camp camp) {
            MoveStrategy strategy = new SoldierStrategy(camp.direction());
            List<Path> paths = strategy.findMovablePaths(Position.of(4, 4));
            assertThat(paths.get(0).destination()).isEqualTo(Position.of(4 + camp.direction(), 4));
            assertThat(paths.get(1).destination()).isEqualTo(Position.of(4, 5));
            assertThat(paths.get(2).destination()).isEqualTo(Position.of(4, 3));
        }

        @Test
        @DisplayName("포는 현재 위치에서 가로와 세로 직선상의 모든 좌표를 후보로 반환한다")
        void findMovablePaths_ReturnAllLinearCandidates() {
            MoveStrategy strategy = new CannonStrategy();
            int row = 4;
            int column = 4;

            List<Path> paths = strategy.findMovablePaths(Position.of(row, column));
            List<Position> destinations = paths.stream()
                    .map(Path::destination)
                    .toList();

            for (int i = 0; i < 9; i++) {
                if (column - 1 <= i && i <= column + 1) {
                    assertThat(destinations).doesNotContain(Position.of(4, i));
                    continue;
                }
                assertThat(destinations).contains(Position.of(4, i));
            }

            for (int i = 0; i <= 9; i++) {
                if (row - 1 <= i && i <= row + 1) {
                    assertThat(destinations).doesNotContain(Position.of(i, 4));
                    continue;
                }
                assertThat(destinations).contains(Position.of(i, 4));
            }
        }
    }
}