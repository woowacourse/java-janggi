package domain.board;

import domain.piece.Camp;
import domain.piece.Piece;
import domain.piece.PieceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

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
    void 도착지에_위치한_기물이_같은_진영의_기물일_경우_예외를_던진다() {
        assertThatThrownBy(() -> board.move(new Position(8, 0), new Position(7, 0)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 왕이_잡히면_게임이_종료되고_승자를_반환한다() {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(4, 8), Piece.of(Camp.CHO, PieceType.CHARIOT));
        pieces.put(new Position(4, 9), Piece.of(Camp.HAN, PieceType.GENERAL));
        Board emptyBoard = new Board(pieces);

        emptyBoard.move(new Position(4, 8), new Position(4, 9));

        assertThat(emptyBoard.isGameOver()).isTrue();
        assertThat(emptyBoard.winner()).isEqualTo(Camp.CHO);
    }

    @Test
    void 게임이_종료되면_더_이상_기물을_이동할_수_없다() {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(4, 8), Piece.of(Camp.CHO, PieceType.CHARIOT));
        pieces.put(new Position(4, 9), Piece.of(Camp.HAN, PieceType.GENERAL));
        Board emptyBoard = new Board(pieces);

        emptyBoard.move(new Position(4, 8), new Position(4, 9));

        assertThatThrownBy(() -> emptyBoard.move(new Position(4, 9), new Position(4, 8)))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 같은_진영의_기물은_잡을_수_없다() {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(4, 0), Piece.of(Camp.CHO, PieceType.CHARIOT));
        pieces.put(new Position(4, 1), Piece.of(Camp.CHO, PieceType.SOLDIER));
        Board board = new Board(pieces);

        assertThatThrownBy(() -> board.move(new Position(4, 0), new Position(4, 1)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
