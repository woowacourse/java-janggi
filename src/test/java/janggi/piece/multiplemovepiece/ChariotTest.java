package janggi.piece.multiplemovepiece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    @DisplayName("제공된 위치를 기준으로 이동할 수 없다면 예외를 던진다.")
    @Test
    void nonCanMoveBy() {
        //given
        final Chariot chariot = new Chariot(Team.HAN);

        //when
        final Position currnetPosition = new Position(0, 0);
        final Position targetPosition = new Position(1, 1);

        //then
        assertThatThrownBy(() -> chariot.canMoveBy(currnetPosition, targetPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("차는 움직임을 제공된 위치를 기준으로 가로, 세로 방향으로 무제한 이동할 수 있다면 예외를 던지지 않는다.")
    @ParameterizedTest
    @MethodSource("chariotCanMoveByPositionProvider")
    void canMoveBy(final Position currentPosition, final Position targetPosition) {
        //given
        final Chariot chariot = new Chariot(Team.HAN);

        //when //then
        assertThatCode(() -> chariot.canMoveBy(currentPosition, targetPosition))
                .doesNotThrowAnyException();
    }

    private static Stream<Arguments> chariotCanMoveByPositionProvider() {
        return Stream.of(
                Arguments.of(new Position(0, 0), new Position(0, 1)),
                Arguments.of(new Position(0, 0), new Position(1, 0)));
    }

    @Nested
    @DisplayName("차는 제공된 위치에서 목적지까지의 경로를 계산하여 반환한다.")
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

        @DisplayName("차의 이동 경로에 장애물이 있다면 예외를 던진다.")
        @Test
        void hasObstacle() {
            //given
            final Chariot chariot = new Chariot(Team.HAN);

            final Map<Position, Piece> board = Map.of(
                    new Position(6, 5), new Soldier(Team.HAN)
            );

            final Position currentPosition = new Position(5, 5);
            final Position targetPosition = new Position(7, 5);

            //when //then
            assertThatThrownBy(() -> chariot.checkObstacle(currentPosition, targetPosition, board))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageStartingWith("[ERROR]");
        }

        @DisplayName("차의 이동 경로에 장애물이 없다면 예외를 던지지 않는다.")
        @Test
        void nonObstacle() {
            //given
            final Chariot chariot = new Chariot(Team.HAN);

            final Map<Position, Piece> board = Map.of(
                    new Position(7, 5), new Soldier(Team.HAN)
            );

            final Position currentPosition = new Position(5, 5);
            final Position targetPosition = new Position(6, 5);

            //when //then
            assertThatCode(() -> chariot.checkObstacle(currentPosition, targetPosition, board))
                    .doesNotThrowAnyException();
        }
    }
}
