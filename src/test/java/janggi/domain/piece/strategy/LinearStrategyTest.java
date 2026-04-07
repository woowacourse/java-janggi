package janggi.domain.piece.strategy;

import janggi.domain.Palaces;
import janggi.domain.Paths;
import janggi.domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LinearStrategyTest {

    @Test
    @DisplayName("직선 4방향을 갈 수있다.")
    void findMovablePaths_ReturnAllLinearCandidates() {
        MoveStrategy strategy = new LinearStrategy(Palaces.of());
        Paths paths = strategy.findMovablePaths(Position.of(4, 4));

        for (int c = 0; c <= 8; c++) {
            if (c == 4) {
                continue;
            }
            assertThat(paths.findPathByDestination(Position.of(4, c)).isDestination(Position.of(4, c))).isTrue();
        }
        for (int r = 0; r <= 9; r++) {
            if (r == 4) {
                continue;
            }
            assertThat(paths.findPathByDestination(Position.of(r, 4)).isDestination(Position.of(r, 4))).isTrue();
        }

        assertThatThrownBy(() -> paths.findPathByDestination(Position.of(4, 4)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동할 수 없는 좌표입니다.");
    }

    @Test
    void 궁성_중앙이라면_4방향의_대각선으로_이동이_가능하다() {
        MoveStrategy strategy = new LinearStrategy(Palaces.of());
        Paths paths = strategy.findMovablePaths(Position.of(1, 4));

        assertThat(paths.findPathByDestination(Position.of(2, 3)).isDestination(Position.of(2, 3))).isTrue();
        assertThat(paths.findPathByDestination(Position.of(2, 5)).isDestination(Position.of(2, 5))).isTrue();
        assertThat(paths.findPathByDestination(Position.of(0, 3)).isDestination(Position.of(0, 3))).isTrue();
        assertThat(paths.findPathByDestination(Position.of(0, 5)).isDestination(Position.of(0, 5))).isTrue();
    }

    @Test
    void 궁성_꼭짓점이라면_1방향의_대각선으로_이동이_가능하다() {
        MoveStrategy strategy = new LinearStrategy(Palaces.of());
        Paths paths = strategy.findMovablePaths(Position.of(2, 3));

        assertThat(paths.findPathByDestination(Position.of(1, 4)).isDestination(Position.of(1, 4))).isTrue();
    }
}