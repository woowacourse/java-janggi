package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BoardTest {

    @Test
    @DisplayName("보드 객체를 생성한다.")
    void test1() {
        //given
        Map<ChessPosition, ChessPiece> chessPieces = new HashMap<>();

        //when
        final Board board = new Board(chessPieces);

        //then
        assertThat(board).isInstanceOf(Board.class);
    }

    @Test
    @DisplayName("기물들을 초기화 한다.")
    void test2() {
        //given
        final ChessPosition chessPosition = new ChessPosition(0, 0);
        //when
        final Board board = Board.initialize();
        final Map<ChessPosition, ChessPiece> chessPieces = board.getChessPieces();

        //then
        assertThat(chessPieces.get(chessPosition)).isEqualTo(ChessPieceType.CHARIOT);
    }
}
