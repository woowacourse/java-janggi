package domain.board;

import domain.piece.Camp;
import domain.piece.Piece;
import domain.piece.PieceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class BoardTest {
    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board(BoardInitializer.init(BoardSetting.LEFT_ELEPHANT_SET_UP));
    }

    @Test
    void 해당_좌표에_기물의_존재_여부를_반환한다() {
        assertThat(board.isExistPieceAt(new Position(8, 0))).isTrue();
        assertThat(board.isExistPieceAt(new Position(8, 5))).isFalse();
    }

    @Test
    void 해당_좌표에_위치한_기물을_반환한다() {
        assertThat(board.pieceAt(new Position(8, 0))).isEqualTo(Piece.of(Camp.CHO, PieceType.CHARIOT));
    }

    @Test
    void 이동을_선택한_좌표에_기물이_없을_경우_예외를_던진다() {
        assertThatThrownBy(() -> board.pieceAt(new Position(5, 5))).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 출발지와_도착지가_같을_경우_예외를_던진다() {
        assertThatThrownBy(() -> board.move(new Position(8, 0), new Position(8, 0)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 출발지에서_도착지로_기물을_이동시킨다() {
        Position from = new Position(8, 3);
        Piece movingPiece = board.pieceAt(from);

        Position first = new Position(8, 4);
        board.move(from, first);

        Position second = new Position(8, 5);
        board.move(first, second);

        Position to = new Position(8, 6);
        board.move(second, to);

        assertThat(board.isExistPieceAt(from)).isFalse();
        assertThat(board.pieceAt(to)).isEqualTo(movingPiece);
    }

    @Test
    void 도착지에_위치한_기물이_같은_진영의_기물일_경우_예외를_던진다() {
        assertThatThrownBy(() -> board.move(new Position(8, 0), new Position(7, 0)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
