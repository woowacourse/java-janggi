package janggi.domain.piece.strategy;

import janggi.domain.Camp;
import janggi.domain.Path;
import janggi.domain.Position;

import org.junit.jupiter.api.Nested;
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
    }
}