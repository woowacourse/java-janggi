package domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class JanggiCoordinateTest {

    JanggiCoordinate coordinate;

    @BeforeEach
    void initCoordinate() {
        coordinate = new JanggiCoordinate(5, 5);
    }

    @Nested
    class CoordinateCreateTest {

        @DisplayName("유효하지 않은 row의 경우 생성되지 않고 에러를 반환한다")
        @ParameterizedTest
        @ValueSource(ints = {-2, -1, 11, 12})
        void coordinateCreateTest1(int row) {
            assertThatThrownBy(() -> new JanggiCoordinate(row, 5))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("유효하지 않은 col의 경우 생성되지 않고 에러를 반환한다")
        @ParameterizedTest
        @ValueSource(ints = {-2, -1, 10, 11})
        void coordinateCreateTest2(int col) {
            assertThatThrownBy(() -> new JanggiCoordinate(5, col))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    class CoordinateMoveLinearTest {

        @DisplayName("좌표를 오른쪽으로 한칸 이동한다")
        @Test
        void moveCoordinateToRight() {
            coordinate = coordinate.moveRight();

            int row = coordinate.row();
            int col = coordinate.col();

            assertAll(
                    () -> assertThat(row).isEqualTo(5),
                    () -> assertThat(col).isEqualTo(6)
            );
        }

        @DisplayName("좌표를 왼쪽으로 한칸 이동한다")
        @Test
        void moveCoordinateToLeft() {
            coordinate = coordinate.moveLeft();

            int row = coordinate.row();
            int col = coordinate.col();

            assertAll(
                    () -> assertThat(row).isEqualTo(5),
                    () -> assertThat(col).isEqualTo(4)
            );
        }

        @DisplayName("좌표를 위쪽으로 한칸 이동한다")
        @Test
        void moveCoordinateToUp() {
            coordinate = coordinate.moveUp();

            int row = coordinate.row();
            int col = coordinate.col();

            assertAll(
                    () -> assertThat(row).isEqualTo(4),
                    () -> assertThat(col).isEqualTo(5)
            );
        }

        @DisplayName("좌표를 아래쪽으로 한칸 이동한다")
        @Test
        void moveCoordinateToDown() {
            coordinate = coordinate.moveDown();

            int row = coordinate.row();
            int col = coordinate.col();

            assertAll(
                    () -> assertThat(row).isEqualTo(6),
                    () -> assertThat(col).isEqualTo(5)
            );
        }
    }

    @Nested
    class CoordinateMoveNoneLinearTest {

        @DisplayName("좌표를 오른쪽 위 대각선으로 이동시킨다.")
        @Test
        void moveCoordinateUpRight() {
            coordinate = coordinate.moveRightUp();

            int row = coordinate.row();
            int col = coordinate.col();

            assertAll(
                    () -> assertThat(row).isEqualTo(4),
                    () -> assertThat(col).isEqualTo(6)
            );
        }

        @DisplayName("좌표를 왼쪽 위 대각선으로 이동시킨다.")
        @Test
        void moveCoordinateUpLeft() {
            coordinate = coordinate.moveLeftUp();

            int row = coordinate.row();
            int col = coordinate.col();

            assertAll(
                    () -> assertThat(row).isEqualTo(4),
                    () -> assertThat(col).isEqualTo(4)
            );
        }

        @DisplayName("좌표를 오른쪽 아래 대각선으로 이동시킨다.")
        @Test
        void moveCoordinateDownRight() {
            coordinate = coordinate.moveRightDown();

            int row = coordinate.row();
            int col = coordinate.col();

            assertAll(
                    () -> assertThat(row).isEqualTo(6),
                    () -> assertThat(col).isEqualTo(6)
            );
        }

        @DisplayName("좌표를 왼쪽 아래 대각선으로 이동시킨다.")
        @Test
        void moveCoordinateUpDiagonal() {
            coordinate = coordinate.moveLeftDown();

            int row = coordinate.row();
            int col = coordinate.col();

            assertAll(
                    () -> assertThat(row).isEqualTo(6),
                    () -> assertThat(col).isEqualTo(4)
            );
        }
    }
}
