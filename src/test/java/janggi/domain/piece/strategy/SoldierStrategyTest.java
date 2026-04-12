package janggi.domain.piece.strategy;

import janggi.domain.Path;
import janggi.domain.Paths;
import janggi.domain.position.Position;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SoldierStrategyTest {

    @Nested
    class 초나라_병_이동_테스트 {
        @Test
        void 초나라_병은_앞과_양_옆으로_움직인다() {
            MoveStrategy strategy = ChoSoldierStrategy.getInstance();
            Paths paths = strategy.findMovablePaths(Position.of(4, 4));
            assertThat(paths.findPathByDestination(Position.of(5, 4))).isEqualTo(Path.of(Position.of(5, 4)));
            assertThat(paths.findPathByDestination(Position.of(4, 5))).isEqualTo(Path.of(Position.of(4, 5)));
            assertThat(paths.findPathByDestination(Position.of(4, 3))).isEqualTo(Path.of(Position.of(4, 3)));
        }

        @Test
        void 초나라_병이_적진_궁성_중앙에_있을_때_전진_방향_대각선_두_곳을_경로에_포함한다() {
            MoveStrategy strategy = ChoSoldierStrategy.getInstance();
            Position center = Position.of(8, 4);

            Paths paths = strategy.findMovablePaths(center);

            assertThat(paths.findPathByDestination(Position.of(9, 5))).isEqualTo(Path.of(Position.of(9, 5)));
            assertThat(paths.findPathByDestination(Position.of(9, 4))).isEqualTo(Path.of(Position.of(9, 4)));
            assertThat(paths.findPathByDestination(Position.of(9, 3))).isEqualTo(Path.of(Position.of(9, 3)));
            assertThat(paths.findPathByDestination(Position.of(8, 5))).isEqualTo(Path.of(Position.of(8, 5)));
            assertThat(paths.findPathByDestination(Position.of(8, 3))).isEqualTo(Path.of(Position.of(8, 3)));
        }

        @Test
        void 초나라_병이_적진_궁성_상단_모서리에_있을_때_중앙으로_향하는_대각선을_포함한다() {
            MoveStrategy strategy = ChoSoldierStrategy.getInstance();
            Position center = Position.of(7, 3);

            Paths paths = strategy.findMovablePaths(center);

            assertThat(paths.findPathByDestination(Position.of(8, 4))).isEqualTo(Path.of(Position.of(8, 4)));
            assertThat(paths.findPathByDestination(Position.of(8, 3))).isEqualTo(Path.of(Position.of(8, 3)));
            assertThat(paths.findPathByDestination(Position.of(7, 2))).isEqualTo(Path.of(Position.of(7, 2)));
            assertThat(paths.findPathByDestination(Position.of(7, 4))).isEqualTo(Path.of(Position.of(7, 4)));
        }
    }

    @Nested
    class 한나라_병_이동_테스트 {
        @Test
        void 한나라_병은_앞과_양_옆으로_움직인다() {
            MoveStrategy strategy = HanSoldierStrategy.getInstance();
            Paths paths = strategy.findMovablePaths(Position.of(4, 4));
            assertThat(paths.findPathByDestination(Position.of(3, 4))).isEqualTo(Path.of(Position.of(3, 4)));
            assertThat(paths.findPathByDestination(Position.of(4, 5))).isEqualTo(Path.of(Position.of(4, 5)));
            assertThat(paths.findPathByDestination(Position.of(4, 3))).isEqualTo(Path.of(Position.of(4, 3)));
        }

        @Test
        void 한나라_병이_적진_궁성_중앙에_있을_때_전진_방향_대각선_두_곳을_경로에_포함한다() {
            MoveStrategy strategy = HanSoldierStrategy.getInstance();
            Position center = Position.of(1, 4);

            Paths paths = strategy.findMovablePaths(center);

            assertThat(paths.findPathByDestination(Position.of(0, 5))).isEqualTo(Path.of(Position.of(0, 5)));
            assertThat(paths.findPathByDestination(Position.of(0, 4))).isEqualTo(Path.of(Position.of(0, 4)));
            assertThat(paths.findPathByDestination(Position.of(0, 3))).isEqualTo(Path.of(Position.of(0, 3)));
            assertThat(paths.findPathByDestination(Position.of(1, 5))).isEqualTo(Path.of(Position.of(1, 5)));
            assertThat(paths.findPathByDestination(Position.of(1, 3))).isEqualTo(Path.of(Position.of(1, 3)));
        }

        @Test
        void 한나라_병이_적진_궁성_상단_모서리에_있을_때_중앙으로_향하는_대각선을_포함한다() {
            MoveStrategy strategy = HanSoldierStrategy.getInstance();
            Position center = Position.of(2, 3);

            Paths paths = strategy.findMovablePaths(center);

            assertThat(paths.findPathByDestination(Position.of(1, 4))).isEqualTo(Path.of(Position.of(1, 4)));
            assertThat(paths.findPathByDestination(Position.of(1, 3))).isEqualTo(Path.of(Position.of(1, 3)));
            assertThat(paths.findPathByDestination(Position.of(2, 2))).isEqualTo(Path.of(Position.of(2, 2)));
            assertThat(paths.findPathByDestination(Position.of(2, 4))).isEqualTo(Path.of(Position.of(2, 4)));
        }
    }
}
