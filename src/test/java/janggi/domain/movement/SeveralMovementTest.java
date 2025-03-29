package janggi.domain.movement;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.BoardFixture;
import janggi.domain.Coordinate;
import janggi.domain.movestep.InfiniteMoveProcess;
import janggi.domain.movestep.MoveProcess;
import janggi.domain.movestep.MoveStep;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class SeveralMovementTest {

    @Test
    @DisplayName("연속적인 움직임을 움직임 프로세스들로 만들 수 있다.")
    void createMovement() {
        Movement movement = new SeveralMovement(
            new MoveProcess(MoveStep.UP),
            new MoveProcess(MoveStep.RIGHT)
        );

        assertThat(movement).isNotNull();
    }

    @Test
    @DisplayName("연속적인 움직임은 경로에 다른 기물이 있으면 도달할 수 없다.")
    void canMoveConsiderObstacles() {
        //given
        Movement movement = new SeveralMovement(
            new MoveProcess(MoveStep.UP, MoveStep.RIGHT_UP)
        );
        final var board = new BoardFixture()
            .anyPieceNotPo(5, 4)
            .build();

        //when
        final var canMove = movement.canMove(new Coordinate(5, 5), new Coordinate(6, 3), board);

        //then
        assertThat(canMove).isFalse();
    }

    @Nested
    @DisplayName("연속적인 움직임이 가진 움직임 프로세스들로 움직일 수 있다.")
    class CanMoveTest {

        @ParameterizedTest
        @DisplayName("상하좌우 무한 움직임 프로세스 => 상하좌우 모든 칸으로 움직임 가능")
        @MethodSource("provideCrossArrivals")
        void test1(Coordinate arrival) {
            // given
            Movement movement = new SeveralMovement(
                new InfiniteMoveProcess(MoveStep.UP),
                new InfiniteMoveProcess(MoveStep.DOWN),
                new InfiniteMoveProcess(MoveStep.RIGHT),
                new InfiniteMoveProcess(MoveStep.LEFT)
            );

            final var departure = new Coordinate(5, 5);

            // when
            final var canMove = movement.canMove(departure, arrival, BoardFixture.emptyBoard());

            // then
            assertThat(canMove).isTrue();
        }

        private static Stream<Arguments> provideCrossArrivals() {
            return Stream.of(
                Arguments.of(new Coordinate(1, 5)),
                Arguments.of(new Coordinate(2, 5)),
                Arguments.of(new Coordinate(3, 5)),
                Arguments.of(new Coordinate(4, 5)),
                Arguments.of(new Coordinate(6, 5)),
                Arguments.of(new Coordinate(7, 5)),
                Arguments.of(new Coordinate(8, 5)),
                Arguments.of(new Coordinate(9, 5)),
                Arguments.of(new Coordinate(5, 1)),
                Arguments.of(new Coordinate(5, 2)),
                Arguments.of(new Coordinate(5, 3)),
                Arguments.of(new Coordinate(5, 4)),
                Arguments.of(new Coordinate(5, 6)),
                Arguments.of(new Coordinate(5, 7)),
                Arguments.of(new Coordinate(5, 8)),
                Arguments.of(new Coordinate(5, 9)),
                Arguments.of(new Coordinate(5, 10))
            );
        }

        @ParameterizedTest
        @DisplayName("마 기물의 움직임 프로세스 => 마 기물의 도달 가능한 모든 칸으로 움직임 가능")
        @MethodSource("provideMaArrivals")
        void test2(Coordinate arrival) {
            // given
            Movement movement = new SeveralMovement(
                new MoveProcess(MoveStep.UP, MoveStep.LEFT_UP),
                new MoveProcess(MoveStep.UP, MoveStep.RIGHT_UP),
                new MoveProcess(MoveStep.DOWN, MoveStep.LEFT_DOWN),
                new MoveProcess(MoveStep.DOWN, MoveStep.RIGHT_DOWN),
                new MoveProcess(MoveStep.LEFT, MoveStep.LEFT_UP),
                new MoveProcess(MoveStep.LEFT, MoveStep.LEFT_DOWN),
                new MoveProcess(MoveStep.RIGHT, MoveStep.RIGHT_UP),
                new MoveProcess(MoveStep.RIGHT, MoveStep.RIGHT_DOWN)
            );

            final var departure = new Coordinate(5, 5);

            // when
            final var canMove = movement.canMove(departure, arrival, BoardFixture.emptyBoard());

            // then
            assertThat(canMove).isTrue();
        }

        public static Stream<Arguments> provideMaArrivals() {
            return Stream.of(
                Arguments.of(new Coordinate(3, 4)),
                Arguments.of(new Coordinate(3, 6)),
                Arguments.of(new Coordinate(7, 4)),
                Arguments.of(new Coordinate(7, 6)),
                Arguments.of(new Coordinate(4, 3)),
                Arguments.of(new Coordinate(6, 3)),
                Arguments.of(new Coordinate(4, 7)),
                Arguments.of(new Coordinate(6, 7))
            );
        }

        @ParameterizedTest
        @DisplayName("상 기물의 움직임 프로세스 => 상 기물의 도달 가능한 모든 칸으로 움직임 가능")
        @MethodSource("provideSangArrivals")
        void test3(Coordinate arrival) {
            // given
            Movement movement = new SeveralMovement(
                new MoveProcess(MoveStep.UP, MoveStep.LEFT_UP, MoveStep.LEFT_UP),
                new MoveProcess(MoveStep.UP, MoveStep.RIGHT_UP, MoveStep.RIGHT_UP),
                new MoveProcess(MoveStep.DOWN, MoveStep.LEFT_DOWN, MoveStep.LEFT_DOWN),
                new MoveProcess(MoveStep.DOWN, MoveStep.RIGHT_DOWN, MoveStep.RIGHT_DOWN),
                new MoveProcess(MoveStep.LEFT, MoveStep.LEFT_UP, MoveStep.LEFT_UP),
                new MoveProcess(MoveStep.LEFT, MoveStep.LEFT_DOWN, MoveStep.LEFT_DOWN),
                new MoveProcess(MoveStep.RIGHT, MoveStep.RIGHT_UP, MoveStep.RIGHT_UP),
                new MoveProcess(MoveStep.RIGHT, MoveStep.RIGHT_DOWN, MoveStep.RIGHT_DOWN)
            );

            final var departure = new Coordinate(5, 5);

            // when
            final var canMove = movement.canMove(departure, arrival, BoardFixture.emptyBoard());

            // then
            assertThat(canMove).isTrue();
        }

        public static Stream<Arguments> provideSangArrivals() {
            return Stream.of(
                Arguments.of(new Coordinate(2, 3)),
                Arguments.of(new Coordinate(2, 7)),
                Arguments.of(new Coordinate(8, 3)),
                Arguments.of(new Coordinate(8, 7)),
                Arguments.of(new Coordinate(7, 2)),
                Arguments.of(new Coordinate(3, 2)),
                Arguments.of(new Coordinate(7, 8)),
                Arguments.of(new Coordinate(3, 8))
            );
        }
    }
}
