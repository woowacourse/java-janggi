package janggi.domain.movement.pathless;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.BoardFixture;
import janggi.domain.Coordinate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class JolMovementTest {

    @Nested
    @DisplayName("이동 가능 여부 반환하는 테스트")
    class CanMoveTest {

        @ParameterizedTest
        @DisplayName("졸은 기본적으로 상,좌,우 방향으로 이동할 수 있다.")
        @CsvSource({"3,6", "2,7", "4,7"})
        void test1(int x, int y) {
            // given
            final var movement = new JolMovement();
            final var departure = new Coordinate(3, 7);
            final var arrival = new Coordinate(x, y);

            // when
            final var canMove = movement.canMove(departure, arrival, BoardFixture.emptyBoard());

            // then
            assertThat(canMove).isTrue();
        }
    }

    @Nested
    @DisplayName("졸이 궁성 내에서 대각선을 따라 움직일 수 있다.")
    class InCastleMoveTest {

        @ParameterizedTest
        @DisplayName("졸이 궁성 내에 있을 때 위쪽 대각선을 따라 움직일 수 있다.")
        @CsvSource({"4,1", "6,1"})
        void test1(int x, int y) {
            // given
            final var movement = new JolMovement();
            final var departure = new Coordinate(5, 2);
            final var arrival = new Coordinate(x, y);

            // when
            final var canMove = movement.canMove(departure, arrival, BoardFixture.emptyBoard());

            // then
            assertThat(canMove).isTrue();
        }

        @ParameterizedTest
        @DisplayName("궁성 내에 아래쪽 대각선이 있다고 하더라도 졸은 아래쪽 방향으로 움직일 수 없다.")
        @CsvSource({"4,3", "6,3"})
        void test2(int x, int y) {
            //given
            final var movement = new JolMovement();
            final var departure = new Coordinate(5, 2);
            final var arrival = new Coordinate(x, y);

            //when
            final var canMove = movement.canMove(departure, arrival, BoardFixture.emptyBoard());

            //then
            assertThat(canMove).isFalse();
        }
    }
}
