package janggi.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.piece.Byeong;
import janggi.piece.Cha;
import janggi.piece.Jol;
import janggi.piece.Nation;
import janggi.piece.Piece;
import janggi.piece.PieceProfile;
import janggi.piece.Po;
import janggi.position.Position;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class BoardTest {

    @DisplayName("장기판은 기물과 위치를 가진다.")
    @Test
    void board() {
        //given
        PieceInitializer pieceInitializer = new PieceInitializer();
        List<Piece> pieces = pieceInitializer.generate();

        //when
        Board board = new Board(pieces);

        //then
        assertThat(board.getJanggiBoard()).hasSize(32);
    }

    @Nested
    @DisplayName("포를 수직 또는 수평으로 이동시킬 수 있다.")
    class PoMoving {

        @DisplayName("포는 수직으로 이동할 떄 포를 제외한 장애물이 앞에 있는 경우 장애물을 넘어서 이동할 수 있다.")
        @Test
        void poMovingVertical() {
            //given
            List<Piece> pieces = List.of(
                    new Po(new PieceProfile("포", Nation.HAN), new Position(3, 2)),
                    new Byeong(new PieceProfile("병", Nation.HAN), new Position(4, 2))
            );

            Board board = new Board(pieces);

            Position presentPosition = new Position(3, 2);
            Position futurePosition = new Position(5, 2);

            //when
            board.updateBoard(presentPosition, futurePosition);

            //then
            Piece actual = board.getJanggiBoard().get(futurePosition);
            assertThat(actual).isEqualTo(new Po(new PieceProfile("포", Nation.HAN),
                    new Position(5, 2)));
        }

        @DisplayName("포는 수평으로 이동할 때 포를 제외한 장애물이 앞에 있는 경우 장애물을 넘어서 이동할 수 있다.")
        @Test
        void poMovingHorizontal() {
            //given
            List<Piece> pieces = List.of(
                    new Po(new PieceProfile("포", Nation.HAN), new Position(3, 2)),
                    new Byeong(new PieceProfile("병", Nation.HAN), new Position(4, 2))
            );

            Board board = new Board(pieces);

            Position presentPosition = new Position(3, 2);
            Position futurePosition = new Position(5, 2);

            //when
            board.updateBoard(presentPosition, futurePosition);

            //then
            Piece actual = board.getJanggiBoard().get(futurePosition);
            assertThat(actual).isEqualTo(new Po(new PieceProfile("포", Nation.HAN),
                    new Position(5, 2)));
        }
    }

    @Nested
    @DisplayName("포는 수직 또는 수평으로 이동하지 못하면 예외를 던진다.")
    class PoMovingException {

        @DisplayName("포를 수직으로 이동 시킬 때 앞에 포를 제외한 어떠한 장애물도 없다면 예외를 던진다.")
        @Test
        void poNotMovingVerticalInFrontNothing() {
            //given
            List<Piece> pieces = List.of(
                    new Po(new PieceProfile("포", Nation.HAN), new Position(3, 2))
            );

            Board board = new Board(pieces);

            Position presentPosition = new Position(3, 2);
            Position futurePosition = new Position(7, 2);

            //when //then
            assertThatThrownBy(() -> board.updateBoard(presentPosition, futurePosition))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageStartingWith("[ERROR]");
        }

        @DisplayName("포를 수평으로 이동 시킬 때 앞에 포를 제외한 어떠한 장애물도 없다면 예외를 던진다.")
        @Test
        void poNotMovingHorizontalInFrontNothing() {
            //given
            List<Piece> pieces = List.of(
                    new Po(new PieceProfile("포", Nation.HAN), new Position(3, 2))
            );

            Board board = new Board(pieces);

            Position presentPosition = new Position(3, 2);
            Position futurePosition = new Position(3, 7);

            //when //then
            assertThatThrownBy(() -> board.updateBoard(presentPosition, futurePosition))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageStartingWith("[ERROR]");
        }

        @DisplayName("포를 수직으로 이동 시킬 때 앞에 포가 존재한다면 예외를 던진다.")
        @Test
        void notPoMovingVerticalInFrontPo() {

            //given
            List<Piece> pieces = List.of(
                    new Po(new PieceProfile("포", Nation.HAN), new Position(3, 2)),
                    new Po(new PieceProfile("포", Nation.HAN), new Position(4, 2))
            );

            Board board = new Board(pieces);

            Position presentPosition = new Position(3, 2);
            Position futurePosition = new Position(5, 2);

            //when //then
            assertThatThrownBy(() -> board.updateBoard(presentPosition, futurePosition))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageStartingWith("[ERROR]");
        }

        @DisplayName("포를 수평으로 이동 시킬 때 앞에 포가 존재한다면 예외를 던진다.")
        @Test
        void notPoMovingHorizontalInFrontPo() {
            //given
            List<Piece> pieces = List.of(
                    new Po(new PieceProfile("포", Nation.HAN), new Position(2, 3)),
                    new Po(new PieceProfile("포", Nation.HAN), new Position(2, 4))
            );

            Board board = new Board(pieces);

            Position presentPosition = new Position(2, 3);
            Position futurePosition = new Position(2, 5);

            //when //then
            assertThatThrownBy(() -> board.updateBoard(presentPosition, futurePosition))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageStartingWith("[ERROR]");
        }

        @DisplayName("포를 수평으로 이동 시킬 때 앞에 기물이 2개 이상 존재한다면 예외를 던진다.")
        @Test
        void notFoMovingHorizontalInFrontTwoPiece() {
            List<Piece> pieces = List.of(
                    new Po(new PieceProfile("포", Nation.HAN), new Position(3, 2)),
                    new Jol(new PieceProfile("졸", Nation.HAN), new Position(3, 3)),
                    new Byeong(new PieceProfile("병", Nation.HAN), new Position(3, 4))
            );

            Board board = new Board(pieces);

            Position presentPosition = new Position(3, 2);
            Position futurePosition = new Position(3, 5);

            //when //then
            assertThatThrownBy(() -> board.updateBoard(presentPosition, futurePosition))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageStartingWith("[ERROR]");
        }

        @DisplayName("포를 수직으로 이동 시킬 때 앞에 기물이 2개 이상 존재한다면 예외를 던진다.")
        @Test
        void notFoMovingVerticalInFrontTwoPiece() {
            List<Piece> pieces = List.of(
                    new Po(new PieceProfile("포", Nation.HAN), new Position(2, 3)),
                    new Jol(new PieceProfile("졸", Nation.HAN), new Position(3, 3)),
                    new Byeong(new PieceProfile("병", Nation.HAN), new Position(4, 3))
            );

            Board board = new Board(pieces);

            Position presentPosition = new Position(2, 3);
            Position futurePosition = new Position(5, 3);

            //when //then
            assertThatThrownBy(() -> board.updateBoard(presentPosition, futurePosition))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageStartingWith("[ERROR]");
        }
    }

    @DisplayName("장기판의 기물을 옮길 수 있다.")
    @Test
    void updateBoard() {
        //given
        List<Piece> pieces = List.of(
                new Byeong(new PieceProfile("병", Nation.HAN), new Position(3, 2))
        );

        Board board = new Board(pieces);

        Position presentPosition = new Position(3, 2);
        Position futurePosition = new Position(4, 2);

        //when
        board.updateBoard(presentPosition, futurePosition);

        //then
        Piece actual = board.getJanggiBoard().get(futurePosition);
        assertThat(actual).isEqualTo(new Byeong(new PieceProfile("병", Nation.HAN),
                new Position(4, 2)));
    }


    @DisplayName("기물이 이동하는 경로를 검사한다.")
    @Test
    void checkObstacle() {
        //given
        List<Piece> pieces = List.of(
                new Cha(new PieceProfile("차", Nation.HAN), new Position(4, 2)),
                new Byeong(new PieceProfile("병", Nation.HAN), new Position(7, 2))
        );

        Position presentPosition = new Position(4, 2);
        Position futurePosition = new Position(8, 2);

        Board board = new Board(pieces);

        //when //then
        assertThatThrownBy(() -> board.checkObstacle(presentPosition, futurePosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }
}
