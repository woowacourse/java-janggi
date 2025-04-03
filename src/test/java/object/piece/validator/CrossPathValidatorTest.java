package object.piece.validator;

import static org.assertj.core.api.Assertions.assertThat;

import object.board.Board;
import object.board.BoardFixture;
import object.coordinate.Coordinate;
import object.piece.Piece;
import object.piece.PieceType;
import object.team.Country;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CrossPathValidatorTest {

    @Test
    @DisplayName("차가 (5,5) -> (8,5) 으로 이동할 때 장애물이 하나 이상 있을 경우 이동할 수 없다.")
    void test1() {
        // given
        Board board = new BoardFixture()
                .addPiece(5, 5, new Piece(Country.HAN, PieceType.CHA))
                .addPiece(6, 5, new Piece(Country.CHO, PieceType.PO))
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
                .addPiece(5, 5, new Piece(Country.HAN, PieceType.CHA))
                .build();
        CrossPathValidator validator = new CrossPathValidator();

        // when
        boolean result = validator.validate(board, new Coordinate(5, 5), new Coordinate(8, 5));

        // then
        assertThat(result).isTrue();
    }
}
