package janggi.piece.multiplemovepiece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.board.Board;
import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.piece.Team;
import janggi.piece.onemovepiece.Pawn;
import janggi.piece.onemovepiece.Soldier;
import janggi.position.Position;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CannonTest {

    @DisplayName("포는 팀과 타입을 가진다.")
    @Test
    void cannonBoardPosition() {
        //given //when
        final Cannon cannon = new Cannon(Team.HAN);

        //then
        assertThat(cannon.getPieceType()).isEqualTo(PieceType.CANNON);
        assertThat(cannon.getTeam()).isEqualTo(Team.HAN);
    }

    @DisplayName("제공된 위치를 기준으로 이동할 수 없다면 예외를 던진다.")
    @Test
    void nonCanMoveBy() {
        //given
        final Cannon cannon = new Cannon(Team.HAN);

        //when
        final Position currentPosition = new Position(0, 0);
        final Position targetPosition = new Position(1, 1);

        //then
        assertThatThrownBy(() -> cannon.canMoveBy(currentPosition, targetPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("포는 움직임을 제공된 위치를 기준으로 가로, 세로 방향으로 무제한 이동할 수 있다면 예외를 던지지 않는다.")
    @ParameterizedTest
    @MethodSource("cannonCanMoveByPositionProvider")
    void canMoveBy(final Position currentPosition, final Position position) {
        //given
        final Cannon cannon = new Cannon(Team.HAN);

        //when //then
        assertThatCode(() -> cannon.canMoveBy(currentPosition, position))
                .doesNotThrowAnyException();
    }

    private static Stream<Arguments> cannonCanMoveByPositionProvider() {
        return Stream.of(
                Arguments.of(new Position(0, 0), new Position(0, 1)),
                Arguments.of(new Position(0, 0), new Position(1, 0))
        );
    }

    @Nested
    @DisplayName("포는 제공된 위치에서 목적지까지의 경로를 계산하여 반환한다.")
    class makeRoute {

        @DisplayName("수직으로 아래로 이동할 때 경로를 계산한다.")
        @Test
        void makeRouteVerticalDown() {
            final Cannon cannon = new Cannon(Team.HAN);

            final Position currentPosition = new Position(0, 0);
            final Position targetPosition = new Position(5, 0);

            final List<Position> actual = cannon.makeRoute(currentPosition, targetPosition);

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
            final Cannon cannon = new Cannon(Team.HAN);

            final Position currentPosition = new Position(5, 0);
            final Position targetPosition = new Position(0, 0);

            final List<Position> actual = cannon.makeRoute(currentPosition, targetPosition);

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
            final Cannon cannon = new Cannon(Team.HAN);

            final Position currentPosition = new Position(0, 0);
            final Position targetPosition = new Position(0, 5);

            final List<Position> actual = cannon.makeRoute(currentPosition, targetPosition);

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
            final Cannon cannon = new Cannon(Team.HAN);

            final Position currentPosition = new Position(0, 5);
            final Position targetPosition = new Position(0, 0);

            final List<Position> actual = cannon.makeRoute(currentPosition, targetPosition);

            assertThat(actual).containsExactly(
                    new Position(0, 4),
                    new Position(0, 3),
                    new Position(0, 2),
                    new Position(0, 1)
            );
        }
    }

    @Nested
    @DisplayName("포가 수직 또는 수평으로 이동할 수 있는지 확인할 수 있다.")
    class CannonMoving {
        @DisplayName("수평으로 왼쪽으로 이동할 때 경로를 계산한다.")
        @Test
        void makeRouteHorizontalLeft() {
            final Cannon cannon = new Cannon(Team.HAN);

            final Position currentPosition = new Position(0, 5);
            final Position targetPosition = new Position(0, 0);

            final List<Position> actual = cannon.makeRoute(currentPosition, targetPosition);

            assertThat(actual).containsExactly(
                    new Position(0, 4),
                    new Position(0, 3),
                    new Position(0, 2),
                    new Position(0, 1)
            );
        }

        @DisplayName("포는 수직으로 이동할 때 포를 제외한 장애물이 앞에 있으면 이를 넘어서 이동할 수 있다.")
        @Test
        void cannonMovingVertical() {
            //given
            final Cannon cannon = new Cannon(Team.HAN);

            final Map<Position, Piece> janggiBoard = Map.of(
                    new Position(3, 2), cannon,
                    new Position(4, 2), new Soldier(Team.HAN)
            );

            final Position currentPosition = new Position(3, 2);
            final Position targetPosition = new Position(5, 2);

            //when //then
            assertThatCode(() -> cannon.checkObstacle(currentPosition, targetPosition, janggiBoard))
                    .doesNotThrowAnyException();
        }

        @DisplayName("포는 수평으로 이동할 때 포를 제외한 장애물이 앞에 있으면 이를 넘어서 이동할 수 있다.")
        @Test
        void cannonMovingHorizontal() {
            //given
            final Cannon cannon = new Cannon(Team.HAN);

            final Map<Position, Piece> janggiBoard = Map.of(
                    new Position(3, 2), cannon,
                    new Position(3, 4), new Soldier(Team.HAN)
            );

            final Position currentPosition = new Position(3, 2);
            final Position targetPosition = new Position(3, 5);

            //when //then
            assertThatCode(() -> cannon.checkObstacle(currentPosition, targetPosition, janggiBoard))
                    .doesNotThrowAnyException();
        }
    }

    @Nested
    @DisplayName("포가 수직 또는 수평으로 이동할 수 없는 경우 예외를 던진다.")
    class CannonMovingException {

        @DisplayName("포를 수직으로 이동할 때 앞에 장애물이 없다면 예외를 던진다.")
        @Test
        void cannonNotMovingVerticalInFrontNothing() {
            //given
            final Board board = new Board();
            board.deployPiece(new Position(3, 2), new Cannon(Team.HAN));

            final Position currentPosition = new Position(3, 2);
            final Position targetPosition = new Position(7, 2);

            //when //then
            assertThatThrownBy(() -> board.pieceMove(currentPosition, targetPosition))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageStartingWith("[ERROR]");
        }

        @DisplayName("포를 수평으로 이동할 때 앞에 장애물이 없다면 예외를 던진다.")
        @Test
        void cannonNotMovingHorizontalInFrontNothing() {
            //given
            final Board board = new Board();
            board.deployPiece(new Position(3, 2), new Cannon(Team.HAN));

            final Position currentPosition = new Position(3, 2);
            final Position targetPosition = new Position(3, 7);

            //when //then
            assertThatThrownBy(() -> board.pieceMove(currentPosition, targetPosition))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageStartingWith("[ERROR]");
        }

        @DisplayName("포를 수직으로 이동할 때 앞에 다른 포가 있다면 예외를 던진다.")
        @Test
        void notCannonMovingVerticalInFrontPo() {
            //given
            final Board board = new Board();
            board.deployPiece(new Position(3, 2), new Cannon(Team.HAN));
            board.deployPiece(new Position(4, 2), new Cannon(Team.HAN));

            final Position currentPosition = new Position(3, 2);
            final Position targetPosition = new Position(5, 2);

            //when //then
            assertThatThrownBy(() -> board.pieceMove(currentPosition, targetPosition))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageStartingWith("[ERROR]");
        }

        @DisplayName("포를 수평으로 이동할 때 앞에 다른 포가 있다면 예외를 던진다.")
        @Test
        void notCannonMovingHorizontalInFrontPo() {
            //given
            final Board board = new Board();
            board.deployPiece(new Position(2, 3), new Cannon(Team.HAN));
            board.deployPiece(new Position(2, 4), new Cannon(Team.HAN));

            final Position currentPosition = new Position(2, 3);
            final Position targetPosition = new Position(2, 5);

            //when //then
            assertThatThrownBy(() -> board.pieceMove(currentPosition, targetPosition))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageStartingWith("[ERROR]");
        }

        @DisplayName("포를 이동할 때 경로에 기물이 2개 이상 존재하면 예외를 던진다.")
        @Test
        void notCannonMovingHorizontalInFrontTwoPiece() {
            final Board board = new Board();
            board.deployPiece(new Position(3, 2), new Cannon(Team.HAN));
            board.deployPiece(new Position(3, 3), new Pawn(Team.HAN));
            board.deployPiece(new Position(3, 4), new Soldier(Team.HAN));

            final Position currentPosition = new Position(3, 2);
            final Position targetPosition = new Position(3, 5);

            //when //then
            assertThatThrownBy(() -> board.pieceMove(currentPosition, targetPosition))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageStartingWith("[ERROR]");
        }
    }

}
