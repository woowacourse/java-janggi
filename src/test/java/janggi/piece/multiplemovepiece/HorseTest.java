package janggi.piece.multiplemovepiece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.piece.Piece;
import janggi.piece.PieceType;
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

    @DisplayName("마는 팀과 타입을 가진다.")
    @Test
    void horsejanggiBoardPosition() {
        //given //when
        final Horse horse = new Horse(Team.HAN);

        //then
        assertThat(horse.getPieceType()).isEqualTo(PieceType.HORSE);
        assertThat(horse.getTeam()).isEqualTo(Team.HAN);
    }

    @DisplayName("제공된 위치를 기준으로 이동할 수 없다면 예외를 반환한다.")
    @ParameterizedTest
    @MethodSource("horseNonCanMoveByPositionProvider")
    void nonCanMoveBy(final Position currentPosition, final Position targetPosition) {
        //given
        final Horse horse = new Horse(Team.HAN);

        //when //then
        assertThatThrownBy(() -> horse.canMoveBy(currentPosition, targetPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    private static Stream<Arguments> horseNonCanMoveByPositionProvider() {
        return Stream.of(
                Arguments.of(new Position(5, 5), new Position(3, 5)),
                Arguments.of(new Position(5, 5), new Position(3, 3)),
                Arguments.of(new Position(5, 5), new Position(3, 7)),
                Arguments.of(new Position(5, 5), new Position(5, 7)),
                Arguments.of(new Position(5, 5), new Position(3, 7)),
                Arguments.of(new Position(5, 5), new Position(7, 7)),
                Arguments.of(new Position(5, 5), new Position(7, 5)),
                Arguments.of(new Position(5, 5), new Position(7, 3)),
                Arguments.of(new Position(5, 5), new Position(5, 3))
        );
    }

    @DisplayName("제공된 위치를 기준으로 직선으로 한칸 + 대각선으로 한칸 이동할 수 있다면 예외를 반환하지 않는다.")
    @ParameterizedTest
    @MethodSource("horseCanMoveByPositionProvider")
    void canMoveBy(final Position currentPosition, final Position targetPosition) {
        //given
        final Horse horse = new Horse(Team.HAN);

        //when //then
        assertThatCode(() -> horse.canMoveBy(currentPosition, targetPosition))
                .doesNotThrowAnyException();
    }

    private static Stream<Arguments> horseCanMoveByPositionProvider() {
        return Stream.of(
                Arguments.of(new Position(5, 5), new Position(3, 4)),
                Arguments.of(new Position(5, 5), new Position(3, 6)),
                Arguments.of(new Position(5, 5), new Position(4, 7)),
                Arguments.of(new Position(5, 5), new Position(6, 7)),
                Arguments.of(new Position(5, 5), new Position(7, 6)),
                Arguments.of(new Position(5, 5), new Position(7, 4)),
                Arguments.of(new Position(5, 5), new Position(6, 3)),
                Arguments.of(new Position(5, 5), new Position(4, 3))
        );
    }

    @Nested
    @DisplayName("마는 제공된 위치에서 목적지까지의 경로를 계산하여 반환한다.")
    class makeRoute {
        @DisplayName("제공된 위치에서 위로 한칸 이동 후 왼쪽 대각선으로 한칸 이동하는 경로를 계산한다")
        @Test
        void makeRouteCase1() {
            final Horse horse = new Horse(Team.HAN);

            final Position currentPosition = new Position(5, 5);
            final Position targetPosition = new Position(3, 4);

            final List<Position> actual = horse.makeRoute(currentPosition, targetPosition);

            assertThat(actual).containsExactly(
                    new Position(4, 5)
            );
        }

        @DisplayName("제공된 위치에서 위로 한칸 이동 후 오른쪽 대각선으로 한칸 이동하는 경로를 계산한다")
        @Test
        void makeRouteCase2() {
            final Horse horse = new Horse(Team.HAN);

            final Position currentPosition = new Position(5, 5);
            final Position targetPosition = new Position(3, 6);

            final List<Position> actual = horse.makeRoute(currentPosition, targetPosition);

            assertThat(actual).containsExactly(
                    new Position(4, 5)
            );
        }

        @DisplayName("제공된 위치에서 오른쪽으로 한칸 이동 후 왼쪽 대각선으로 한칸 이동하는 경로를 계산한다")
        @Test
        void makeRouteCase3() {
            final Horse horse = new Horse(Team.HAN);

            final Position currentPosition = new Position(5, 5);
            final Position targetPosition = new Position(4, 7);

            final List<Position> actual = horse.makeRoute(currentPosition, targetPosition);

            assertThat(actual).containsExactly(
                    new Position(5, 6)
            );
        }

        @DisplayName("제공된 위치에서 오른쪽으로 한칸 이동 후 오른쪽 대각선으로 한칸 이동하는 경로를 계산한다")
        @Test
        void makeRouteCase4() {
            final Horse horse = new Horse(Team.HAN);

            final Position currentPosition = new Position(5, 5);
            final Position targetPosition = new Position(6, 7);

            final List<Position> actual = horse.makeRoute(currentPosition, targetPosition);

            assertThat(actual).containsExactly(
                    new Position(5, 6)
            );
        }

        @DisplayName("제공된 위치에서 아래쪽으로 한칸 이동 후 왼쪽 대각선으로 한칸 이동하는 경로를 계산한다")
        @Test
        void makeRouteCase5() {
            final Horse horse = new Horse(Team.HAN);

            final Position currentPosition = new Position(5, 5);
            final Position targetPosition = new Position(7, 6);

            final List<Position> actual = horse.makeRoute(currentPosition, targetPosition);

            assertThat(actual).containsExactly(
                    new Position(6, 5)
            );
        }

        @DisplayName("제공된 위치에서 아래쪽으로 한칸 이동 후 오른쪽 대각선으로 한칸 이동하는 경로를 계산한다")
        @Test
        void makeRouteCase6() {
            final Horse horse = new Horse(Team.HAN);

            final Position currentPosition = new Position(5, 5);
            final Position targetPosition = new Position(7, 4);

            final List<Position> actual = horse.makeRoute(currentPosition, targetPosition);

            assertThat(actual).containsExactly(
                    new Position(6, 5)
            );
        }

        @DisplayName("제공된 위치에서 왼쪽으로 한칸 이동 후 왼쪽 대각선으로 한칸 이동하는 경로를 계산한다")
        @Test
        void makeRouteCase7() {
            final Horse horse = new Horse(Team.HAN);

            final Position currentPosition = new Position(5, 5);
            final Position targetPosition = new Position(6, 3);

            final List<Position> actual = horse.makeRoute(currentPosition, targetPosition);

            assertThat(actual).containsExactly(
                    new Position(5, 4)
            );
        }

        @DisplayName("제공된 위치에서 왼쪽으로 한칸 이동 후 오른쪽 대각선으로 한칸 이동하는 경로를 계산한다")
        @Test
        void makeRouteCase8() {
            final Horse horse = new Horse(Team.HAN);

            final Position currentPosition = new Position(5, 5);
            final Position targetPosition = new Position(4, 3);

            final List<Position> actual = horse.makeRoute(currentPosition, targetPosition);

            assertThat(actual).containsExactly(
                    new Position(5, 4)
            );
        }

        @DisplayName("마가 일보 전진하는 자리에 멱(장애물)이 존재한다면 예외가 발생한다.")
        @Test
        void hasObstacle() {
            //given
            final Horse horse = new Horse(Team.HAN);

            final Map<Position, Piece> janggiBoard = Map.of(
                    new Position(1, 2), new Chariot(Team.HAN)
            );

            final Position currentPosition = new Position(1, 1);
            final Position targetPosition = new Position(2, 3);

            //when //then
            assertThatThrownBy(() -> horse.checkObstacle(currentPosition, targetPosition, janggiBoard))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageStartingWith("[ERROR]");
        }

        @DisplayName("마의 이동 경로에 장애물이 없다면 예외를 던지지 않는다.")
        @Test
        void nonObstacle() {
            //given
            final Horse horse = new Horse(Team.HAN);

            final Map<Position, Piece> janggiBoard = Map.of(
                    new Position(2, 2), new Chariot(Team.HAN)
            );

            final Position currentPosition = new Position(1, 1);
            final Position targetPosition = new Position(2, 3);

            //when //then
            assertThatCode(() -> horse.checkObstacle(currentPosition, targetPosition, janggiBoard))
                    .doesNotThrowAnyException();
        }
    }
}
