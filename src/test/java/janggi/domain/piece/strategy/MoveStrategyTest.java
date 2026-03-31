package janggi.domain.piece.strategy;

import janggi.domain.Path;
import janggi.domain.Paths;
import janggi.domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class MoveStrategyTest {
    private static final int MIN_INDEX = 0;
    private static final int MAX_ROW_INDEX = 9;
    private static final int MAX_COL_INDEX = 8;

    @Nested
    class 병_이동_테스트 {
        @Test
        void 초나라_병은_앞과_양_옆으로_움직인다() {
            MoveStrategy strategy = new ChoSoldierStrategy();
            Paths paths = strategy.findMovablePaths(Position.of(4, 4));
            assertThat(paths.findPathByDestination(Position.of(5, 4))).isEqualTo(Path.of(Position.of(5, 4)));
            assertThat(paths.findPathByDestination(Position.of(4, 5))).isEqualTo(Path.of(Position.of(4, 5)));
            assertThat(paths.findPathByDestination(Position.of(4, 3))).isEqualTo(Path.of(Position.of(4, 3)));
        }

        @Test
        void 한나라_병은_앞과_양_옆으로_움직인다() {
            MoveStrategy strategy = new HanSoldierStrategy();
            Paths paths = strategy.findMovablePaths(Position.of(4, 4));
            assertThat(paths.findPathByDestination(Position.of(3, 4))).isEqualTo(Path.of(Position.of(3, 4)));
            assertThat(paths.findPathByDestination(Position.of(4, 5))).isEqualTo(Path.of(Position.of(4, 5)));
            assertThat(paths.findPathByDestination(Position.of(4, 3))).isEqualTo(Path.of(Position.of(4, 3)));
        }
    }

    @Nested
    class 궁_내부_이동_테스트 {

        @DisplayName("사는 현재 위치에서 앞, 뒤, 양 옆을 1칸씩의 좌표를 도착지점 후보로 반환한다")
        @Test
        void 사는_앞뒤_양옆으로_움직인다() {
            MoveStrategy strategy = new PalaceStrategy();
            Paths paths = strategy.findMovablePaths(Position.of(4, 4));
            assertThat(paths.findPathByDestination(Position.of(5, 4))).isEqualTo(Path.of(Position.of(5, 4)));
            assertThat(paths.findPathByDestination(Position.of(3, 4))).isEqualTo(Path.of(Position.of(3, 4)));
            assertThat(paths.findPathByDestination(Position.of(4, 5))).isEqualTo(Path.of(Position.of(4, 5)));
            assertThat(paths.findPathByDestination(Position.of(4, 3))).isEqualTo(Path.of(Position.of(4, 3)));
        }
    }

    @Nested
    class 마_이동_테스트 {

        @Test
        void 마는_직선_한칸_후_대각선_한칸으로_이동한다() {
            MoveStrategy strategy = new HorseStrategy();
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

    @Nested
    class 상_이동_테스트 {

        @Test
        void 상은_직선_한칸_후_대각선_두칸으로_이동한다() {
            MoveStrategy strategy = new ElephantStrategy();
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

    @Nested
    class 직선_이동_테스트 {
        @Test
        @DisplayName("포는 현재 위치에서 가로와 세로 직선상의 모든 좌표를 후보로 반환한다")
        void findMovablePaths_ReturnAllLinearCandidates() {
            MoveStrategy strategy = new LinearStrategy();
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
    }
}
