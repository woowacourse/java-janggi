package janggi.domain.movement;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.BoardFixture;
import janggi.domain.Coordinate;
import janggi.domain.movestep.MoveStep;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class OnceMovementTest {

    @Test
    @DisplayName("일회성 움직임을 움직임 스텝들로 만들 수 있다.")
    void createMovement() {
        Movement movement = new OnceMovement(
            MoveStep.UP,
            MoveStep.RIGHT
        );

        assertThat(movement).isNotNull();
    }

    @Nested
    @DisplayName("일회성 움직임이 가진 움직임 스텝들로 움직일 수 있다.")
    class CanMoveTest {

        @ParameterizedTest
        @DisplayName("상하좌우 움직임 스텝 => 상하좌우 모든 방향으로 한 칸 움직임 가능")
        @MethodSource("provideCrossArrivals")
        void test1(Coordinate arrival) {
            // given
            Movement movement = new OnceMovement(
                MoveStep.UP,
                MoveStep.DOWN,
                MoveStep.LEFT,
                MoveStep.RIGHT
            );

            final var departure = new Coordinate(5, 5);

            // when
            final var canMove = movement.canMove(departure, arrival, BoardFixture.emptyBoard());

            // then
            assertThat(canMove).isTrue();
        }

        private static Stream<Arguments> provideCrossArrivals() {
            return Stream.of(
                Arguments.of(new Coordinate(4, 5)),
                Arguments.of(new Coordinate(6, 5)),
                Arguments.of(new Coordinate(5, 4)),
                Arguments.of(new Coordinate(5, 6))
            );
        }

        @ParameterizedTest
        @DisplayName("대각선 움직임 스텝 => 대각선 모든 방향으로 한 칸 움직임 가능")
        @MethodSource("provideDiagonalArrivals")
        void test2(Coordinate arrival) {
            // given
            Movement movement = new OnceMovement(
                MoveStep.LEFT_UP,
                MoveStep.RIGHT_UP,
                MoveStep.LEFT_DOWN,
                MoveStep.RIGHT_DOWN
            );

            final var departure = new Coordinate(5, 5);

            // when
            final var canMove = movement.canMove(departure, arrival, BoardFixture.emptyBoard());

            // then
            assertThat(canMove).isTrue();
        }

        private static Stream<Arguments> provideDiagonalArrivals() {
            return Stream.of(
                Arguments.of(new Coordinate(4, 4)),
                Arguments.of(new Coordinate(4, 6)),
                Arguments.of(new Coordinate(6, 4)),
                Arguments.of(new Coordinate(6, 6))
            );
        }
    }
}
