package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.board.Board;
import janggi.position.Position;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PoTest {

    @DisplayName("포는 위치 정보를 가진다,")
    @Test
    void poBoardPosition() {
        //given
        Position position = new Position(4, 5);

        //when
        Po po = new Po(new PieceProfile("포", Nation.HAN), position);

        //then
        assertThat(po.getBoardPosition()).isEqualTo(new Position(4, 5));
    }

    @DisplayName("자신의 위치를 기준으로 이동할 수 없다면 false를 반환한다.")
    @Test
    void nonIsMove() {
        //given
        Po po = new Po(new PieceProfile("포", Nation.HAN), new Position(0, 0));

        //when //then
        assertThatThrownBy(() -> po.isMove(new Position(1, 1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("포는 움직임을 자신의 위치를 기준으로 가로, 세로 방향으로 무제한 이동할 수 있다면 true를 반환한다.")
    @ParameterizedTest
    @MethodSource("poIsMovePositionProvider")
    void isMove(Position position) {
        //given
        Po po = new Po(new PieceProfile("포", Nation.HAN), new Position(0, 0));

        //when
        boolean actual = po.isMove(position);

        //then
        assertThat(actual).isTrue();
    }

    @DisplayName("포는 자신의 위치에서 목적지까지의 경로를 계산하여 반환한다.")
    @Test
    void makeRoute() {
        //given
        Po po = new Po(new PieceProfile("포", Nation.HAN), new Position(0, 0));
        Position futurePosition = new Position(5, 0);

        //when
        List<Position> actual = po.makeRoute(futurePosition);

        //then
        assertThat(actual).containsExactly(
                new Position(1, 0),
                new Position(2, 0),
                new Position(3, 0),
                new Position(4, 0),
                new Position(5, 0)
        );
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
            board.pieceMove(presentPosition, futurePosition);

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
            board.pieceMove(presentPosition, futurePosition);

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
            assertThatThrownBy(() -> board.pieceMove(presentPosition, futurePosition))
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
            assertThatThrownBy(() -> board.pieceMove(presentPosition, futurePosition))
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
            assertThatThrownBy(() -> board.pieceMove(presentPosition, futurePosition))
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
            assertThatThrownBy(() -> board.pieceMove(presentPosition, futurePosition))
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
            assertThatThrownBy(() -> board.pieceMove(presentPosition, futurePosition))
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
            assertThatThrownBy(() -> board.pieceMove(presentPosition, futurePosition))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageStartingWith("[ERROR]");
        }
    }

    private static Stream<Arguments> poIsMovePositionProvider() {
        return Stream.of(
                Arguments.of(new Position(0, 1)),
                Arguments.of(new Position(1, 0))
        );
    }

}
