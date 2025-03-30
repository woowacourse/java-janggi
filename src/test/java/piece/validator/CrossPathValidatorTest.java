package piece.validator;

import static org.assertj.core.api.Assertions.assertThat;

import board.Board;
import board.BoardFixture;
import coordinate.Coordinate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import piece.Piece;
import piece.PieceType;
import team.Team;

class CrossPathValidatorTest {

    @Test
    @DisplayName("차가 (5,5) -> (8,5) 으로 이동할 때 장애물이 하나 이상 있을 경우 이동할 수 없다.")
    void test1() {
        // given
        Board board = new BoardFixture()
                .addPiece(5, 5, new Piece(Team.HAN, PieceType.차))
                .addPiece(6, 5, new Piece(Team.CHO, PieceType.포))
                .build();
        CrossPathValidator validator = new CrossPathValidator();

        // when
        boolean result = validator.validate(board, new Coordinate(5, 5), new Coordinate(8, 5));

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("차가 (5,5) -> (8,5) 으로 이동할 때 장애물이 하나도 없을 경우 이동할 수 있다.")
    void test2() {
        // given
        Board board = new BoardFixture()
                .addPiece(5, 5, new Piece(Team.HAN, PieceType.차))
                .build();
        CrossPathValidator validator = new CrossPathValidator();

        // when
        boolean result = validator.validate(board, new Coordinate(5, 5), new Coordinate(8, 5));

        // then
        assertThat(result).isTrue();
    }
}
