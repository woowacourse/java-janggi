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
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

public class ChaTest {

    @Nested
    @DisplayName("이동 가능 여부 반환하는 테스트")
    class CanMoveTest {

        @ParameterizedTest
        @DisplayName("차는 기본적으로 상하좌우 모든 칸을 이동할 수 있다.")
        @MethodSource("provideCrossCoordinates")
        void test1(Coordinate arrival) {
            // given
            Cha cha = new Cha(Team.HAN, new Coordinate(5, 5));

            // when
            final var canMove = cha.canMove(arrival, BoardFixture.emptyBoard());

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
                Cha cha = new Cha(Team.HAN, new Coordinate(5, 2));

                // when
                final var canMove = cha.canMove(new Coordinate(x, y), BoardFixture.emptyBoard());

                // then
                assertThat(canMove).isTrue();
            }

            @Test
            @DisplayName("차가 대각선을 따라 움직일 때 궁성을 벗어날 수 없다.")
            void test2() {
                //given
                Cha cha = new Cha(Team.HAN, new Coordinate(4, 1));

                //when
                final var canMove = cha.canMove(new Coordinate(7, 4), BoardFixture.emptyBoard());

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
            Cha cha = new Cha(Team.HAN, new Coordinate(5, 5));
            Board board = new BoardFixture()
                .addPiece(cha)
                .anyPiece(6, 5)
                .build();

            // when
            final var canMove = cha.canMove(new Coordinate(8, 5), board);

            // then
            assertThat(canMove).isFalse();
        }

        @Test
        @DisplayName("차가 이동할 때 장애물이 하나도 없을 경우 이동할 수 있다.")
        void test2() {
            // given
            Cha cha = new Cha(Team.HAN, new Coordinate(5, 5));

            // when
            boolean result = cha.canMove(new Coordinate(8, 5), BoardFixture.emptyBoard());

            // then
            assertThat(result).isTrue();
        }
    }
}
