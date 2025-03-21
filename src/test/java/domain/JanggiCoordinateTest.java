package domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class JanggiCoordinateTest {

    @Nested
    class CoordinateMoveLinearTest {
        JanggiCoordinate coordinate;

        @BeforeEach
        void initCoordinate() {
            coordinate = new JanggiCoordinate(5, 5);
        }

        @DisplayName("좌표를 오른쪽으로 한칸 이동한다")
        @Test
        void moveCoordinateToRight() {
            coordinate = coordinate.moveRight();

            int row = coordinate.getRow();
            int col = coordinate.getCol();

            assertAll(
                    () -> assertThat(row).isEqualTo(6),
                    () -> assertThat(col).isEqualTo(5)
            );
        }

        @DisplayName("좌표를 왼쪽으로 한칸 이동한다")
        @Test
        void moveCoordinateToLeft() {
            coordinate = coordinate.moveLeft();

            int row = coordinate.getRow();
            int col = coordinate.getCol();

            assertAll(
                    () -> assertThat(row).isEqualTo(4),
                    () -> assertThat(col).isEqualTo(5)
            );
        }

        @DisplayName("좌표를 위쪽으로 한칸 이동한다")
        @Test
        void moveCoordinateToUp() {
            coordinate = coordinate.moveUp();

            int row = coordinate.getRow();
            int col = coordinate.getCol();

            assertAll(
                    () -> assertThat(row).isEqualTo(5),
                    () -> assertThat(col).isEqualTo(4)
            );
        }

        @DisplayName("좌표를 아래쪽으로 한칸 이동한다")
        @Test
        void moveCoordinateToDown() {
            coordinate = coordinate.moveDown();

            int row = coordinate.getRow();
            int col = coordinate.getCol();

            assertAll(
                    () -> assertThat(row).isEqualTo(5),
                    () -> assertThat(col).isEqualTo(6)
            );
        }
    }
}