package janggi.domain.board;

import static janggi.domain.board.PieceSetup.OUTER_ELEPHANT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.Team;
import janggi.domain.piece.Piece;
import janggi.domain.position.Position;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class BoardTest {

    @Test
    void 출발_좌표와_도착_좌표가_같으면_예외가_발생한다() {
        Board board = BoardFactory.create(OUTER_ELEPHANT, OUTER_ELEPHANT);

        assertThatThrownBy(() -> board.move(Position.from("11"), Position.from("11"), Team.HAN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 출발 좌표와 도착 좌표는 같을 수 없습니다.");
    }

    @Test
    void 잘못된_좌표로_출발_및_도착_좌표를_입력하면_예외가_발생한다() {
        Board board = BoardFactory.create(OUTER_ELEPHANT, OUTER_ELEPHANT);

        assertAll(
                () -> assertThatThrownBy(() -> board.move(Position.from("101"), Position.from("11"), Team.HAN))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("[ERROR] 좌표값 입력은 2자리 숫자여야 합니다."),
                () -> assertThatThrownBy(() -> board.move(Position.from("10"), Position.from("11"), Team.HAN))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("[ERROR] 열 좌표는 1~9까지 사용 가능 합니다"),
                () -> assertThatThrownBy(() -> board.move(Position.from("1a"), Position.from("11"), Team.HAN))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("[ERROR] 열 좌표는 1~9까지 사용 가능 합니다"),
                () -> assertThatThrownBy(() -> board.move(Position.from("a0"), Position.from("11"), Team.HAN))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("[ERROR] 행 좌표는 1~10까지 사용 가능 합니다")
        );
    }
    @Test
    void 출발_좌표와_도착_좌표를_입력하면_도착_좌표의_기물은_출발_좌표의_기물이_된다() {
        Board board = BoardFactory.create(OUTER_ELEPHANT, OUTER_ELEPHANT);
        Position from = Position.from("25");
        Position to = Position.from("35");
        Map<Position, Piece> initBoard = board.showBoard();
        Piece initialFromPiece = initBoard.get(from);

        Map<Position, Piece> movedBoard = board.move(from, to, Team.HAN);
        Piece movedToPiece = movedBoard.get(to);

        assertThat(movedToPiece).isEqualTo(initialFromPiece);
    }

    @Test
    void 출발_좌표와_도착_좌표를_입력하면_출발_좌표의_기물은_빈_기물이_된다() {
        Board board = BoardFactory.create(OUTER_ELEPHANT, OUTER_ELEPHANT);
        Position from = Position.from("25");
        Position to = Position.from("35");

        Map<Position, Piece> movedBoard = board.move(from, to, Team.HAN);
        boolean result = movedBoard.get(from).isEmptyPiece();

        assertThat(result).isTrue();
    }

    @Test
    void 자신의_기물이_아닌_기물을_이동시키면_예외가_발생한다() {
        Board board = BoardFactory.create(OUTER_ELEPHANT, OUTER_ELEPHANT);
        Position from = Position.from("43");
        Position to = Position.from("53");

        assertThatThrownBy(() -> board.move(from, to, Team.CHO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 자신의 기물만 이동시킬 수 있습니다.");
    }
}
