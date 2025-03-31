package object.piece.validator;

import static org.assertj.core.api.Assertions.assertThat;

import object.board.Board;
import object.board.BoardFixture;
import object.coordinate.Coordinate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import object.piece.Piece;
import object.piece.PieceType;
import object.team.Country;

class PoPathValidatorTest {

    @Test
    @DisplayName("포가 (5,5) -> (6,5) 으로 이동할 때 장애물이 없을 경우 이동할 수 없다.")
    void test1() {
        // given
        Board board = new BoardFixture()
                .addPiece(5, 5, new Piece(Country.HAN, PieceType.포))
                .build();
        PoPathValidator validator = new PoPathValidator();

        // when
        boolean result = validator.validate(board, new Coordinate(5, 5), new Coordinate(7, 5));

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("포가 (5,5) -> (8,5) 으로 이동할 때 장애물이 하나가 아닐 경우 이동할 수 없다.")
    void test2() {
        // given
        Board board = new BoardFixture()
                .addPiece(5, 5, new Piece(Country.HAN, PieceType.포))
                .addPiece(6, 5, new Piece(Country.CHO, PieceType.마))
                .addPiece(7, 5, new Piece(Country.CHO, PieceType.상)).build();
        PoPathValidator validator = new PoPathValidator();

        // when
        boolean result = validator.validate(board, new Coordinate(5, 5), new Coordinate(8, 5));

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("포가 (5,5) -> (8,5) 으로 이동할 때 장애물이 하나이면서 그 장애물이 포인 경우 이동할 수 없다.")
    void test3() {
        // given
        Board board = new BoardFixture()
                .addPiece(5, 5, new Piece(Country.HAN, PieceType.포))
                .addPiece(6, 5, new Piece(Country.CHO, PieceType.포))
                .build();
        PoPathValidator validator = new PoPathValidator();

        // when
        boolean result = validator.validate(board, new Coordinate(5, 5), new Coordinate(8, 5));

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("포가 (5,5) -> (8,5) 으로 이동할 때 장애물이 하나이면서 그 장애물이 포가 아니면서 도착 좌표에 포가 있으면 이동할 수 없다.")
    void test4() {
        // given
        Board board = new BoardFixture()
                .addPiece(5, 5, new Piece(Country.HAN, PieceType.포))
                .addPiece(6, 5, new Piece(Country.CHO, PieceType.마))
                .addPiece(8, 5, new Piece(Country.CHO, PieceType.포))
                .build();
        PoPathValidator validator = new PoPathValidator();

        // when
        boolean result = validator.validate(board, new Coordinate(5, 5), new Coordinate(8, 5));

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("포가 (5,5) -> (8,5) 으로 이동할 때 장애물이 하나이면서 그 장애물이 포가 아니면서 도착 좌표에 포가 없을 경우 이동할 수 있다.")
    void test5() {
        // given
        Board board = new BoardFixture()
                .addPiece(5, 5, new Piece(Country.HAN, PieceType.포))
                .addPiece(6, 5, new Piece(Country.CHO, PieceType.마))
                .addPiece(8, 5, new Piece(Country.CHO, PieceType.상))
                .build();
        PoPathValidator validator = new PoPathValidator();

        // when
        boolean result = validator.validate(board, new Coordinate(5, 5), new Coordinate(8, 5));

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("포가 (5,5) -> (8,5) 으로 이동할 때 장애물이 하나이면서 그 장애물이 포가 아니면서 도착 좌표에 피스가 없을 경우 이동할 수 있다.")
    void test6() {
        // given
        Board board = new BoardFixture()
                .addPiece(5, 5, new Piece(Country.HAN, PieceType.포))
                .addPiece(6, 5, new Piece(Country.CHO, PieceType.상))
                .build();
        PoPathValidator validator = new PoPathValidator();

        // when
        boolean result = validator.validate(board, new Coordinate(5, 5), new Coordinate(8, 5));

        // then
        assertThat(result).isTrue();
    }
}
