package domain;

import static org.assertj.core.api.Assertions.assertThat;

import domain.piece.movement.Movement;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


class CoordinateTest {

    @Nested
    class CoordinateMoveTest {

        @DisplayName("장기판 밖으로 나갈 수 없다.")
        @Test
        void validateBoardBoundary1() {
            boolean isOutOfBoundary = new Coordinate(11, 10).isOutOfBoundary();

            assertThat(isOutOfBoundary).isTrue();
        }

        @DisplayName("좌표를 원하는 만큼 움직인다")
        @Test
        void moveTest() {
            int row = 5;
            int col = 5;
            Coordinate currPosition = new Coordinate(row, col);

            Coordinate next = currPosition.move(1, 1);

            Assertions.assertThat(next).isEqualTo(new Coordinate(row + 1, col + 1));
        }

        @DisplayName("좌표를 움직인다")
        @Test
        void moveTest2() {
            Coordinate currPosition = new Coordinate(5, 5);

            Coordinate next = currPosition.move(Movement.UP_RIGHT);

            Assertions.assertThat(next).isEqualTo(new Coordinate(4, 6));
        }
    }
}
