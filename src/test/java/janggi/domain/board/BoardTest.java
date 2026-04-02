package janggi.domain.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.Arrangement;
import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.SideScore;
import janggi.domain.piece.Gung;
import janggi.domain.piece.Pawn;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.initializer.BoardInitializer;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class BoardTest {
    private Board board = new Board(BoardInitializer.createBoard(Arrangement.MA_SANG_MA_SANG, Arrangement.MA_SANG_MA_SANG), 72, 72);

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
        Board board = new Board(BoardInitializer.createBoard(Arrangement.MA_SANG_MA_SANG, Arrangement.MA_SANG_MA_SANG), 72, 72);
        board.move(new Position(1, 1), new Position(2, 1), Side.HAN);

        assertThat(board.getCurrentBoard().get(1).getFirst().pieceType()).isEqualTo(PieceType.CHA);
        assertThat(board.getCurrentBoard().getFirst().getFirst().pieceType()).isEqualTo(PieceType.NONE);
    }

    @Test
    void 다른_진영의_기물을_움직이면_예외_처리한다() {
        Board board = new Board(BoardInitializer.createBoard(Arrangement.MA_SANG_MA_SANG, Arrangement.MA_SANG_MA_SANG), 72, 72);

        assertThatThrownBy(() -> board.move(new Position(1, 1), new Position(2, 1), Side.CHO)).isInstanceOf(IllegalArgumentException.class).hasMessage("자기 진영의 기물만 움직일 수 있습니다.");
    }

    @Test
    void 기물을_잡으면_상대편_진영의_점수가_깎인다() {
        Map<Position, Piece> customBoard = new HashMap<>(Map.of(new Position(1, 1), new Pawn(Side.HAN), new Position(1, 2), new Pawn(Side.CHO)));
        Board board = new Board(customBoard, PieceType.PAWN.getScore(), PieceType.PAWN.getScore());

        board.move(new Position(1, 2), new Position(1, 1), Side.CHO);
        assertThat(board.getScore()).isEqualTo(new SideScore(1.5, PieceType.PAWN.getScore()));
    }

    @Test
    void 궁을_잡으면_게임_끝나는지_확인하는_메서드에서_참으로_리턴한다() {
        Map<Position, Piece> customBoard = new HashMap<>(Map.of(new Position(1, 1), new Pawn(Side.HAN), new Position(1, 2), new Gung(Side.CHO)));
        Board board = new Board(customBoard,0, 0);

        board.move(new Position(1, 1), new Position(1, 2), Side.HAN);
        assertThat(board.isEndGame()).isTrue();
    }
}
