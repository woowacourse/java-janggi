package domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


class JanggiCoordinateTest {

    @Nested
    class CoordinateMoveTest {
        
        @DisplayName("좌표를 원하는 만큼 움직인다")
        @Test
        void coordinateMoveForward() {
            int row = 5;
            int col = 5;
            Coordinate currPosition = new JanggiCoordinate(row, col);

            Coordinate next = currPosition.move(1, 1);

            Assertions.assertThat(next).isEqualTo(new JanggiCoordinate(row + 1, col + 1));
        }
    }
}