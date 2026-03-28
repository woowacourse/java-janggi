package board;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import pieces.Cha;
import pieces.EmptyPiece;
import pieces.Gung;
import pieces.JolByeong;
import pieces.Ma;
import pieces.Piece;
import pieces.Po;
import pieces.Sa;
import pieces.Sang;
import pieces.Side;
import position.Position;

class InnerSangSetupTest {

    private final static Piece EMPTY_PIECE = new EmptyPiece();

    @Test
    void 한나라진영이_안상차림으로_초기화한다() {
        // given
        SangSetup innerSangSetup = new InnerSangSetup();
        // when
        Board board = innerSangSetup.initialize(Side.HAN);
        // then
        assertThat(board.pieces()).isEqualTo(expectedHanBoard());
    }

    @Test
    void 초나라진영이_안상차림으로_초기화한다() {
        // given
        SangSetup innerSangSetup = new InnerSangSetup();
        // when
        Board board = innerSangSetup.initialize(Side.CHO);
        // then
        assertThat(board.pieces()).isEqualTo(expectedChoBoard());
    }

    private static Map<Position, Piece> expectedChoBoard() {
        Map<Position, Piece> board = emptyChoBoard();
        putChoPieces(board);
        return board;
    }

    private static Map<Position, Piece> expectedHanBoard() {
        Map<Position, Piece> board = emptyHanBoard();
        putHanPieces(board);
        return board;
    }

    private static Map<Position, Piece> emptyChoBoard() {
        Map<Position, Piece> board = new HashMap<>();
        for (int row = 0; row <= 4; row++) {
            for (int column = 0; column <= 8; column++) {
                board.put(new Position(row, column), EMPTY_PIECE);
            }
        }
        return board;
    }

    private static Map<Position, Piece> emptyHanBoard() {
        Map<Position, Piece> board = new HashMap<>();
        for (int row = 5; row <= 9; row++) {
            for (int column = 0; column <= 8; column++) {
                board.put(new Position(row, column), EMPTY_PIECE);
            }
        }
        return board;
    }

    private static void putChoPieces(Map<Position, Piece> board) {
        board.put(new Position(0, 0), new Cha(Side.CHO));
        board.put(new Position(0, 1), new Ma(Side.CHO));
        board.put(new Position(0, 2), new Sang(Side.CHO));
        board.put(new Position(0, 3), new Sa(Side.CHO));
        board.put(new Position(1, 4), new Gung(Side.CHO));
        board.put(new Position(0, 5), new Sa(Side.CHO));
        board.put(new Position(0, 6), new Sang(Side.CHO));
        board.put(new Position(0, 7), new Ma(Side.CHO));
        board.put(new Position(0, 8), new Cha(Side.CHO));

        board.put(new Position(2, 1), new Po(Side.CHO));
        board.put(new Position(2, 7), new Po(Side.CHO));

        board.put(new Position(3, 0), new JolByeong(Side.CHO));
        board.put(new Position(3, 2), new JolByeong(Side.CHO));
        board.put(new Position(3, 4), new JolByeong(Side.CHO));
        board.put(new Position(3, 6), new JolByeong(Side.CHO));
        board.put(new Position(3, 8), new JolByeong(Side.CHO));
    }

    private static void putHanPieces(Map<Position, Piece> board) {
        board.put(new Position(9, 0), new Cha(Side.HAN));
        board.put(new Position(9, 1), new Ma(Side.HAN));
        board.put(new Position(9, 2), new Sang(Side.HAN));
        board.put(new Position(9, 3), new Sa(Side.HAN));
        board.put(new Position(8, 4), new Gung(Side.HAN));
        board.put(new Position(9, 5), new Sa(Side.HAN));
        board.put(new Position(9, 6), new Sang(Side.HAN));
        board.put(new Position(9, 7), new Ma(Side.HAN));
        board.put(new Position(9, 8), new Cha(Side.HAN));

        board.put(new Position(7, 1), new Po(Side.HAN));
        board.put(new Position(7, 7), new Po(Side.HAN));

        board.put(new Position(6, 0), new JolByeong(Side.HAN));
        board.put(new Position(6, 2), new JolByeong(Side.HAN));
        board.put(new Position(6, 4), new JolByeong(Side.HAN));
        board.put(new Position(6, 6), new JolByeong(Side.HAN));
        board.put(new Position(6, 8), new JolByeong(Side.HAN));
    }
}
