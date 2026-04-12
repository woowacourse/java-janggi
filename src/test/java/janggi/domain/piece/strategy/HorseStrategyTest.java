package janggi.domain.piece.strategy;

import janggi.domain.Paths;
import janggi.domain.position.Position;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HorseStrategyTest {

    @Test
    void 마는_직선_한칸_후_대각선_한칸으로_이동한다() {
        MoveStrategy strategy = HorseStrategy.getInstance();
        Paths paths = strategy.findMovablePaths(Position.of(4, 4));

        assertThat(paths.findPathByDestination(Position.of(6, 5)).isDestination(Position.of(6, 5))).isTrue();
        assertThat(paths.findPathByDestination(Position.of(5, 6)).isDestination(Position.of(5, 6))).isTrue();
        assertThat(paths.findPathByDestination(Position.of(3, 6)).isDestination(Position.of(3, 6))).isTrue();
        assertThat(paths.findPathByDestination(Position.of(2, 5)).isDestination(Position.of(2, 5))).isTrue();
        assertThat(paths.findPathByDestination(Position.of(6, 3)).isDestination(Position.of(6, 3))).isTrue();
        assertThat(paths.findPathByDestination(Position.of(5, 2)).isDestination(Position.of(5, 2))).isTrue();
        assertThat(paths.findPathByDestination(Position.of(3, 2)).isDestination(Position.of(3, 2))).isTrue();
        assertThat(paths.findPathByDestination(Position.of(2, 3)).isDestination(Position.of(2, 3))).isTrue();
    }
}
