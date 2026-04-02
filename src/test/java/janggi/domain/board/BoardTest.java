package janggi.domain.board;

import janggi.domain.Team;
import janggi.domain.piece.Piece;
import janggi.domain.position.Movement;
import janggi.domain.position.Position;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static janggi.domain.board.PieceSetup.OUTER_ELEPHANT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BoardTest {

    @Test
    void 출발_좌표와_도착_좌표를_입력하면_도착_좌표의_기물은_출발_좌표의_기물이_된다() {
        Board board = BoardFactory.create(OUTER_ELEPHANT, OUTER_ELEPHANT);
        Movement movement = new Movement(Position.from("25"), Position.from("35"));
        Map<Position, Piece> initBoard = board.showBoard();
        Piece initialFromPiece = initBoard.get(movement.getFrom());

        board.move(movement, Team.HAN);
        Map<Position, Piece> movedBoard = board.showBoard();
        Piece movedToPiece = movedBoard.get(movement.getTo());

        assertThat(movedToPiece).isEqualTo(initialFromPiece);
    }

    @Test
    void 출발_좌표와_도착_좌표를_입력하면_출발_좌표의_기물은_빈_기물이_된다() {
        Board board = BoardFactory.create(OUTER_ELEPHANT, OUTER_ELEPHANT);
        Movement movement = new Movement(Position.from("25"), Position.from("35"));

        board.move(movement, Team.HAN);
        Map<Position, Piece> movedBoard = board.showBoard();
        boolean result = movedBoard.get(movement.getFrom()).isEmptyPiece();

        assertThat(result).isTrue();
    }

    @Test
    void 자신의_기물이_아닌_기물을_이동시키면_예외가_발생한다() {
        Board board = BoardFactory.create(OUTER_ELEPHANT, OUTER_ELEPHANT);
        Movement movement = new Movement(Position.from("43"), Position.from("53"));

        assertThatThrownBy(() -> board.move(movement, Team.CHO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 자신의 기물만 이동시킬 수 있습니다.");
    }
}
