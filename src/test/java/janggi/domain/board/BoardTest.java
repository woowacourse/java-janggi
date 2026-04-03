package janggi.domain.board;

import janggi.domain.Arrangement;
import janggi.domain.MoveResult;
import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.piece.PieceType;
import janggi.initializer.BoardInitializer;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BoardTest {
    @Test
    void 자기_진영의_기물을_움직이면_정상_작동한다() {
        Board board = new Board(
                BoardInitializer.createBoard(Arrangement.MA_SANG_MA_SANG, Arrangement.MA_SANG_MA_SANG),
                BoardInitializer.createScoresBySide()
        );

        MoveResult moveResult = board.move(new Position(1, 1), new Position(2, 1), Side.HAN);

        assertThat(moveResult.capturedPieceType()).isEqualTo(PieceType.NONE);
    }

    @Test
    void 다른_진영의_기물을_움직이면_예외_처리한다() {
        Board board = new Board(
                BoardInitializer.createBoard(Arrangement.MA_SANG_MA_SANG, Arrangement.MA_SANG_MA_SANG),
                BoardInitializer.createScoresBySide());

        assertThatThrownBy(() -> board.move(new Position(1, 1), new Position(2, 1), Side.CHO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("자기 진영의 기물만 움직일 수 있습니다.");
    }
}