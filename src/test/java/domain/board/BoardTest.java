package domain.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import domain.piece.coordiante.Coordinate;
import domain.piece.Byeong;
import domain.piece.Cha;
import domain.piece.Country;
import domain.piece.Gung;
import domain.piece.Ma;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Sa;
import domain.piece.Sang;
import domain.piece.jump.Pho;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardTest {


    @DisplayName("자신의 기물이 아니면 움직일 수 없다.")
    @Test
    void validateFromTest() {
        Map<Coordinate, Piece> pieces = new HashMap<>();
        pieces.put(new Coordinate(1, 1), new Ma(Country.CHO));
        Board board = new Board(pieces);

        Coordinate from = new Coordinate(1, 1);

        assertThatThrownBy(() -> board.validateIsMyPiece(from, Country.HAN))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("자신의 기물이면 움직일 수 있다.")
    @Test
    void validateFromTest2() {
        Map<Coordinate, Piece> pieces = new HashMap<>();
        pieces.put(new Coordinate(1, 1), new Ma(Country.CHO));
        Board board = new Board(pieces);

        Coordinate from = new Coordinate(1, 1);

        assertThatCode(() -> board.validateIsMyPiece(from, Country.CHO))
                .doesNotThrowAnyException();
    }


    @DisplayName("마를 움직인다.")
    @Test
    void movePieceTest() {
        Coordinate from = new Coordinate(1, 3);

        HashMap<Coordinate, Piece> pieces = new HashMap<>();
        pieces.put(from, new Ma(Country.HAN));
        Board board = new Board(pieces);

        Coordinate to = new Coordinate(3, 4);
        board.movePiece(from, to);

        assertThat(board.findPieceTypeByCoordinate(to)).isEqualTo(PieceType.MA);
    }

    @DisplayName("병을 움직인다.")
    @Test
    void movePieceTest2() {
        Coordinate from = new Coordinate(1, 1);

        HashMap<Coordinate, Piece> pieces = new HashMap<>();
        pieces.put(from, new Byeong(Country.HAN));
        Board board = new Board(pieces);

        Coordinate to = new Coordinate(2, 1);
        board.movePiece(from, to);

        assertThat(board.findPieceTypeByCoordinate(to)).isEqualTo(PieceType.BYEONG);
    }

    @DisplayName("상을 움직인다.")
    @Test
    void movePieceTest3() {
        Coordinate from = new Coordinate(1, 2);

        HashMap<Coordinate, Piece> pieces = new HashMap<>();
        pieces.put(from, new Sang(Country.HAN));
        Board board = new Board(pieces);

        Coordinate to = new Coordinate(4, 4);
        board.movePiece(from, to);

        assertThat(board.findPieceTypeByCoordinate(to)).isEqualTo(PieceType.SANG);
    }

    @DisplayName("사를 움직인다")
    @Test
    void movePieceTest4() {
        Coordinate from = new Coordinate(1, 4);

        HashMap<Coordinate, Piece> pieces = new HashMap<>();
        pieces.put(from, new Sa(Country.HAN));
        Board board = new Board(pieces);

        Coordinate to = new Coordinate(2, 5);
        board.movePiece(from, to);

        assertThat(board.findPieceTypeByCoordinate(to)).isEqualTo(PieceType.SA);
    }

    @DisplayName("포를 움직인다")
    @Test
    void movePieceTest5() {
        Coordinate from = new Coordinate(2, 2);

        HashMap<Coordinate, Piece> pieces = new HashMap<>();
        pieces.put(from, new Pho(Country.HAN));
        pieces.put(new Coordinate(2, 3), new Ma(Country.HAN));
        Board board = new Board(pieces);

        Coordinate to = new Coordinate(2, 4);
        board.movePiece(from, to);

        assertThat(board.findPieceTypeByCoordinate(to)).isEqualTo(PieceType.PHO);
    }

    @DisplayName("차를 움직인다")
    @Test
    void movePieceTest6() {
        Coordinate from = new Coordinate(1, 1);

        HashMap<Coordinate, Piece> pieces = new HashMap<>();
        pieces.put(from, new Cha(Country.HAN));
        Board board = new Board(pieces);

        Coordinate to = new Coordinate(5, 1);
        board.movePiece(from, to);

        assertThat(board.findPieceTypeByCoordinate(to)).isEqualTo(PieceType.CHA);
    }

    @DisplayName("궁을 움직인다")
    @Test
    void movePieceTest7() {
        Coordinate from = new Coordinate(1, 5);

        HashMap<Coordinate, Piece> pieces = new HashMap<>();
        pieces.put(from, new Gung(Country.HAN));
        Board board = new Board(pieces);

        Coordinate to = new Coordinate(2, 5);
        board.movePiece(from, to);

        assertThat(board.findPieceTypeByCoordinate(to)).isEqualTo(PieceType.GUNG);
    }

    @DisplayName("궁이 죽었는지 확인한다_죽은 경우")
    @Test
    void isChoGungDead() {
        Board board = new Board(new HashMap<>());

        assertThat(board.isChoGungDead()).isTrue();
        assertThat(board.isHanGungDead()).isTrue();
    }

    @DisplayName("궁이 죽었는지 확인한다_살아있는 경우")
    @Test
    void isChoGungDead2() {
        HashMap<Coordinate, Piece> pieces = new HashMap<>();
        pieces.put(new Coordinate(2, 5), new Gung(Country.HAN));
        pieces.put(new Coordinate(9, 5), new Gung(Country.CHO));
        Board board = new Board(pieces);

        assertThat(board.isChoGungDead()).isFalse();
        assertThat(board.isHanGungDead()).isFalse();
    }

    @DisplayName("한나라의 점수를 계산한다")
    @Test
    void calculateHanScoreTest() {
        Map<Coordinate, Piece> pieces = new HashMap<>();
        pieces.put(new Coordinate(1, 1), new Ma(Country.HAN));
        Board board = new Board(pieces);

        assertThat(board.calculateHanScore()).isEqualTo(5);
    }

    @DisplayName("초나라의 점수를 계산한다")
    @Test
    void calculateChoScoreTest() {
        Map<Coordinate, Piece> pieces = new HashMap<>();
        pieces.put(new Coordinate(1, 1), new Ma(Country.CHO));
        Board board = new Board(pieces);

        assertThat(board.calculateChoScore()).isEqualTo(5);
    }

}
