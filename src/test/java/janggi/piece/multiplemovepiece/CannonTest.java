package janggi.piece.multiplemovepiece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.board.Board;
import janggi.piece.Piece;
import janggi.piece.Team;
import janggi.piece.onemovepiece.Pawn;
import janggi.piece.onemovepiece.Soldier;
import janggi.position.Position;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CannonTest {

    @DisplayName("포는 자신의 팀과 위치를 가진다.")
    @Test
    void cannonBoardPosition() {
        //given
        final Position position = new Position(4, 5);

        //when
        final Cannon cannon = new Cannon(Team.HAN, position);

        //then
        assertThat(cannon.getBoardPosition()).isEqualTo(new Position(4, 5));
    }

    @DisplayName("자신의 위치를 기준으로 이동할 수 없다면 false를 반환한다.")
    @Test
    void nonIsMove() {
        //given
        final Cannon cannon = new Cannon(Team.HAN, new Position(0, 0));

        //when //then
        assertThatThrownBy(() -> cannon.isMove(new Position(1, 1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("포는 움직임을 자신의 위치를 기준으로 가로, 세로 방향으로 무제한 이동할 수 있다면 true를 반환한다.")
    @ParameterizedTest
    @MethodSource("cannonIsMovePositionProvider")
    void isMove(final Position position) {
        //given
        final Cannon cannon = new Cannon(Team.HAN, new Position(0, 0));

        //when
        final boolean actual = cannon.isMove(position);

        //then
        assertThat(actual).isTrue();
    }

    @Nested
    @DisplayName("포는 자신의 위치에서 목적지까지의 경로를 계산하여 반환한다.")
    class makeRoute {

        @DisplayName("수직으로 아래로 이동할 때 경로를 계산한다.")
        @Test
        void makeRouteVerticalDown() {
            final Cannon cannon = new Cannon(Team.HAN, new Position(0, 0));
            final Position futurePosition = new Position(5, 0);

            final List<Position> actual = cannon.makeRoute(futurePosition);

            assertThat(actual).containsExactly(
                    new Position(1, 0),
                    new Position(2, 0),
                    new Position(3, 0),
                    new Position(4, 0)
            );
        }

        @DisplayName("수직으로 위로 이동할 때 경로를 계산한다.")
        @Test
        void makeRouteVerticalUp() {
            final Cannon cannon = new Cannon(Team.HAN, new Position(5, 0));
            final Position futurePosition = new Position(0, 0);

            final List<Position> actual = cannon.makeRoute(futurePosition);

            assertThat(actual).containsExactly(
                    new Position(4, 0),
                    new Position(3, 0),
                    new Position(2, 0),
                    new Position(1, 0)
            );
        }

        @DisplayName("수평으로 오른쪽으로 이동할 때 경로를 계산한다.")
        @Test
        void makeRouteHorizontalRight() {
            final Cannon cannon = new Cannon(Team.HAN, new Position(0, 0));
            final Position futurePosition = new Position(0, 5);

            final List<Position> actual = cannon.makeRoute(futurePosition);

            assertThat(actual).containsExactly(
                    new Position(0, 1),
                    new Position(0, 2),
                    new Position(0, 3),
                    new Position(0, 4)
            );
        }

        @DisplayName("수평으로 왼쪽으로 이동할 때 경로를 계산한다.")
        @Test
        void makeRouteHorizontalLeft() {
            final Cannon cannon = new Cannon(Team.HAN, new Position(0, 5));
            final Position futurePosition = new Position(0, 0);

            final List<Position> actual = cannon.makeRoute(futurePosition);

            assertThat(actual).containsExactly(
                    new Position(0, 4),
                    new Position(0, 3),
                    new Position(0, 2),
                    new Position(0, 1)
            );
        }

    }

    @Nested
    @DisplayName("포를 수직 또는 수평으로 이동시킬 수 있다.")
    class CannonMoving {
        @DisplayName("포는 수직으로 이동할 떄 포를 제외한 장애물이 앞에 있는 경우 장애물을 넘어서 이동할 수 있다.")
        @Test
        void cannonMovingVertical() {
            //given
            final List<Piece> pieces = List.of(
                    new Cannon(Team.HAN, new Position(3, 2)),
                    new Soldier(Team.HAN, new Position(4, 2))
            );

            final Board board = new Board(pieces);

            final Position presentPosition = new Position(3, 2);
            final Position futurePosition = new Position(5, 2);

            //when
            board.pieceMove(presentPosition, futurePosition);

            //then
            final Piece actual = board.getJanggiBoard().get(futurePosition);
            assertThat(actual).isEqualTo(new Cannon(Team.HAN,
                    new Position(5, 2)));
        }

        @DisplayName("포는 수평으로 이동할 때 포를 제외한 장애물이 앞에 있는 경우 장애물을 넘어서 이동할 수 있다.")
        @Test
        void cannonMovingHorizontal() {
            //given
            final List<Piece> pieces = List.of(
                    new Cannon(Team.HAN, new Position(3, 2)),
                    new Soldier(Team.HAN, new Position(4, 2))
            );

            final Board board = new Board(pieces);

            final Position presentPosition = new Position(3, 2);
            final Position futurePosition = new Position(5, 2);

            //when
            board.pieceMove(presentPosition, futurePosition);

            //then
            final Piece actual = board.getJanggiBoard().get(futurePosition);
            assertThat(actual).isEqualTo(new Cannon(Team.HAN,
                    new Position(5, 2)));
        }

    }

    @Nested
    @DisplayName("포는 수직 또는 수평으로 이동하지 못하면 예외를 던진다.")
    class CannonMovingException {
        @DisplayName("포를 수직으로 이동 시킬 때 앞에 포를 제외한 어떠한 장애물도 없다면 예외를 던진다.")
        @Test
        void cannonNotMovingVerticalInFrontNothing() {
            //given
            final List<Piece> pieces = List.of(
                    new Cannon(Team.HAN, new Position(3, 2))
            );

            final Board board = new Board(pieces);

            final Position presentPosition = new Position(3, 2);
            final Position futurePosition = new Position(7, 2);

            //when //then
            assertThatThrownBy(() -> board.pieceMove(presentPosition, futurePosition))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageStartingWith("[ERROR]");
        }

        @DisplayName("포를 수평으로 이동 시킬 때 앞에 포를 제외한 어떠한 장애물도 없다면 예외를 던진다.")
        @Test
        void cannonNotMovingHorizontalInFrontNothing() {
            //given
            final List<Piece> pieces = List.of(
                    new Cannon(Team.HAN, new Position(3, 2))
            );

            final Board board = new Board(pieces);

            final Position presentPosition = new Position(3, 2);
            final Position futurePosition = new Position(3, 7);

            //when //then
            assertThatThrownBy(() -> board.pieceMove(presentPosition, futurePosition))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageStartingWith("[ERROR]");
        }

        @DisplayName("포를 수직으로 이동 시킬 때 앞에 포가 존재한다면 예외를 던진다.")
        @Test
        void notCannonMovingVerticalInFrontPo() {
            //given
            final List<Piece> pieces = List.of(
                    new Cannon(Team.HAN, new Position(3, 2)),
                    new Cannon(Team.HAN, new Position(4, 2))
            );

            final Board board = new Board(pieces);

            final Position presentPosition = new Position(3, 2);
            final Position futurePosition = new Position(5, 2);

            //when //then
            assertThatThrownBy(() -> board.pieceMove(presentPosition, futurePosition))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageStartingWith("[ERROR]");
        }

        @DisplayName("포를 수평으로 이동 시킬 때 앞에 포가 존재한다면 예외를 던진다.")
        @Test
        void notCannonMovingHorizontalInFrontPo() {
            //given
            final List<Piece> pieces = List.of(
                    new Cannon(Team.HAN, new Position(2, 3)),
                    new Cannon(Team.HAN, new Position(2, 4))
            );

            final Board board = new Board(pieces);

            final Position presentPosition = new Position(2, 3);
            final Position futurePosition = new Position(2, 5);

            //when //then
            assertThatThrownBy(() -> board.pieceMove(presentPosition, futurePosition))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageStartingWith("[ERROR]");
        }

        @DisplayName("포를 수평으로 이동 시킬 때 앞에 기물이 2개 이상 존재한다면 예외를 던진다.")
        @Test
        void notCannonMovingHorizontalInFrontTwoPiece() {
            final List<Piece> pieces = List.of(
                    new Cannon(Team.HAN, new Position(3, 2)),
                    new Pawn(Team.HAN, new Position(3, 3)),
                    new Soldier(Team.HAN, new Position(3, 4))
            );

            final Board board = new Board(pieces);

            final Position presentPosition = new Position(3, 2);
            final Position futurePosition = new Position(3, 5);

            //when //then
            assertThatThrownBy(() -> board.pieceMove(presentPosition, futurePosition))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageStartingWith("[ERROR]");
        }

        @DisplayName("포를 수직으로 이동 시킬 때 앞에 기물이 2개 이상 존재한다면 예외를 던진다.")
        @Test
        void notCannonMovingVerticalInFrontTwoPiece() {
            final List<Piece> pieces = List.of(
                    new Cannon(Team.HAN, new Position(2, 3)),
                    new Pawn(Team.HAN, new Position(3, 3)),
                    new Soldier(Team.HAN, new Position(4, 3))
            );

            final Board board = new Board(pieces);

            final Position presentPosition = new Position(2, 3);
            final Position futurePosition = new Position(5, 3);

            //when //then
            assertThatThrownBy(() -> board.pieceMove(presentPosition, futurePosition))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageStartingWith("[ERROR]");
        }
    }

    private static Stream<Arguments> cannonIsMovePositionProvider() {
        return Stream.of(
                Arguments.of(new Position(0, 1)),
                Arguments.of(new Position(1, 0))
        );
    }

}
