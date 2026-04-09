package janggi.domain.piece.strategy;

import janggi.domain.Path;
import janggi.domain.Paths;
import janggi.domain.position.Position;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PalaceStrategyTest {

    @Nested
    class 초나라_궁_내부_기물_이동_테스트 {

        @Test
        void 초나라_궁성_내부_기물은_궁성_내부에서_앞뒤_양옆_대각선으로_움직인다() {
            MoveStrategy strategy = ChoPalaceStrategy.getInstance();
            Paths paths = strategy.findMovablePaths(Position.of(1, 4));
            assertThat(paths.findPathByDestination(Position.of(0, 4))).isEqualTo(Path.of(Position.of(0, 4)));
            assertThat(paths.findPathByDestination(Position.of(2, 4))).isEqualTo(Path.of(Position.of(2, 4)));
            assertThat(paths.findPathByDestination(Position.of(1, 5))).isEqualTo(Path.of(Position.of(1, 5)));
            assertThat(paths.findPathByDestination(Position.of(1, 3))).isEqualTo(Path.of(Position.of(1, 3)));
            assertThat(paths.findPathByDestination(Position.of(2, 3))).isEqualTo(Path.of(Position.of(2, 3)));
            assertThat(paths.findPathByDestination(Position.of(2, 5))).isEqualTo(Path.of(Position.of(2, 5)));
            assertThat(paths.findPathByDestination(Position.of(0, 3))).isEqualTo(Path.of(Position.of(0, 3)));
            assertThat(paths.findPathByDestination(Position.of(0, 5))).isEqualTo(Path.of(Position.of(0, 5)));
        }

        @Test
        void 초나라_궁성_기물은_궁성_외부에서_움직일_수_없다() {
            MoveStrategy strategy = ChoPalaceStrategy.getInstance();
            Paths paths = strategy.findMovablePaths(Position.of(4, 4));
            assertThatThrownBy(() -> paths.findPathByDestination(Position.of(5, 4)))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("이동할 수 없는 좌표입니다.");
        }
    }

    @Nested
    class 한나라_궁_내부_기물_이동_테스트 {

        @Test
        void 한나라_궁성_내부_기물은_궁성_내부에서_앞뒤_양옆_대각선으로_움직인다() {
            MoveStrategy strategy = HanPalaceStrategy.getInstance();
            Paths paths = strategy.findMovablePaths(Position.of(8, 4));
            assertThat(paths.findPathByDestination(Position.of(9, 4))).isEqualTo(Path.of(Position.of(9, 4)));
            assertThat(paths.findPathByDestination(Position.of(7, 4))).isEqualTo(Path.of(Position.of(7, 4)));
            assertThat(paths.findPathByDestination(Position.of(8, 5))).isEqualTo(Path.of(Position.of(8, 5)));
            assertThat(paths.findPathByDestination(Position.of(8, 3))).isEqualTo(Path.of(Position.of(8, 3)));
            assertThat(paths.findPathByDestination(Position.of(7, 3))).isEqualTo(Path.of(Position.of(7, 3)));
            assertThat(paths.findPathByDestination(Position.of(7, 5))).isEqualTo(Path.of(Position.of(7, 5)));
            assertThat(paths.findPathByDestination(Position.of(9, 3))).isEqualTo(Path.of(Position.of(9, 3)));
            assertThat(paths.findPathByDestination(Position.of(9, 5))).isEqualTo(Path.of(Position.of(9, 5)));
        }

        @Test
        void 한나라_궁성_기물은_궁성_외부에서_움직일_수_없다() {
            MoveStrategy strategy = HanPalaceStrategy.getInstance();
            Paths paths = strategy.findMovablePaths(Position.of(4, 4));
            assertThatThrownBy(() -> paths.findPathByDestination(Position.of(5, 4)))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("이동할 수 없는 좌표입니다.");
        }
    }
}
