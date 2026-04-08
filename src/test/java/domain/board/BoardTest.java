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
    void 이동시키려는_좌표에_기물이_없을_경우_예외를_던진다() {
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
    void 도착지가_비어있을_경우_해당_좌표로_이동한다() {
        Position from = new Position(4, 0);
        Position to = new Position(4, 1);

        Piece departurePiece = Piece.of(Camp.CHO, PieceType.SOLDIER);

        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(from, departurePiece);
        Board fakeBoard = new Board(pieces);

        fakeBoard.move(from, to);

        assertThat(fakeBoard.isExistPieceAt(from)).isFalse();
        assertThat(fakeBoard.pieceAt(to)).isEqualTo(departurePiece);
    }


    @Test
    void 도착지에_위치한_기물이_다른_진영의_기물일_경우_해당_기물을_잡고_해당_좌표로_이동한다() {
        Position from = new Position(4, 0);
        Position to = new Position(4, 1);

        Piece departurePiece = Piece.of(Camp.CHO, PieceType.SOLDIER);

        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(from, departurePiece);
        pieces.put(to, Piece.of(Camp.HAN, PieceType.SOLDIER));
        Board fakeBoard = new Board(pieces);

        fakeBoard.move(from, to);

        assertThat(fakeBoard.isExistPieceAt(from)).isFalse();
        assertThat(fakeBoard.pieceAt(to)).isEqualTo(departurePiece);
    }


    @Test
    void 왕이_잡히면_게임이_종료되고_승자를_반환한다() {
        Position from = new Position(4, 8);
        Position to = new Position(4, 9);
        Position choGeneralPosition = new Position(4, 0);

        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(choGeneralPosition, Piece.of(Camp.CHO, PieceType.GENERAL));
        pieces.put(from, Piece.of(Camp.CHO, PieceType.CHARIOT));
        pieces.put(to, Piece.of(Camp.HAN, PieceType.GENERAL));
        Board fakeBoard = new Board(pieces);

        fakeBoard.move(from, to);

        assertThat(fakeBoard.isGameInProgress()).isTrue();
        assertThat(fakeBoard.winner()).isEqualTo(Camp.CHO);
    }

    @Test
    void 게임이_종료되지_않은_상태에서_승자를_조회하면_예외를_던진다() {
        Position from = new Position(4, 8);
        Position to = new Position(4, 9);

        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(from, Piece.of(Camp.CHO, PieceType.CHARIOT));
        pieces.put(to, Piece.of(Camp.HAN, PieceType.SOLDIER));
        Board fakeBoard = new Board(pieces);

        fakeBoard.move(from, to);

        assertThatThrownBy(fakeBoard::winner).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 게임이_종료되면_더_이상_기물을_이동할_수_없다() {
        Position from = new Position(4, 7);
        Position to = new Position(4, 8);

        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(from, Piece.of(Camp.CHO, PieceType.CHARIOT));
        pieces.put(to, Piece.of(Camp.HAN, PieceType.GENERAL));
        Board fakeBoard = new Board(pieces);

        fakeBoard.move(from, to);

        assertThatThrownBy(() -> fakeBoard.move(to, new Position(4, 9)))
                .isInstanceOf(IllegalStateException.class);
    }
}
