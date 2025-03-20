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

    @DisplayName("장기판의 기물이 포일때 앞의 기물이 포라면 예외를 던진다.")
    @Test
    void notUpdateBoard() {
        //given
        List<ChessPiece> chessPieces = List.of(
                new Po(new PieceProfile("포", Nation.HAN), new BoardPosition(3, 2)),
                new Po(new PieceProfile("포", Nation.HAN), new BoardPosition(4, 2))
        );

        List<ChessPiece> chessPieces2 = List.of();

        Board board = new Board(chessPieces, chessPieces2);

        BoardPosition presentPosition = new BoardPosition(3, 2);
        BoardPosition futurePosition = new BoardPosition(5, 2);

        //when //then
        assertThatThrownBy(() -> board.updateBoard(presentPosition, futurePosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("장기판의 기물을 옮길 수 있다.")
    @Test
    void updateBoard() {
        //given
        List<ChessPiece> chessPieces = List.of(
                new Byeong(new PieceProfile("병", Nation.HAN), new BoardPosition(3, 2))
        );

        List<ChessPiece> chessPieces2 = List.of();

        Board board = new Board(chessPieces, chessPieces2);

        BoardPosition presentPosition = new BoardPosition(3, 2);
        BoardPosition futurePosition = new BoardPosition(4, 2);

        //when
        board.updateBoard(presentPosition, futurePosition);

        //then
        ChessPiece actual = board.getJanggiPan().get(futurePosition);
        assertThat(actual).isEqualTo(new Byeong(new PieceProfile("병", Nation.HAN),
                new BoardPosition(4, 2)));
    }

    @DisplayName("장기판의 기물이 포 인경우 옮길 수 있다.")
    @Test
    void poUpdate() {
        //given
        List<ChessPiece> chessPieces = List.of(
                new Po(new PieceProfile("포", Nation.HAN), new BoardPosition(3, 2)),
                new Byeong(new PieceProfile("병", Nation.HAN), new BoardPosition(4, 2))
        );

        List<ChessPiece> chessPieces2 = List.of();

        Board board = new Board(chessPieces, chessPieces2);

        BoardPosition presentPosition = new BoardPosition(3, 2);
        BoardPosition futurePosition = new BoardPosition(5, 2);

        //when
        board.updateBoard(presentPosition, futurePosition);

        //then
        ChessPiece actual = board.getJanggiPan().get(futurePosition);
        assertThat(actual).isEqualTo(new Po(new PieceProfile("포", Nation.HAN),
                new BoardPosition(5, 2)));
    }

    @DisplayName("장기판의 포앞에 기물이 2개 이상 존재한다면 예외를 던진다.")
    @Test
    void notUpdateFoInFrontTwoChessPiece() {
        List<ChessPiece> chessPieces = List.of(
                new Po(new PieceProfile("포", Nation.HAN), new BoardPosition(3, 2)),
                new Jol(new PieceProfile("졸", Nation.HAN), new BoardPosition(4, 2)),
                new Byeong(new PieceProfile("병", Nation.HAN), new BoardPosition(5, 2))
        );

        List<ChessPiece> chessPieces2 = List.of();

        Board board = new Board(chessPieces, chessPieces2);

        BoardPosition presentPosition = new BoardPosition(3, 2);
        BoardPosition futurePosition = new BoardPosition(6, 2);

        //when //then
        assertThatThrownBy(() -> board.updateBoard(presentPosition, futurePosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("기물이 이동하는 경로를 검사한다.")
    @Test
    void checkObstacle() {
        //given
        List<ChessPiece> chessPieces = List.of(
                new Cha(new PieceProfile("차", Nation.HAN), new BoardPosition(4, 2)),
                new Byeong(new PieceProfile("병", Nation.HAN), new BoardPosition(7, 2))
        );

        List<ChessPiece> chessPieces2 = List.of();

        BoardPosition presentPosition = new BoardPosition(4, 2);
        BoardPosition futurePosition = new BoardPosition(8, 2);

        Board board = new Board(chessPieces, chessPieces2);

        //when //then
        assertThatThrownBy(() -> board.checkObstacle(presentPosition, futurePosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }
}
