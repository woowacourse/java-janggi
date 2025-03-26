package domain.movement.path;

import static org.assertj.core.api.Assertions.assertThat;

import domain.BoardFixture;
import domain.Coordinate;
import domain.board.Board;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SangMovementTest {

    @Nested
    @DisplayName("이동 가능 여부 반환하는 테스트")
    class CanMoveTest {

        @ParameterizedTest
        @DisplayName("상은 직선 한 칸 이후 이동했던 쪽 대각선으로 두 번 이동할 수 있다.")
        @MethodSource("provideSangArrivals")
        void test1(Coordinate arrival) {
            // given
            final var movement = new SangMovement();
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

    @Nested
    @DisplayName("장애물을 고려한 움직임 가능 여부 테스트")
    class CanMoveConsideringObstaclesTest {

        @Test
        @DisplayName("상의 첫 번째 이동 경로에 장애물이 있으면 이동할 수 없다.")
        void test1() {
            // given
            final var movement = new SangMovement();
            final var departure = new Coordinate(5, 5);
            final var arrival = new Coordinate(3, 2);

            Board board = new BoardFixture()
                .anyPieceNotPo(5, 4) // 상 -> 상좌 방향 이동 경로 = 상 (5, 4)
                .build();

            // when
            final var canMove = movement.canMove(departure, arrival, board);

            // then
            assertThat(canMove).isFalse();
        }

        @Test
        @DisplayName("상의 두 번째 이동 경로에 장애물이 있으면 이동할 수 없다.")
        void test2() {
            // given
            final var movement = new SangMovement();
            final var departure = new Coordinate(5, 5);
            final var arrival = new Coordinate(3, 2);

            Board board = new BoardFixture()
                .anyPieceNotPo(4, 3) // 상 -> 상좌 -> 상좌 방향 이동 경로 = 상 -> 상좌 (4, 3)
                .build();

            // when
            final var canMove = movement.canMove(departure, arrival, board);

            // then
            assertThat(canMove).isFalse();
        }

        @Test
        @DisplayName("상이 이동할 때 장애물이 없으면 이동할 수 있다.")
        void test3() {
            // given
            final var movement = new SangMovement();
            final var departure = new Coordinate(5, 5);
            final var arrival = new Coordinate(3, 2);

            // when
            final var canMove = movement.canMove(departure, arrival, BoardFixture.emptyBoard());

            // then
            assertThat(canMove).isTrue();
        }
    }
}
