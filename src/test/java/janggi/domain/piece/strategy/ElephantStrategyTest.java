package janggi.domain.piece.strategy;

import janggi.domain.Paths;
import janggi.domain.position.Position;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ElephantStrategyTest {

    @Test
    void 상은_직선_한칸_후_대각선_두칸으로_이동한다() {
        MoveStrategy strategy = ElephantStrategy.getInstance();
        Paths paths = strategy.findMovablePaths(Position.of(4, 4));

        assertThat(paths.findPathByDestination(Position.of(7, 6)).isDestination(Position.of(7, 6))).isTrue();
        assertThat(paths.findPathByDestination(Position.of(6, 7)).isDestination(Position.of(6, 7))).isTrue();
        assertThat(paths.findPathByDestination(Position.of(2, 7)).isDestination(Position.of(2, 7))).isTrue();
        assertThat(paths.findPathByDestination(Position.of(1, 6)).isDestination(Position.of(1, 6))).isTrue();
        assertThat(paths.findPathByDestination(Position.of(7, 2)).isDestination(Position.of(7, 2))).isTrue();
        assertThat(paths.findPathByDestination(Position.of(6, 1)).isDestination(Position.of(6, 1))).isTrue();
        assertThat(paths.findPathByDestination(Position.of(2, 1)).isDestination(Position.of(2, 1))).isTrue();
        assertThat(paths.findPathByDestination(Position.of(1, 2)).isDestination(Position.of(1, 2))).isTrue();
    }
}
