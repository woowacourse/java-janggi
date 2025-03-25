package domain.piece.pathMovement;

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
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

public class ChaMovementTest {

    @Nested
    @DisplayName("이동 가능 여부 반환하는 테스트")
    class CanMoveTest {

        @ParameterizedTest
        @DisplayName("차는 기본적으로 상하좌우 모든 칸을 이동할 수 있다.")
        @MethodSource("provideCrossCoordinates")
        void test1(Coordinate arrival) {
            // given
            final var movement = new ChaMovement();
            final var departure = new Coordinate(5, 5);

            // when
            final var canMove = movement.canMove(departure, arrival, BoardFixture.emptyBoard());

            // then
            assertThat(canMove).isTrue();
        }

        @Nested
        @DisplayName("차가 궁성 내에서 대각선을 따라 움직일 수 있다.")
        class InCastleMoveTest {

            @ParameterizedTest
            @DisplayName("차가 궁성 내에 있을 때 대각선을 따라 움직일 수 있다.")
            @CsvSource({"4,1", "4,3", "6,1", "6,3"})
            void test1(int x, int y) {
                // given
                final var movement = new ChaMovement();
                final var departure = new Coordinate(5, 2);
                final var arrival = new Coordinate(x, y);

                // when
                final var canMove = movement.canMove(departure, arrival, BoardFixture.emptyBoard());

                // then
                assertThat(canMove).isTrue();
            }

            /*
            x...
            ....
            .... <- 궁성 최하단
            ...! <- 궁성 바깥
             */
            @Test
            @DisplayName("차가 대각선을 따라 움직일 때 궁성을 벗어날 수 없다.")
            void test2() {
                //given
                final var movement = new ChaMovement();
                final var departure = new Coordinate(4, 1);
                final var arrival = new Coordinate(7, 4);

                //when
                final var canMove = movement.canMove(departure, arrival, BoardFixture.emptyBoard());

                //then
                assertThat(canMove).isFalse();
            }
        }

        public static Stream<Arguments> provideCrossCoordinates() {
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
    }

    @Nested
    @DisplayName("장애물을 고려한 움직임 가능 여부 테스트")
    class CanMoveConsideringObstaclesTest {

        @Test
        @DisplayName("차가 이동할 때 장애물이 하나라도 있을 경우 이동할 수 없다.")
        void test1() {
            // given
            final var movement = new ChaMovement();
            final var departure = new Coordinate(5, 5);
            final var arrival = new Coordinate(8, 5);

            Board board = new BoardFixture()
                .anyPieceNotPo(6, 5)
                .build();

            // when
            final var canMove = movement.canMove(departure, arrival, board);

            // then
            assertThat(canMove).isFalse();
        }

        @Test
        @DisplayName("차가 이동할 때 장애물이 하나도 없을 경우 이동할 수 있다.")
        void test2() {
            // given
            final var movement = new ChaMovement();
            final var departure = new Coordinate(5, 5);
            final var arrival = new Coordinate(8, 5);

            // when
            final var canMove = movement.canMove(departure, arrival, BoardFixture.emptyBoard());

            // then
            assertThat(canMove).isTrue();
        }
    }
}
