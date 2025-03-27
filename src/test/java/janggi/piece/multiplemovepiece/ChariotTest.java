package janggi.piece.multiplemovepiece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.piece.Piece;
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

    @DisplayName("차는 자신의 팀과 위치를 가진다.")
    @Test
    void chariotBoardPosition() {
        //given
        final Position position = new Position(4, 5);

        //when
        final Chariot chariot = new Chariot(Team.HAN, position);

        //then
        assertThat(chariot.getBoardPosition()).isEqualTo(new Position(4, 5));
    }

    @DisplayName("자신의 위치를 기준으로 이동할 수 없다면 예외를 던진다.")
    @Test
    void nonCanMoveBy() {
        //given
        final Chariot chariot = new Chariot(Team.HAN, new Position(0, 0));

        //when //then
        assertThatThrownBy(() -> chariot.canMoveBy(new Position(1, 1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("차는 움직임을 자신의 위치를 기준으로 가로, 세로 방향으로 무제한 이동할 수 있다면 예외를 던지지 않는다.")
    @ParameterizedTest
    @MethodSource("chariotCanMoveByPositionProvider")
    void canMoveBy(final Position position) {
        //given
        final Chariot chariot = new Chariot(Team.HAN, new Position(0, 0));

        //when //then
        assertThatCode(() -> chariot.canMoveBy(position))
                .doesNotThrowAnyException();
    }

    private static Stream<Arguments> chariotCanMoveByPositionProvider() {
        return Stream.of(
                Arguments.of(new Position(0, 1)),
                Arguments.of(new Position(1, 0)));
    }

    @Nested
    @DisplayName("차는 자신의 위치에서 목적지까지의 경로를 계산하여 반환한다.")
    class makeRoute {

        @DisplayName("수직으로 아래로 이동할 때 경로를 계산한다.")
        @Test
        void makeRouteVerticalDown() {
            final Chariot chariot = new Chariot(Team.HAN, new Position(0, 0));
            final Position futurePosition = new Position(5, 0);

            final List<Position> actual = chariot.makeRoute(futurePosition);

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
            final Chariot chariot = new Chariot(Team.HAN, new Position(5, 0));
            final Position futurePosition = new Position(0, 0);

            final List<Position> actual = chariot.makeRoute(futurePosition);

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
            final Chariot chariot = new Chariot(Team.HAN, new Position(0, 0));
            final Position futurePosition = new Position(0, 5);

            final List<Position> actual = chariot.makeRoute(futurePosition);

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
            final Chariot chariot = new Chariot(Team.HAN, new Position(0, 5));
            final Position futurePosition = new Position(0, 0);

            final List<Position> actual = chariot.makeRoute(futurePosition);

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
            final Chariot chariot = new Chariot(Team.HAN, new Position(5, 5));

            final Map<Position, Piece> board = Map.of(
                    new Position(6, 5), new Soldier(Team.HAN, new Position(6, 5))
            );

            final Position futurePosition = new Position(7, 5);

            //when //then
            assertThatThrownBy(() -> chariot.checkObstacle(futurePosition, board))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageStartingWith("[ERROR]");
        }

        @DisplayName("차의 이동 경로에 장애물이 없다면 예외를 던지지 않는다.")
        @Test
        void nonObstacle() {
            //given
            final Chariot chariot = new Chariot(Team.HAN, new Position(5, 5));

            final Map<Position, Piece> board = Map.of(
                    new Position(7, 5), new Soldier(Team.HAN, new Position(7, 5))
            );

            final Position futurePosition = new Position(6, 5);

            //when //then
            assertThatCode(() -> chariot.checkObstacle(futurePosition, board))
                    .doesNotThrowAnyException();
        }
    }
}
