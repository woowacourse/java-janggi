package domain;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.JanggiBoard;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class JanggiBoardTest {

    @Nested
    class JanggiBoardValidation {

        @DisplayName("장기판 밖으로 나갈 수 없다.")
        @Test
        void validateBoardBoundary1() {
            int row = 11;
            int col = 10;

            JanggiBoard janggiBoard = new JanggiBoard();
            boolean isOutOfBoundary = janggiBoard.isOutOfBoundary(new JanggiCoordinate(row, col));

            assertThat(isOutOfBoundary).isTrue();
        }


    }

}