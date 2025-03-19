package janggi.board;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BoardTest {

    @Test
    void 출발_지점에_기물이_존재하지_않으면_예외를_발생한다() {
        // given
        Board board = BoardFixture.createBasicBoard();
        Position start = new Position(0, 1);
        Position goal = new Position(0, 2);

        // when && then
        assertThatThrownBy(() -> board.movePiece(start, goal))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 출발 지점에 기물이 존재하지 않습니다.");
    }

    @Test
    void 출발_지점에_기물이_존재하면_예외가_발생하지_않는다() {
        // given
        Board board = BoardFixture.createBasicBoard();
        Position start = new Position(0, 0);
        Position goal = new Position(0, 2);

        // when && then
        assertThatCode(() -> board.movePiece(start, goal))
                .doesNotThrowAnyException();
    }
}
