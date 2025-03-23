package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import janggi.Camp;
import janggi.PieceSymbol;
import janggi.Point;
import janggi.board.Board;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EmptySpaceTest {

    @DisplayName("비어있는 곳은 기물이 존재하지 않는다.")
    @Test
    void emptySpaceTest() {
        // given
        Board board = new Board();
        EmptySpace emptySpace = new EmptySpace(board);

        // when
        boolean exists = emptySpace.exists();

        // then
        assertThat(exists)
                .isSameAs(false);
    }

    @DisplayName("비어있는 곳을 움직이려는 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenEmptySpaceMove() {
        // given
        Board board = new Board();
        EmptySpace emptySpace = new EmptySpace(board);
        Point from = new Point(1, 1);
        Point to = new Point(1, 2);

        // when & then
        assertThatCode(() -> emptySpace.validateMove(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치에서 기물을 찾을 수 없습니다.");
    }

    @DisplayName("비어있는 곳을 선택할 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenEmptySpaceSelect() {
        // given
        Board board = new Board();
        EmptySpace emptySpace = new EmptySpace(board);

        // when & then
        assertThatCode(() -> emptySpace.validateSelect(Camp.CHU))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치에서 기물을 찾을 수 없습니다.");
    }

    @DisplayName("비어있는 곳으로 기물을 잡으려고 할 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenEmptySpaceCatch() {
        // given
        Board board = new Board();
        EmptySpace emptySpace = new EmptySpace(board);

        // when & then
        assertThatCode(() -> emptySpace.validateCatch(new Soldier(Camp.CHU, board)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치에서 기물을 찾을 수 없습니다.");
    }

    @DisplayName("비어있는 곳의 심볼 테스트")
    @Test
    void emptySpaceSymbolTest() {
        // given
        Board board = new Board();
        EmptySpace emptySpace = new EmptySpace(board);

        // when
        PieceSymbol pieceSymbol = emptySpace.getPieceSymbol();

        // then
        assertThat(pieceSymbol)
                .isSameAs(PieceSymbol.EMPTY_SPACE);
    }
}
