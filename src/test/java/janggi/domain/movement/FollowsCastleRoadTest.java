package janggi.domain.movement;

import static janggi.domain.movestep.MoveStep.LEFT_DOWN;
import static janggi.domain.movestep.MoveStep.LEFT_UP;
import static janggi.domain.movestep.MoveStep.RIGHT_DOWN;
import static janggi.domain.movestep.MoveStep.RIGHT_UP;
import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.BoardFixture;
import janggi.domain.Coordinate;
import janggi.domain.movestep.InfiniteMoveProcess;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("궁성의 연결된 길을 따라 움직일 수 있다.")
class FollowsCastleRoadTest {

    @ParameterizedTest
    @DisplayName("궁성 정중앙에 있을 때 대각선 4개의 방향으로 이동할 수 있다.")
    @CsvSource({"4,1", "4,3", "6,1", "6,3"})
    void test1(int x, int y) {
        // given
        final var movement = new FollowsCastleRoad(
            new OnceMovement(LEFT_UP, RIGHT_UP, LEFT_DOWN, RIGHT_DOWN));
        final var departure = new Coordinate(5, 2);
        final var arrival = new Coordinate(x, y);

        // when
        final var canMove = movement.canMove(departure, arrival, BoardFixture.emptyBoard());

        // then
        assertThat(canMove).isTrue();
    }

    @ParameterizedTest
    @DisplayName("궁성의 모서리 네 군데에서 궁성 정중앙으로 대각선 이동할 수 있다.")
    @CsvSource({"4,1", "4,3", "6,1", "6,3"})
    void test2(int x, int y) {
        // given
        final var movement = new FollowsCastleRoad(
            new OnceMovement(LEFT_UP, RIGHT_UP, LEFT_DOWN, RIGHT_DOWN)
        );
        final var departure = new Coordinate(x, y);
        final var arrival = new Coordinate(5, 2);

        // when
        final var canMove = movement.canMove(departure, arrival, BoardFixture.emptyBoard());

        // then
        assertThat(canMove).isTrue();
    }

    @Nested
    @DisplayName("생성 시 결합된 움직임을 이용해 길을 따라 움직인다.")
    class CanMoveByCombinedMovement {

        @ParameterizedTest
        @DisplayName("궁성 내에 위쪽 대각선이 있다고 하더라도 결합된 움직임에 위쪽 방향이 없으면 움직일 수 없다.")
        @CsvSource({"4,8", "6,8"})
        void test1(int x, int y) {
            //given
            final var movement = new FollowsCastleRoad(
                new OnceMovement(LEFT_DOWN, RIGHT_DOWN)
            );

            final var departure = new Coordinate(5, 9);
            final var arrival = new Coordinate(x, y);

            //when
            final var canMove = movement.canMove(departure, arrival, BoardFixture.emptyBoard());

            //then
            assertThat(canMove).isFalse();
        }

        @ParameterizedTest
        @DisplayName("궁성 내에 아래쪽 대각선이 있다고 하더라도 결합된 움직임에 아래쪽 방향이 없으면 움직일 수 없다.")
        @CsvSource({"4,10", "6,10"})
        void test2(int x, int y) {
            //given
            final var movement = new FollowsCastleRoad(
                new OnceMovement(LEFT_UP, RIGHT_UP)
            );

            final var departure = new Coordinate(5, 9);
            final var arrival = new Coordinate(x, y);

            //when
            final var canMove = movement.canMove(departure, arrival, BoardFixture.emptyBoard());

            //then
            assertThat(canMove).isFalse();
        }

        @ParameterizedTest
        @DisplayName("오직 한 칸 이동 가능한 움직임을 결합하면 궁성의 모서리에서 궁성 반대편 모서리로 대각선 2칸 이동할 수 없다.")
        @MethodSource("provideEdgeToEdge")
        void test3(Coordinate departure, Coordinate arrival) {
            // given
            final var movement = new FollowsCastleRoad(
                new OnceMovement(LEFT_UP, RIGHT_UP, LEFT_DOWN, RIGHT_DOWN)
            );

            // when
            final var canMove = movement.canMove(departure, arrival, BoardFixture.emptyBoard());

            // then
            assertThat(canMove).isFalse();
        }

        @ParameterizedTest
        @DisplayName("두 칸 이상 이동 가능한 움직임을 결합하면 궁성의 모서리에서 궁성 반대편 모서리로 대각선 2칸 이동할 수 있다.")
        @MethodSource("provideEdgeToEdge")
        void test4(Coordinate departure, Coordinate arrival) {
            // given
            final var movement = new FollowsCastleRoad(
                new SeveralMovement(
                    new InfiniteMoveProcess(LEFT_UP),
                    new InfiniteMoveProcess(RIGHT_UP),
                    new InfiniteMoveProcess(LEFT_DOWN),
                    new InfiniteMoveProcess(RIGHT_DOWN)
                )
            );

            // when
            final var canMove = movement.canMove(departure, arrival, BoardFixture.emptyBoard());

            // then
            assertThat(canMove).isTrue();
        }

        public static Stream<Arguments> provideEdgeToEdge() {
            return Stream.of(
                Arguments.of(new Coordinate(4, 1), new Coordinate(6, 3)),
                Arguments.of(new Coordinate(4, 3), new Coordinate(6, 1)),
                Arguments.of(new Coordinate(6, 1), new Coordinate(4, 3)),
                Arguments.of(new Coordinate(6, 3), new Coordinate(4, 1)),

                Arguments.of(new Coordinate(4, 8), new Coordinate(6, 10)),
                Arguments.of(new Coordinate(4, 10), new Coordinate(6, 8)),
                Arguments.of(new Coordinate(6, 8), new Coordinate(4, 10)),
                Arguments.of(new Coordinate(6, 10), new Coordinate(4, 8))
            );
        }
    }
}
