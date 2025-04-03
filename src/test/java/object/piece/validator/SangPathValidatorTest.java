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

class SangPathValidatorTest {

    @Test
    @DisplayName("상이 (5,5) -> (3,2) 으로 이동할 때 (5,4)를 거치기 때문에 이동할 수 없다.")
    void test1() {
        // given
        Board board = new BoardFixture()
                .addPiece(5, 5, new Piece(Country.HAN, PieceType.SANG))
                .addPiece(5, 4, new Piece(Country.CHO, PieceType.SANG))
                .build();
        SangPathValidator validator = new SangPathValidator();

        // when
        boolean result = validator.validate(board, new Coordinate(5, 5), new Coordinate(3, 2));

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("상이 (5,5) -> (3,2) 으로 이동할 때 장애물이 하나도 없을 경우 이동할 수 있다.")
    void test2() {
        // given
        Board board = new BoardFixture()
                .addPiece(5, 5, new Piece(Country.HAN, PieceType.SANG))
                .build();
        SangPathValidator validator = new SangPathValidator();

        // when
        boolean result = validator.validate(board, new Coordinate(5, 5), new Coordinate(3, 2));

        // then
        assertThat(result).isTrue();
    }
}
