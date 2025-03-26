package domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class JanggiCoordinateTest {

    JanggiCoordinate coordinate;

    @BeforeEach
    void initCoordinate() {
        coordinate = new JanggiCoordinate(5, 5);
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

    @Nested
    class CoordinateCalcTest {

        @DisplayName("두 좌표간 거리를 계산한다")
        @ParameterizedTest
        @MethodSource("coordinates")
        void calcCoordinateDistance(JanggiCoordinate from, JanggiCoordinate to, int distSqaure) {
            int fromDst = from.distanceTo(to);
            int toDst = to.distanceTo(from);

            assertAll(
                    () -> assertThat(fromDst).isEqualTo(toDst),
                    () -> assertThat(fromDst).isEqualTo(distSqaure)
            );
        }

        static Stream<Arguments> coordinates() {
            return Stream.of(
                    Arguments.arguments(new JanggiCoordinate(0, 0), new JanggiCoordinate(3, 4), 25),
                    Arguments.arguments(new JanggiCoordinate(1, 1), new JanggiCoordinate(4, 5), 25),
                    Arguments.arguments(new JanggiCoordinate(2, 3), new JanggiCoordinate(2, 3), 0),
                    Arguments.arguments(new JanggiCoordinate(2, 4), new JanggiCoordinate(5, 6), 13),
                    Arguments.arguments(new JanggiCoordinate(5, 6), new JanggiCoordinate(6, 6), 1),
                    Arguments.arguments(new JanggiCoordinate(5, 4), new JanggiCoordinate(6, 5), 2)
            );
        }
    }
}
