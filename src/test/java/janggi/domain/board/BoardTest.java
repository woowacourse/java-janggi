package janggi.domain.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.Arrangement;
import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Po;
import janggi.initializer.BoardInitializer;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class BoardTest {
    private Board board = new Board(BoardInitializer.createBoard(Arrangement.MA_SANG_MA_SANG, Arrangement.MA_SANG_MA_SANG));

    @Test
    void 빈_칸인지_여부를_제대로_반영한다() {
        assertThat(board.isEmpty(new Position(4,4))).isTrue();
        assertThat(board.isEmpty(new Position(1,1))).isFalse();
    }

    @Test
    void 해당_타입의_기물인지_여부를_제대로_반영한다() {
        assertThat(board.isEqualPieceType(new Position(4,4), PieceType.NONE)).isTrue();
        assertThat(board.isEqualPieceType(new Position(1,1), PieceType.CHA)).isTrue();

        assertThat(board.isEqualPieceType(new Position(1,2), PieceType.SANG)).isFalse();
        assertThat(board.isEqualPieceType(new Position(1,3), PieceType.MA)).isFalse();
    }

    @Test
    void 해당_기물이_같은_진영인지_여부를_제대로_반영한다() {
        assertThat(board.isAlly(Side.HAN, new Position(1,1))).isTrue();
        assertThat(board.isAlly(Side.HAN, new Position(9,1))).isFalse();
    }

    @Test
    void 자기_진영의_기물을_움직이면_정상_작동한다() {
        Board board = new Board(BoardInitializer.createBoard(Arrangement.MA_SANG_MA_SANG, Arrangement.MA_SANG_MA_SANG));
        board.move(new Position(1, 1), new Position(2, 1), Side.HAN);

        assertThat(board.getCurrentBoard().get(1).getFirst().pieceType()).isEqualTo(PieceType.CHA);
        assertThat(board.getCurrentBoard().getFirst().getFirst().pieceType()).isEqualTo(PieceType.NONE);
    }

    @Test
    void 다른_진영의_기물을_움직이면_예외_처리한다() {
        Board board = new Board(BoardInitializer.createBoard(Arrangement.MA_SANG_MA_SANG, Arrangement.MA_SANG_MA_SANG));

        assertThatThrownBy(() -> board.move(new Position(1, 1), new Position(2, 1), Side.CHO)).isInstanceOf(IllegalArgumentException.class).hasMessage("자기 진영의 기물만 움직일 수 있습니다.");
    }
}
