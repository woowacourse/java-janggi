package janggi.domain.piece.strategy;

import janggi.domain.Path;
import janggi.domain.JanggiPosition;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class HorseStrategyTest {

    @Test
    void 마는_8방향으로_이동할_수_있다() {
        MoveStrategy strategy = new HorseStrategy();
        List<Path> paths = strategy.findMovablePaths(JanggiPosition.of(4, 4));
        assertThat(paths).hasSize(8);
    }

    @Test
    void 마는_직선_한칸_후_대각선_한칸으로_이동한다() {
        MoveStrategy strategy = new HorseStrategy();
        List<Path> paths = strategy.findMovablePaths(JanggiPosition.of(4, 4));

        assertThat(paths.get(0).destination()).isEqualTo(JanggiPosition.of(6, 5));
        assertThat(paths.get(1).destination()).isEqualTo(JanggiPosition.of(5, 6));
        assertThat(paths.get(2).destination()).isEqualTo(JanggiPosition.of(3, 6));
        assertThat(paths.get(3).destination()).isEqualTo(JanggiPosition.of(2, 5));
        assertThat(paths.get(4).destination()).isEqualTo(JanggiPosition.of(6, 3));
        assertThat(paths.get(5).destination()).isEqualTo(JanggiPosition.of(5, 2));
        assertThat(paths.get(6).destination()).isEqualTo(JanggiPosition.of(3, 2));
        assertThat(paths.get(7).destination()).isEqualTo(JanggiPosition.of(2, 3));
    }
}
