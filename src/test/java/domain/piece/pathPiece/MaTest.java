package domain.piece.pathPiece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.BoardFixture;
import domain.Coordinate;
import domain.Team;
import domain.board.Board;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class MaTest {

    @Nested
    @DisplayName("이동 가능 여부 반환하는 테스트")
    class CanMoveTest {

        @ParameterizedTest
        @DisplayName("마는 직선 한 칸 이후 이동했던 쪽 대각선으로 이동할 수 있다.")
        @MethodSource("provideMaArrivals")
        void test1(Coordinate arrival) {
            // given
            Ma ma = new Ma(Team.HAN, new Coordinate(5, 5));

            // when
            final var canMove = ma.canMove(arrival, BoardFixture.emptyBoard());

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
    }

    @Nested
    @DisplayName("장애물을 고려한 움직임 가능 여부 테스트")
    class CanMoveConsideringObstaclesTest {

        @Test
        @DisplayName("마의 이동 경로에 장애물이 있으면 이동할 수 없다.")
        void test1() {
            // given
            Ma ma = new Ma(Team.CHO, new Coordinate(5, 5));
            Board board = new BoardFixture()
                .addPiece(5, 5, ma)
                .anyPiece(5, 4) // 상 -> 상좌 방향 이동 경로 = 상 (5, 4)
                .build();

            // when
            final var canMove = ma.canMove(new Coordinate(4, 3), board);

            // then
            assertThat(canMove).isFalse();
        }

        @Test
        @DisplayName("마가 이동할 때 장애물이 없으면 이동할 수 있다.")
        void test2() {
            // given
            Ma ma = new Ma(Team.CHO, new Coordinate(5, 5));

            // when
            final var canMove = ma.canMove(new Coordinate(4, 3), BoardFixture.emptyBoard());

            // then
            assertThat(canMove).isTrue();
        }
    }
}
