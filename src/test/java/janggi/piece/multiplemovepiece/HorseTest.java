package janggi.piece.multiplemovepiece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.piece.Piece;
import janggi.piece.Team;
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

class HorseTest {

    @DisplayName("마는 자신의 팀과 위치를 가진다.")
    @Test
    void horseBoardPosition() {
        //given
        final Position position = new Position(4, 5);

        //when
        final Horse horse = new Horse(Team.HAN, position);

        //then
        assertThat(horse.getBoardPosition()).isEqualTo(new Position(4, 5));
    }

    @DisplayName("자신의 위치를 기준으로 이동할 수 없다면 false를 반환한다.")
    @ParameterizedTest
    @MethodSource("horseNonIsMovePositionProvider")
    void nonIsMove(final Position position) {
        //given
        final Horse horse = new Horse(Team.HAN, new Position(5, 5));

        //when //then
        assertThatThrownBy(() -> horse.isMove(position)).isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("자신의 위치를 기준으로 직선으로 한칸 + 대각선으로 한칸 이동할 수 있다면 true를 반환한다.")
    @ParameterizedTest
    @MethodSource("horseIsMovePositionProvider")
    void isMove(final Position position) {
        //given
        final Horse horse = new Horse(Team.HAN, new Position(5, 5));

        //when
        final boolean actual = horse.isMove(position);

        //then
        assertThat(actual).isTrue();
    }

    @Nested
    @DisplayName("마은 자신의 위치에서 목적지까지의 경로를 계산하여 반환한다.")
    class makeRoute {

        @DisplayName("자신의 위치에서 위로 한칸 이동 후 왼쪽 대각선으로 한칸 이동하는 경로를 계산한다")
        @Test
        void makeRouteCase1() {
            final Horse horse = new Horse(Team.HAN, new Position(5, 5));
            final Position futurePosition = new Position(3, 4);

            final List<Position> actual = horse.makeRoute(futurePosition);

            assertThat(actual).containsExactly(
                    new Position(4, 5)
            );
        }

        @DisplayName("자신의 위치에서 위로 한칸 이동 후 오른쪽 대각선으로 한칸 이동하는 경로를 계산한다")
        @Test
        void makeRouteCase2() {
            final Horse horse = new Horse(Team.HAN, new Position(5, 5));
            final Position futurePosition = new Position(3, 6);

            final List<Position> actual = horse.makeRoute(futurePosition);

            assertThat(actual).containsExactly(
                    new Position(4, 5)
            );
        }

        @DisplayName("자신의 위치에서 오른쪽으로 한칸 이동 후 왼쪽 대각선으로 한칸 이동하는 경로를 계산한다")
        @Test
        void makeRouteCase3() {
            final Horse horse = new Horse(Team.HAN, new Position(5, 5));
            final Position futurePosition = new Position(4, 7);

            final List<Position> actual = horse.makeRoute(futurePosition);

            assertThat(actual).containsExactly(
                    new Position(5, 6)
            );
        }

        @DisplayName("자신의 위치에서 오른쪽으로 한칸 이동 후 오른쪽 대각선으로 한칸 이동하는 경로를 계산한다")
        @Test
        void makeRouteCase4() {
            final Horse horse = new Horse(Team.HAN, new Position(5, 5));
            final Position futurePosition = new Position(6, 7);

            final List<Position> actual = horse.makeRoute(futurePosition);

            assertThat(actual).containsExactly(
                    new Position(5, 6)
            );
        }

        @DisplayName("자신의 위치에서 아래쪽으로 한칸 이동 후 왼쪽 대각선으로 한칸 이동하는 경로를 계산한다")
        @Test
        void makeRouteCase5() {
            final Horse horse = new Horse(Team.HAN, new Position(5, 5));
            final Position futurePosition = new Position(7, 6);

            final List<Position> actual = horse.makeRoute(futurePosition);

            assertThat(actual).containsExactly(
                    new Position(6, 5)
            );
        }

        @DisplayName("자신의 위치에서 아래쪽으로 한칸 이동 후 오른쪽 대각선으로 한칸 이동하는 경로를 계산한다")
        @Test
        void makeRouteCase6() {
            final Horse horse = new Horse(Team.HAN, new Position(5, 5));
            final Position futurePosition = new Position(7, 4);

            final List<Position> actual = horse.makeRoute(futurePosition);

            assertThat(actual).containsExactly(
                    new Position(6, 5)
            );
        }

        @DisplayName("자신의 위치에서 왼쪽으로 한칸 이동 후 왼쪽 대각선으로 한칸 이동하는 경로를 계산한다")
        @Test
        void makeRouteCase7() {
            final Horse horse = new Horse(Team.HAN, new Position(5, 5));
            final Position futurePosition = new Position(6, 3);

            final List<Position> actual = horse.makeRoute(futurePosition);

            assertThat(actual).containsExactly(
                    new Position(5, 4)
            );
        }

        @DisplayName("자신의 위치에서 왼쪽으로 한칸 이동 후 오른쪽 대각선으로 한칸 이동하는 경로를 계산한다")
        @Test
        void makeRouteCase8() {
            final Horse horse = new Horse(Team.HAN, new Position(5, 5));
            final Position futurePosition = new Position(4, 3);

            final List<Position> actual = horse.makeRoute(futurePosition);

            assertThat(actual).containsExactly(
                    new Position(5, 4)
            );
        }
    }

    private static Stream<Arguments> horseNonIsMovePositionProvider() {
        return Stream.of(Arguments.of(new Position(3, 5)), Arguments.of(new Position(3, 3)),
                Arguments.of(new Position(3, 7)), Arguments.of(new Position(5, 7)),
                Arguments.of(new Position(3, 7)), Arguments.of(new Position(7, 7)),
                Arguments.of(new Position(7, 5)), Arguments.of(new Position(7, 3)),
                Arguments.of(new Position(5, 3)));
    }

    private static Stream<Arguments> horseIsMovePositionProvider() {
        return Stream.of(Arguments.of(new Position(3, 4)), Arguments.of(new Position(3, 6)),
                Arguments.of(new Position(4, 7)), Arguments.of(new Position(6, 7)),
                Arguments.of(new Position(7, 6)), Arguments.of(new Position(7, 4)),
                Arguments.of(new Position(6, 3)), Arguments.of(new Position(4, 3)));
    }

    @DisplayName("마가 일보 전진하는 자리에 멱(장애물)이 존재한다면 예외가 발생한다.")
    @Test
    void hasObstacle() {
        //given
        final Horse horse = new Horse(Team.HAN, new Position(0, 2));

        final Map<Position, Piece> board = Map.of(
                new Position(1, 2), new Chariot(Team.HAN, new Position(1, 2))
        );

        final Position futurePosition = new Position(2, 3);

        //when //then
        assertThatThrownBy(() -> horse.checkObstacle(futurePosition, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("마의 이동 경로에 장애물이 없다면 예외를 던지지 않는다.")
    @Test
    void nonObstacle() {
        //given
        final Horse horse = new Horse(Team.HAN, new Position(0, 2));

        final Map<Position, Piece> board = Map.of(
                new Position(2, 2), new Chariot(Team.HAN, new Position(2, 2))
        );

        final Position futurePosition = new Position(2, 3);

        //when //then
        assertThatCode(() -> horse.checkObstacle(futurePosition, board))
                .doesNotThrowAnyException();
    }
}
