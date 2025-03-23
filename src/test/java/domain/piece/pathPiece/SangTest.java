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

class SangTest {

    @Nested
    @DisplayName("이동 가능 여부 반환하는 테스트")
    class CanMoveTest {

        @ParameterizedTest
        @DisplayName("상는 직선 한 칸 이후 이동했던 쪽 대각선으로 두 번 이동할 수 있다.")
        @MethodSource("provideSangArrivals")
        void test1(Coordinate arrival) {
            // given
            Sang sang = new Sang(Team.HAN, new Coordinate(5, 5));

            // when
            final var canMove = sang.canMove(arrival, BoardFixture.emptyBoard());

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
            Sang sang = new Sang(Team.CHO, new Coordinate(5, 5));
            Board board = new BoardFixture()
                .addPiece(5, 5, sang)
                .anyPiece(5, 4) // 상 -> 상좌 방향 이동 경로 = 상 (5, 4)
                .build();

            // when
            final var canMove = sang.canMove(new Coordinate(3, 2), board);

            // then
            assertThat(canMove).isFalse();
        }

        @Test
        @DisplayName("상의 두 번째 이동 경로에 장애물이 있으면 이동할 수 없다.")
        void test2() {
            // given
            Sang sang = new Sang(Team.CHO, new Coordinate(5, 5));
            Board board = new BoardFixture()
                .addPiece(5, 5, sang)
                .anyPiece(4, 3) // 상 -> 상좌 -> 상좌 방향 이동 경로 = 상 -> 상좌 (4, 3)
                .build();

            // when
            final var canMove = sang.canMove(new Coordinate(3, 2), board);

            // then
            assertThat(canMove).isFalse();
        }

        @Test
        @DisplayName("상가 이동할 때 장애물이 없으면 이동할 수 있다.")
        void test3() {
            // given
            Sang sang = new Sang(Team.CHO, new Coordinate(5, 5));

            // when
            final var canMove = sang.canMove(new Coordinate(3, 2), BoardFixture.emptyBoard());

            // then
            assertThat(canMove).isTrue();
        }
    }
}
