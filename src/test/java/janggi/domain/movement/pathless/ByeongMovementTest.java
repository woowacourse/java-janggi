package janggi.domain.movement.pathless;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.BoardFixture;
import janggi.domain.Coordinate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class ByeongMovementTest {

    @Nested
    @DisplayName("이동 가능 여부 반환하는 테스트")
    class CanMoveTest {

        @ParameterizedTest
        @DisplayName("병은 기본적으로 하,좌,우 방향으로 이동할 수 있다.")
        @CsvSource({"3,5", "2,4", "4,4"})
        void test1(int x, int y) {
            // given
            final var movement = new ByeongMovement();
            final var departure = new Coordinate(3, 4);
            final var arrival = new Coordinate(x, y);

            // when
            final var canMove = movement.canMove(departure, arrival, BoardFixture.emptyBoard());

            // then
            assertThat(canMove).isTrue();
        }
    }

    @Nested
    @DisplayName("병이 궁성 내에서 대각선을 따라 움직일 수 있다.")
    class InCastleMoveTest {

        @ParameterizedTest
        @DisplayName("병이 궁성 내에 있을 때 아래쪽 대각선을 따라 움직일 수 있다.")
        @CsvSource({"4,10", "6,10"})
        void test1(int x, int y) {
            // given
            final var movement = new ByeongMovement();
            final var departure = new Coordinate(5, 9);
            final var arrival = new Coordinate(x, y);

            // when
            final var canMove = movement.canMove(departure, arrival, BoardFixture.emptyBoard());

            // then
            assertThat(canMove).isTrue();
        }

        @ParameterizedTest
        @DisplayName("궁성 내에 위쪽 대각선이 있다고 하더라도 병은 위쪽 방향으로 움직일 수 없다.")
        @CsvSource({"4,8", "6,8"})
        void test2(int x, int y) {
            //given
            final var movement = new ByeongMovement();
            final var departure = new Coordinate(5, 9);
            final var arrival = new Coordinate(x, y);

            //when
            final var canMove = movement.canMove(departure, arrival, BoardFixture.emptyBoard());

            //then
            assertThat(canMove).isFalse();
        }
    }
}
