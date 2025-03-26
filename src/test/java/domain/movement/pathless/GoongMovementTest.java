package domain.movement.pathless;

import static org.assertj.core.api.Assertions.assertThat;

import domain.BoardFixture;
import domain.Coordinate;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class GoongMovementTest {

    @Nested
    @DisplayName("이동 가능 여부 반환하는 테스트")
    class CanMoveTest {

        @ParameterizedTest
        @DisplayName("궁은 기본적으로 상하좌우 한 칸을 이동할 수 있다.")
        @CsvSource({"5,1", "5,3", "4,2", "6,2"})
        void test1(int x, int y) {
            // given
            final var movement = new GoongMovement();
            final var departure = new Coordinate(5, 2);
            final var arrival = new Coordinate(x, y);

            // when
            final var canMove = movement.canMove(departure, arrival, BoardFixture.emptyBoard());

            // then
            assertThat(canMove).isTrue();
        }

        @ParameterizedTest
        @DisplayName("궁은 궁성을 벗어날 수 없다.")
        @MethodSource("provideCoordinatesOutCastle")
        void test3(Coordinate departure, Coordinate arrival) {
            //given
            final var movement = new GoongMovement();

            //when
            final var canMove = movement.canMove(departure, arrival, BoardFixture.emptyBoard());

            //then
            assertThat(canMove).isFalse();
        }

        @Nested
        @DisplayName("궁은 궁성 내에서 대각선을 따라 움직일 수 있다.")
        class InCastleMoveTest {

            @ParameterizedTest
            @DisplayName("궁성 정중앙에 있을 때 대각선 4개의 방향으로 이동할 수 있다.")
            @CsvSource({"4,1", "4,3", "6,1", "6,3"})
            void test1(int x, int y) {
                // given
                final var movement = new GoongMovement();
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
                final var movement = new GoongMovement();
                final var departure = new Coordinate(x, y);
                final var arrival = new Coordinate(5, 2);

                // when
                final var canMove = movement.canMove(departure, arrival, BoardFixture.emptyBoard());

                // then
                assertThat(canMove).isTrue();
            }
        }

        public static Stream<Arguments> provideCoordinatesOutCastle() {
            return Stream.of(
                Arguments.of(new Coordinate(4, 10), new Coordinate(3, 10)),
                Arguments.of(new Coordinate(6, 10), new Coordinate(7, 10)),
                Arguments.of(new Coordinate(4, 9), new Coordinate(3, 9)),
                Arguments.of(new Coordinate(6, 9), new Coordinate(7, 9)),
                Arguments.of(new Coordinate(4, 8), new Coordinate(3, 8)),
                Arguments.of(new Coordinate(6, 8), new Coordinate(7, 8)),
                Arguments.of(new Coordinate(4, 8), new Coordinate(4, 7)),
                Arguments.of(new Coordinate(5, 8), new Coordinate(5, 7)),
                Arguments.of(new Coordinate(6, 8), new Coordinate(6, 7))
            );
        }
    }
}
