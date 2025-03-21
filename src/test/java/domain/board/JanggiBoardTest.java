package domain.board;

import domain.JanggiCoordinate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class JanggiBoardTest {

    @Nested
    class BoardCoordinateTest {

        @DisplayName("보드에서 유효하지 않은 좌표의 경우를 검사한다")
        @Test
        void outOfCoordinateTest() {
            JanggiBoard board = new JanggiBoard(new HashMap<>());
            JanggiCoordinate outOfCoordinate = new JanggiCoordinate(0, 0);
            JanggiCoordinate inCoordinate = new JanggiCoordinate(5, 5);

            assertAll(
                    () -> assertThat(board.isOutOfBoundary(outOfCoordinate)).isTrue(),
                    () -> assertThat(board.isOutOfBoundary(inCoordinate)).isFalse()
            );
        }
    }
}