package janggi.domain.piece.strategy;

import janggi.domain.Path;
import janggi.domain.Position;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ElephantStrategyTest {

    @Test
    void 상은_8방향으로_이동할_수_있다() {
        MoveStrategy strategy = new ElephantStrategy();
        List<Path> paths = strategy.findMovablePaths(Position.of(4, 4));
        assertThat(paths).hasSize(8);
    }

    @Test
    void 상은_직선_한칸_후_대각선_두칸으로_이동한다() {
        MoveStrategy strategy = new ElephantStrategy();
        List<Path> paths = strategy.findMovablePaths(Position.of(4, 4));

        assertThat(paths.get(0).destination()).isEqualTo(Position.of(7, 6));
        assertThat(paths.get(1).destination()).isEqualTo(Position.of(6, 7));
        assertThat(paths.get(2).destination()).isEqualTo(Position.of(2, 7));
        assertThat(paths.get(3).destination()).isEqualTo(Position.of(1, 6));
        assertThat(paths.get(4).destination()).isEqualTo(Position.of(7, 2));
        assertThat(paths.get(5).destination()).isEqualTo(Position.of(6, 1));
        assertThat(paths.get(6).destination()).isEqualTo(Position.of(2, 1));
        assertThat(paths.get(7).destination()).isEqualTo(Position.of(1, 2));
    }

}
