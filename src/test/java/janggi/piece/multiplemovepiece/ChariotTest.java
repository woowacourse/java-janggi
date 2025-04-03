package janggi.piece.multiplemovepiece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.board.palace.Palace;
import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.piece.Team;
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

class ChariotTest {

    @DisplayName("차는 팀과 타입을 가진다.")
    @Test
    void chariotBoardPosition() {
        //given //when
        final Chariot chariot = new Chariot(Team.HAN);

        //then
        assertThat(chariot.getPieceType()).isEqualTo(PieceType.CHARIOT);
        assertThat(chariot.getTeam()).isEqualTo(Team.HAN);
    }

    @DisplayName("궁성의 영역이 아닐 때 목적지로 이동할 수 없다면(대각선으로 이동하는 경우) 예외를 던진다.")
    @Test
    void nonCanMoveBy() {
        //given
        final Chariot chariot = new Chariot(Team.HAN);

        final Palace palace = Palace.AREA;

        //when
        final Position currnetPosition = new Position(0, 0);
        final Position targetPosition = new Position(1, 1);

        //then
        assertThatThrownBy(() -> chariot.canMoveBy(currnetPosition, targetPosition, palace))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("궁성이 아닐 때 제공된 위치를 기준으로 가로, 세로 방향으로 무제한 이동할 수 있다면 예외를 던지지 않는다.")
    @ParameterizedTest
    @MethodSource("chariotCanMoveByPositionProvider")
    void canMoveBy(final Position currentPosition, final Position targetPosition, final Palace palace) {
        //given
        final Chariot chariot = new Chariot(Team.HAN);

        //when //then
        assertThatCode(() -> chariot.canMoveBy(currentPosition, targetPosition, palace))
                .doesNotThrowAnyException();
    }

    private static Stream<Arguments> chariotCanMoveByPositionProvider() {
        final Palace palace = Palace.AREA;

        return Stream.of(
                Arguments.of(new Position(0, 0), new Position(0, 1), palace),
                Arguments.of(new Position(0, 0), new Position(1, 0), palace)
        );
    }

    @Nested
    @DisplayName("제공된 위치에서 목적지까지의 경로를 계산하여 반환한다.")
    class makeRoute {

        @DisplayName("수직으로 아래로 이동할 때 경로를 계산한다.")
        @Test
        void makeRouteVerticalDown() {
            final Chariot chariot = new Chariot(Team.HAN);
            final Position currentPosition = new Position(0, 0);
            final Position targetPosition = new Position(5, 0);

            final List<Position> actual = chariot.makeRoute(currentPosition, targetPosition);

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
            final Chariot chariot = new Chariot(Team.HAN);

            final Position currentPosition = new Position(5, 0);
            final Position targetPosition = new Position(0, 0);

            final List<Position> actual = chariot.makeRoute(currentPosition, targetPosition);

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
            final Chariot chariot = new Chariot(Team.HAN);

            final Position currentPosition = new Position(0, 0);
            final Position targetPosition = new Position(0, 5);

            final List<Position> actual = chariot.makeRoute(currentPosition, targetPosition);

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
            final Chariot chariot = new Chariot(Team.HAN);

            final Position currentPosition = new Position(0, 5);
            final Position targetPosition = new Position(0, 0);

            final List<Position> actual = chariot.makeRoute(currentPosition, targetPosition);

            assertThat(actual).containsExactly(
                    new Position(0, 4),
                    new Position(0, 3),
                    new Position(0, 2),
                    new Position(0, 1)
            );
        }

        @DisplayName("이동 경로에 장애물이 있다면 예외를 던진다.")
        @Test
        void hasObstacle() {
            //given
            final Chariot chariot = new Chariot(Team.HAN);

            final Map<Position, Piece> board = Map.of(
                    new Position(6, 5), new Soldier()
            );

            final Position currentPosition = new Position(5, 5);
            final Position targetPosition = new Position(7, 5);

            //when //then
            assertThatThrownBy(() -> chariot.checkObstacle(currentPosition, targetPosition, board))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageStartingWith("[ERROR]");
        }

        @DisplayName("이동 경로에 장애물이 없다면 예외를 던지지 않는다.")
        @Test
        void nonObstacle() {
            //given
            final Chariot chariot = new Chariot(Team.HAN);

            final Map<Position, Piece> board = Map.of(
                    new Position(7, 5), new Soldier()
            );

            final Position currentPosition = new Position(5, 5);
            final Position targetPosition = new Position(6, 5);

            //when //then
            assertThatCode(() -> chariot.checkObstacle(currentPosition, targetPosition, board))
                    .doesNotThrowAnyException();
        }
    }

    @DisplayName("궁성의 영역에서 직선으로 좌우전후 궁성의 간선을 타고 궁성의 영역을 벗어나지 않는 곳에서 무제한 이동할 수 있다.")
    @ParameterizedTest
    @MethodSource("chariotPalaceMovePositionProvider")
    void palaceMove(final Position currentPosition, final Position targetPosition, final Palace palace) {
        //given
        final Chariot chariot = new Chariot(Team.CHU);

        //when //then
        assertThatCode(() -> chariot.canMoveBy(currentPosition, targetPosition, palace))
                .doesNotThrowAnyException();
    }

    private static Stream<Arguments> chariotPalaceMovePositionProvider() {
        final Palace palace = Palace.AREA;

        return Stream.of(
                Arguments.of(new Position(9, 5), new Position(8, 5), palace),
                Arguments.of(new Position(9, 5), new Position(7, 5), palace),
                Arguments.of(new Position(9, 5), new Position(9, 4), palace),
                Arguments.of(new Position(9, 5), new Position(9, 3), palace),
                Arguments.of(new Position(9, 5), new Position(7, 4), palace),
                Arguments.of(new Position(9, 5), new Position(7, 3), palace)
        );
    }

    @DisplayName("궁성의 영역 내에서도 장애물을 넘을 수 없다.")
    @ParameterizedTest
    @MethodSource("chariotNonMoveObstacleInPalacePositionProvider")
    void nonMoveObstacleInPalace(final Position currentPosition, final Position targetPosition, final Palace palace) {
        //given
        final Chariot chariot = new Chariot(Team.CHU);

        final Map<Position, Piece> janggiBoard = Map.of(
                new Position(8, 4), new Soldier(),
                new Position(8, 5), new Soldier(),
                new Position(9, 4), new Soldier()
        );

        //when //then
        assertThatThrownBy(() -> chariot.moveTo(currentPosition, targetPosition, janggiBoard, palace))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 차를 이동할 수 없습니다. 차는 다른 기물을 넘어 다닐 수 없습니다.");
    }

    private static Stream<Arguments> chariotNonMoveObstacleInPalacePositionProvider() {
        final Palace palace = Palace.AREA;

        return Stream.of(
                Arguments.of(new Position(9, 5), new Position(7, 3), palace),
                Arguments.of(new Position(9, 5), new Position(7, 5), palace),
                Arguments.of(new Position(9, 5), new Position(9, 3), palace)
        );
    }

}
