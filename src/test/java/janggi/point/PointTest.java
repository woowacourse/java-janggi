package janggi.point;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PointTest {

    @Nested
    @DisplayName("생성 테스트")
    class CreatePointTest {

        @DisplayName("생성 시 보드의 가로 범위를 벗어나면 예외가 발생한다.")
        @ParameterizedTest
        @ValueSource(ints = {10, -1})
        void failIfOutOfRowRange(int outOfRangeRow) {

            assertThatThrownBy(() -> new Point(outOfRangeRow, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("보드판의 범위를 벗어난 좌표입니다.");
        }

        @DisplayName("생성 시 보드의 가로 범위를 벗어나면 예외가 발생한다.")
        @ParameterizedTest
        @ValueSource(ints = {9, -1})
        void failIfOutOfColumnRange(int outOfRangeColumn) {

            assertThatThrownBy(() -> new Point(0, outOfRangeColumn))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("보드판의 범위를 벗어난 좌표입니다.");
        }
    }

    @Nested
    @DisplayName("이동 테스트")
    class MovePointTest {

        @Test
        @DisplayName("특정 지점에서 row, column 이동 거리를 입력하면 이동시킨 지점을 얻을 수 있다.")
        void movePoint() {
            Point beforePoint = new Point(1, 0);

            Point afterPoint = beforePoint.move(0, 1);

            assertThat(afterPoint).isEqualTo(new Point(1, 1));
        }
    }

    @Nested
    @DisplayName("비교 테스트")
    class PointCompareTest {

        @Test
        @DisplayName("동일한 행이면 true를 반환한다.")
        void checkSameRow() {
            Point startPoint = new Point(1, 0);
            Point targetPoint = new Point(1, 1);

            assertThat(startPoint.isSameRow(targetPoint)).isTrue();
        }

        @Test
        @DisplayName("다른 행이면 false를 반환한다.")
        void checkNotSameRow() {
            Point startPoint = new Point(0, 0);
            Point targetPoint = new Point(1, 1);

            assertThat(startPoint.isSameRow(targetPoint)).isFalse();
        }

        @Test
        @DisplayName("동일한 열이면 true를 반환한다.")
        void checkSameColumn() {
            Point startPoint = new Point(0, 1);
            Point targetPoint = new Point(1, 1);

            assertThat(startPoint.isSameColumn(targetPoint)).isTrue();
        }

        @Test
        @DisplayName("다른 열이면 false를 반환한다.")
        void checkNotSameColumn() {
            Point startPoint = new Point(0, 0);
            Point targetPoint = new Point(1, 1);

            assertThat(startPoint.isSameColumn(targetPoint)).isFalse();
        }

        @Test
        @DisplayName("시작 지점이 타겟 지점의 열보다 더 크다면 true를 반환한다.")
        void checkColumnBiggerThan() {
            Point startPoint = new Point(0, 3);
            Point targetPoint = new Point(1, 1);

            assertThat(startPoint.isColumnBiggerThan(targetPoint)).isTrue();
        }

        @Test
        @DisplayName("시작 지점이 타겟 지점의 열보다 더 작다면 true를 반환한다.")
        void checkColumnLessThan() {
            Point startPoint = new Point(0, 0);
            Point targetPoint = new Point(1, 3);

            assertThat(startPoint.isColumnLessThan(targetPoint)).isTrue();
        }

        @Test
        @DisplayName("시작 지점이 타겟 지점의 행보다 더 크다면 true를 반환한다.")
        void checkRowBiggerThan() {
            Point startPoint = new Point(3, 1);
            Point targetPoint = new Point(1, 1);

            assertThat(startPoint.isRowBiggerThan(targetPoint)).isTrue();
        }

        @Test
        @DisplayName("시작 지점이 타겟 지점의 행보다 더 작다면 true를 반환한다.")
        void checkRowLessThan() {
            Point startPoint = new Point(0, 1);
            Point targetPoint = new Point(3, 1);

            assertThat(startPoint.isRowLessThan(targetPoint)).isTrue();
        }
    }
}
