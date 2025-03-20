package janggi.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import janggi.Camp;
import janggi.Point;
import janggi.piece.Elephant;
import janggi.piece.Piece;
import janggi.piece.Soldier;
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
            "9,11",
            "9,0",
            "10,10",
            "0,10"
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
    void moveTest() {
        // given
        Board board = new Board();
        Point from = new Point(0, 3);
        Piece piece = new Soldier(Camp.CHU, board);
        board.placePiece(from, piece);
        Point to = new Point(0, 4);

        // when
        board.move(from, to);

        // then
        assertThat(board.getPlacedPieces())
                .containsEntry(to, piece);
    }

    @DisplayName("기물을 보드판의 영역을 넘어서 움직일 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenInvalidMove() {
        // given
        Board board = new Board();
        Point from = new Point(0, 3);
        Piece piece = new Soldier(Camp.CHU, board);
        board.placePiece(from, piece);
        Point to = new Point(0, 15);

        // when & then
        assertThatCode(() -> board.move(from, to))
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
        assertThatCode(() -> board.move(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치에서 기물을 찾을 수 없습니다.");
    }

    @DisplayName("같은 진영의 기물을 잡는 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenCatchSameCampPiece() {
        // given
        Board board = new Board();
        Point from = new Point(0, 3);
        Point to = new Point(0, 4);
        Piece fromPiece = new Soldier(Camp.CHU, board);
        Piece toPiece = new Soldier(Camp.CHU, board);
        board.placePiece(from, fromPiece);
        board.placePiece(to, toPiece);

        // when & then
        assertThatCode(() -> board.move(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("같은 진영의 기물을 잡을 수 없습니다.");
    }

    @DisplayName("다른 진영의 기물을 잡을 수 있다.")
    @Test
    void moveCatchTest() {
        // given
        Board board = new Board();
        Point from = new Point(0, 3);
        Point to = new Point(0, 4);
        Piece fromPiece = new Soldier(Camp.CHU, board);
        Piece toPiece = new Soldier(Camp.HAN, board);
        board.placePiece(from, fromPiece);
        board.placePiece(to, toPiece);

        // when
        board.move(from, to);

        // then
        assertThat(board.getPlacedPieces())
                .containsEntry(from, null);
        assertThat(board.getPlacedPieces())
                .containsEntry(to, fromPiece);
        assertThat(board.getPlacedPieces())
                .doesNotContainEntry(to, toPiece);
    }
}
