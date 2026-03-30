package janggi.domain.piece.strategy;

import janggi.domain.Camp;
import janggi.domain.Path;
import janggi.domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MoveStrategyTest {
    private static final int MIN_INDEX = 0;
    private static final int MAX_ROW_INDEX = 9;
    private static final int MAX_COL_INDEX = 8;

    @Nested
    class 병_이동_테스트 {
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

    @Nested
    class 사_이동_테스트 {

        @DisplayName("사는 현재 위치에서 앞, 뒤, 양 옆을 1칸씩의 좌표를 도착지점 후보로 반환한다")
        @Test
        void 사는_앞뒤_양옆으로_움직인다() {
            MoveStrategy strategy = new AdvisorStrategy();
            List<Path> paths = strategy.findMovablePaths(Position.of(4, 4));
            assertThat(paths.get(0).destination()).isEqualTo(Position.of(5, 4));
            assertThat(paths.get(1).destination()).isEqualTo(Position.of(3, 4));
            assertThat(paths.get(2).destination()).isEqualTo(Position.of(4, 5));
            assertThat(paths.get(3).destination()).isEqualTo(Position.of(4, 3));
        }
    }

    @Nested
    class 장_이동_테스트 {

        @DisplayName("장은는 현재 위치에서 앞, 뒤, 양 옆을 1칸씩의 좌표를 도착지점 후보로 반환한다")
        @Test
        void 장는_앞뒤_양옆으로_움직인다() {
            MoveStrategy strategy = new GeneralStrategy();
            List<Path> paths = strategy.findMovablePaths(Position.of(4, 4));
            assertThat(paths.get(0).destination()).isEqualTo(Position.of(5, 4));
            assertThat(paths.get(1).destination()).isEqualTo(Position.of(3, 4));
            assertThat(paths.get(2).destination()).isEqualTo(Position.of(4, 5));
            assertThat(paths.get(3).destination()).isEqualTo(Position.of(4, 3));
        }
    }

    @Nested
    class 마_이동_테스트 {

        @Test
        void 마는_8방향으로_이동할_수_있다() {
            MoveStrategy strategy = new HorseStrategy();
            List<Path> paths = strategy.findMovablePaths(Position.of(4, 4));
            assertThat(paths).hasSize(8);
        }

        @Test
        void 마는_직선_한칸_후_대각선_한칸으로_이동한다() {
            MoveStrategy strategy = new HorseStrategy();
            List<Path> paths = strategy.findMovablePaths(Position.of(4, 4));

            assertThat(paths.get(0).destination()).isEqualTo(Position.of(6, 5));
            assertThat(paths.get(1).destination()).isEqualTo(Position.of(5, 6));
            assertThat(paths.get(2).destination()).isEqualTo(Position.of(3, 6));
            assertThat(paths.get(3).destination()).isEqualTo(Position.of(2, 5));
            assertThat(paths.get(4).destination()).isEqualTo(Position.of(6, 3));
            assertThat(paths.get(5).destination()).isEqualTo(Position.of(5, 2));
            assertThat(paths.get(6).destination()).isEqualTo(Position.of(3, 2));
            assertThat(paths.get(7).destination()).isEqualTo(Position.of(2, 3));
        }
    }

    @Nested
    class 상_이동_테스트 {

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

    @Nested
    class 포_이동_테스트 {
        @Test
        @DisplayName("포는 현재 위치에서 가로와 세로 직선상의 모든 좌표를 후보로 반환한다")
        void findMovablePaths_ReturnAllLinearCandidates() {
            assertLinearStrategy(new CannonStrategy());
        }
    }

    @Nested
    class 차_이동_테스트 {
        @Test
        @DisplayName("차는 현재 위치에서 가로와 세로 직선상의 모든 좌표를 후보로 반환한다")
        void findMovablePaths_ReturnAllLinearCandidates() {
            assertLinearStrategy(new ChariotStrategy());
        }
    }

    private void assertLinearStrategy(MoveStrategy strategy) {
        int row = 4;
        int column = 4;
        List<Path> paths = strategy.findMovablePaths(Position.of(row, column));
        List<Position> destinations = paths.stream()
                .map(Path::destination)
                .toList();

        verifyHorizontalPaths(destinations, row, column);
        verifyVerticalPaths(destinations, row, column);
    }

    private void verifyHorizontalPaths(List<Position> destinations, int row, int column) {
        for (int i = MIN_INDEX; i <= MAX_COL_INDEX; i++) {
            checkHorizontalPresence(destinations, row, column, i);
        }
    }

    private void verifyVerticalPaths(List<Position> destinations, int row, int column) {
        for (int i = MIN_INDEX; i <= MAX_ROW_INDEX; i++) {
            checkVerticalPresence(destinations, row, column, i);
        }
    }

    private void checkHorizontalPresence(List<Position> destinations, int row, int column, int colIndex) {
        Position target = Position.of(row, colIndex);
        if (colIndex == column) {
            assertThat(destinations).doesNotContain(target);
            return;
        }
        assertThat(destinations).contains(target);
    }

    private void checkVerticalPresence(List<Position> destinations, int row, int column, int rowIndex) {
        Position target = Position.of(rowIndex, column);
        if (rowIndex == row) {
            assertThat(destinations).doesNotContain(target);
            return;
        }
        assertThat(destinations).contains(target);
    }
}
