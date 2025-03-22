package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import domain.board.Board;
import domain.board.strategy.SangMaMaSang;
import domain.piece.Country;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class BoardTest {

    @Nested
    class BoardValidation {

        @DisplayName("장기판 밖으로 나갈 수 없다.")
        @Test
        void validateBoardBoundary1() {
            int row = 11;
            int col = 10;

            Board board = new Board(new SangMaMaSang());
            boolean isOutOfBoundary = board.isOutOfBoundary(new Coordinate(row, col));

            assertThat(isOutOfBoundary).isTrue();
        }

        @DisplayName("자신의 기물이 아니면 움직일 수 없다.")
        @Test
        void validateOriginCoordinateTest() {
            Board board = new Board(new SangMaMaSang());

            Coordinate originCoordinate = new Coordinate(1, 1);

            assertThatThrownBy(() -> board.validateOriginCoordinate(originCoordinate, Country.CHO))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    class BoardMoveTest {

        @DisplayName("말을 움직인다.")
        @Test
        void movePieceTest() {
            Coordinate oldCoordinate = new Coordinate(1, 3);
            Coordinate newCoordinate = new Coordinate(3, 4);

            Board board = new Board(new SangMaMaSang());

            board.movePiece(oldCoordinate, newCoordinate);

            assertThatThrownBy(() -> board.getPieceType(oldCoordinate))
                    .isInstanceOf(IllegalArgumentException.class);
            assertThat(board.getPieceType(newCoordinate)).isEqualTo("마");
        }

        @DisplayName("말이 이동 불가능한 위치로 이동할 수 없다.")
        @Test
        void validateMoveCoordinateTest() {
            Coordinate oldCoordinate = new Coordinate(1, 2);
            Coordinate newCoordinate = new Coordinate(3, 2);

            Board board = new Board(new SangMaMaSang());

            assertThatThrownBy(() -> board.movePiece(oldCoordinate, newCoordinate))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

}