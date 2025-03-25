package janggi.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import janggi.board.point.Point;
import janggi.piece.Camp;
import janggi.piece.Elephant;
import janggi.piece.Piece;
import janggi.piece.SoldierByeong;
import janggi.piece.SoldierJol;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class BoardTest {

    @DisplayName("특정 좌표에 기물을 둘 수 있다.")
    @Test
    void placePieceTest() {
        // given
        Board board = new Board();
        Point point = new Point(1, 1);
        Piece piece = new Elephant(Camp.HAN, board);

        // when & then
        assertThatCode(() -> board.placePiece(point, piece))
                .doesNotThrowAnyException();
    }

    @DisplayName("영역 밖으로 기물을 둘 때 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource({
            "8,11",
            "8,-1",
            "10,9",
            "-1,9"
    })
    void shouldThrowException_WhenInvalidPoint(int x, int y) {
        // given
        Board board = new Board();
        Point point = new Point(x, y);
        Piece piece = new Elephant(Camp.HAN, board);

        // when & then
        assertThatCode(() -> board.placePiece(point, piece))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("기물의 위치는 9 x 10 영역을 벗어날 수 없습니다.");
    }

    @DisplayName("기물을 정상적인 좌표로 움직인다.")
    @Test
    void movePieceTest() {
        // given
        Board board = new Board();
        Point from = new Point(0, 3);
        Point to = new Point(0, 4);
        Piece piece = new SoldierJol(board);
        board.placePiece(from, piece);

        // when
        board.movePiece(from, to);

        // then
        assertThat(board.getPlacedPieces())
                .containsEntry(to, piece);
    }

    @DisplayName("기물을 보드판의 영역을 넘어서 움직일 경우 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource({
            "0, 11",
            "10, 0",
    })
    void shouldThrowException_WhenInvalidMovePiece(int x, int y) {
        // given
        Board board = new Board();
        Point from = new Point(0, 3);
        Point to = new Point(x, y);
        board.placePiece(from, new SoldierJol(board));

        // when & then
        assertThatCode(() -> board.movePiece(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("기물의 위치는 9 x 10 영역을 벗어날 수 없습니다.");
    }

    @DisplayName("이동시킬 기물을 찾을 수 없는 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenNotFoundPiece() {
        // given
        Board board = new Board();
        Point from = new Point(0, 3);
        Point to = new Point(0, 4);

        // when & then
        assertThatCode(() -> board.movePiece(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치에 기물이 없습니다. 위치: (0, 3)");
    }

    @DisplayName("같은 진영의 기물을 잡는 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenCatchSameCampPiece() {
        // given
        Board board = new Board();
        Point from = new Point(0, 3);
        Point to = new Point(0, 4);
        Piece fromPiece = new SoldierJol(board);
        Piece toPiece = new SoldierJol(board);
        board.placePiece(from, fromPiece);
        board.placePiece(to, toPiece);

        // when & then
        assertThatCode(() -> board.movePiece(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 기물을 잡을 수 없습니다.");
    }

    @DisplayName("다른 진영의 기물을 잡을 수 있다.")
    @Test
    void movePieceCatchTest() {
        // given
        Board board = new Board();
        Point from = new Point(0, 3);
        Point to = new Point(0, 4);
        Piece fromPiece = new SoldierJol(board);
        Piece toPiece = new SoldierByeong(board);
        board.placePiece(from, fromPiece);
        board.placePiece(to, toPiece);

        // when
        board.movePiece(from, to);

        // then
        assertThat(board.getPlacedPieces())
                .containsEntry(to, fromPiece);
        assertThat(board.getPlacedPieces())
                .doesNotContainEntry(to, toPiece);
    }

    @DisplayName("같은 위치로 이동할 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenMovePieceSamePoint() {
        // given
        Board board = new Board();
        Point fromPoint = new Point(1, 1);
        Point toPoint = new Point(1, 1);
        board.placePiece(fromPoint, new SoldierJol(board));

        // when & then
        assertThatCode(() -> board.movePiece(fromPoint, toPoint))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("같은 위치로 이동할 수 없습니다.");
    }
}
