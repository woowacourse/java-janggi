package chessPiece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardTest {

    @DisplayName("장기판은 기물과 위치를 가진다.")
    @Test
    void board() {
        //given
        ChessPieceInitializer chessPieceInitializer = new ChessPieceInitializer();
        List<ChessPiece> han = chessPieceInitializer.hanInit();
        List<ChessPiece> cho = chessPieceInitializer.choInit();

        //when
        Board board = new Board(han, cho);

        //then
        assertThat(board.getJanggiPan()).hasSize(32);
    }

    @DisplayName("앞에 기물에 있다면 true를 반환한다.")
    @Test
    void isPieceInFront() {
        //given
        List<ChessPiece> chessPieces = List.of(
                new Po(new PieceProfile("포", Nation.HAN), new BoardPosition(3, 2)),
                new Po(new PieceProfile("포", Nation.HAN), new BoardPosition(4, 2))
        );

        List<ChessPiece> chessPieces2 = List.of();

        Board board = new Board(chessPieces, chessPieces2);

        BoardPosition presentPosition = new BoardPosition(3, 2);
        BoardPosition futurePosition = new BoardPosition(5, 2);

        //when
        boolean actual = board.isPieceInFront(presentPosition, futurePosition);

        //then
        assertThat(actual).isFalse();
    }

    @DisplayName("앞에 기물이 po라면 false를 반환한다.")
    @Test
    void isPoInFront() {
        List<ChessPiece> chessPieces = List.of(
                new Po(new PieceProfile("포", Nation.HAN), new BoardPosition(3, 2)),
                new Po(new PieceProfile("포", Nation.HAN), new BoardPosition(4, 2))
        );

        List<ChessPiece> chessPieces2 = List.of();

        Board board = new Board(chessPieces, chessPieces2);

        BoardPosition presentPosition = new BoardPosition(3, 2);
        BoardPosition futurePosition = new BoardPosition(5, 2);

        //when
        boolean actual = board.isPieceInFront(presentPosition, futurePosition);

        //then
        assertThat(actual).isFalse();
    }

    @DisplayName("현재 위치의 말에게 이동가능 여부를 확인할 수 있다.")
    @Test
    void canMove() {
        //given
        List<ChessPiece> chessPieces = List.of(
                new Po(new PieceProfile("포", Nation.HAN), new BoardPosition(3, 2)),
                new Po(new PieceProfile("포", Nation.HAN), new BoardPosition(4, 2))
        );

        List<ChessPiece> chessPieces2 = List.of();

        Board board = new Board(chessPieces, chessPieces2);

        BoardPosition presentPosition = new BoardPosition(3, 2);
        BoardPosition futurePosition = new BoardPosition(7, 3);

        //when //then
        assertThatThrownBy(() -> board.canMoveBy(presentPosition, futurePosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }
}
