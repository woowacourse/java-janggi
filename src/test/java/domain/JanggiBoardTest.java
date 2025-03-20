package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import domain.board.JanggiBoard;
import domain.piece.Ma;
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

    @Nested
    class BoardMoveTest {

        @DisplayName("말을 움직인다.")
        @Test
        void movePieceTest() {
            JanggiCoordinate oldCoordinate = new JanggiCoordinate(2, 1);
            JanggiCoordinate newCoordinate = new JanggiCoordinate(3, 3);

            JanggiBoard janggiBoard = new JanggiBoard();

            janggiBoard.movePiece(oldCoordinate, newCoordinate);

            assertThatThrownBy(() -> janggiBoard.getPieceType(oldCoordinate))
                    .isInstanceOf(IllegalArgumentException.class);
            assertThat(janggiBoard.getPieceType(newCoordinate)).isEqualTo("마");
        }

        @DisplayName("말이 이동 불가능한 위치로 이동할 수 없다.")
        @Test
        void validateMoveCoordinateTest() {
            JanggiCoordinate oldCoordinate = new JanggiCoordinate(2, 1);
            JanggiCoordinate newCoordinate = new JanggiCoordinate(2, 3);

            JanggiBoard janggiBoard = new JanggiBoard();

            assertThatThrownBy(() -> janggiBoard.movePiece(oldCoordinate, newCoordinate))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

}