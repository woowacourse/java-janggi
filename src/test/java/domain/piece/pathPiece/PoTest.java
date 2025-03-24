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

class PoTest {

    @Nested
    @DisplayName("이동 가능 여부 반환하는 테스트")
    class CanMoveTest {

        @ParameterizedTest
        @DisplayName("포는 조건을 만족했을 때 포다리를 제외한 상하좌우 모든 칸을 이동할 수 있다.")
        @MethodSource("provideCrossCoordinates")
        void test1(Coordinate arrival) {
            // given
            Po po = new Po(Team.HAN, new Coordinate(5, 5));
            Board board = new BoardFixture()
                .addPiece(5, 5, po)
                .anyPieceNotPo(5, 4) // <- 위 방향 포다리
                .anyPieceNotPo(5, 6) // <- 아래 방향 포다리
                .anyPieceNotPo(4, 5) // <- 좌측 방향 포다리
                .anyPieceNotPo(6, 5) // <- 우측 방향 포다리
                .build();

            // when
            final var canMove = po.canMove(arrival, board);

            // then
            assertThat(canMove).isTrue();
        }

        @Nested
        @DisplayName("포가 궁성 내에서 대각선을 따라 움직일 수 있다.")
        class InCastleMoveTest {

            @ParameterizedTest
            @DisplayName("포가 궁성 내에 있을 때 대각선을 따라 움직일 수 있다.")
            @MethodSource("provideFromTo")
            void test1(Coordinate from, Coordinate to) {
                // given
                Po po = new Po(Team.HAN, from);
                Board board = new BoardFixture()
                    .addPiece(4, 1, po)
                    .anyPieceNotPo(5, 2) // <- 포다리
                    .build();

                // when
                final var canMove = po.canMove(to, board);

                // then
                assertThat(canMove).isTrue();
            }

            private static Stream<Arguments> provideFromTo() {
                return Stream.of(
                    Arguments.of(new Coordinate(4, 1), new Coordinate(6, 3)),
                    Arguments.of(new Coordinate(4, 3), new Coordinate(6, 1)),
                    Arguments.of(new Coordinate(6, 1), new Coordinate(4, 3)),
                    Arguments.of(new Coordinate(6, 3), new Coordinate(4, 1))
                );
            }

            @Test
            @DisplayName("포가 대각선을 따라 움직일 때 궁성을 벗어날 수 없다.")
            void test2() {
                //given
                Po po = new Po(Team.HAN, new Coordinate(4, 1));
                Board board = new BoardFixture()
                    .addPiece(4, 1, po)
                    .anyPieceNotPo(5, 2) // <- 포다리
                    .build();

                //when
                final var canMove = po.canMove(new Coordinate(7, 4), board);

                //then
                assertThat(canMove).isFalse();
            }
        }

        public static Stream<Arguments> provideCrossCoordinates() {
            return Stream.of(
                Arguments.of(new Coordinate(1, 5)),
                Arguments.of(new Coordinate(2, 5)),
                Arguments.of(new Coordinate(3, 5)),
                Arguments.of(new Coordinate(7, 5)),
                Arguments.of(new Coordinate(8, 5)),
                Arguments.of(new Coordinate(9, 5)),
                Arguments.of(new Coordinate(5, 1)),
                Arguments.of(new Coordinate(5, 2)),
                Arguments.of(new Coordinate(5, 3)),
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
        @DisplayName("포가 이동할 때 포다리가 없을 경우 이동할 수 없다.")
        void test1() {
            // given
            Po po = new Po(Team.HAN, new Coordinate(5, 5));

            // when
            final var canMove = po.canMove(new Coordinate(8, 5), BoardFixture.emptyBoard());

            // then
            assertThat(canMove).isFalse();
        }

        @Test
        @DisplayName("포가 이동할 때 뛰어넘을 기물이 하나가 아닐 경우 이동할 수 없다.")
        void test2() {
            // given
            Po po = new Po(Team.HAN, new Coordinate(5, 5));
            Board board = new BoardFixture()
                .addPiece(5, 5, po)
                .anyPieceNotPo(6, 5)
                .anyPieceNotPo(7, 5)
                .build();

            // when
            final var canMove = po.canMove(new Coordinate(8, 5), board);

            // then
            assertThat(canMove).isFalse();
        }

        @Test
        @DisplayName("포는 포를 뛰어넘을 수 없다.")
        void test3() {
            // given
            Po po = new Po(Team.HAN, new Coordinate(5, 5));
            Board board = new BoardFixture()
                .addPiece(5, 5, po)
                .addPiece(6, 5, Po.class, Team.CHO) // <- 포다리 [불가능]
                .build();

            // when
            final var canMove = po.canMove(new Coordinate(8, 5), board);

            // then
            assertThat(canMove).isFalse();
        }

        @Test
        @DisplayName("포의 도착지에 포가 있으면 이동할 수 없다.")
        void test4() {
            // given
            Po po = new Po(Team.HAN, new Coordinate(5, 5));
            Board board = new BoardFixture()
                .addPiece(5, 5, po)
                .anyPieceNotPo(6, 5) // <- 포다리
                .addPiece(8, 5, Po.class, Team.CHO) // <- 목적지
                .build();

            // when
            final var canMove = po.canMove(new Coordinate(8, 5), board);

            // then
            assertThat(canMove).isFalse();
        }

        @Test
        @DisplayName("포다리가 포가 아니면서 도착지에 포가 아닌 기물이 있으면 이동할 수 있다.")
        void test5() {
            // given
            Po po = new Po(Team.HAN, new Coordinate(5, 5));
            Board board = new BoardFixture()
                .addPiece(5, 5, po)
                .anyPieceNotPo(6, 5) // <- 포댜리
                .anyPieceNotPo(8, 5) // <- 목적지
                .build();

            // when
            final var canMove = po.canMove(new Coordinate(8, 5), board);

            // then
            assertThat(canMove).isTrue();
        }

        @Test
        @DisplayName("포가 이동할 때 포다리가 하나이면서 포다리가 포가 아니면서 도착 좌표에 피스가 없을 경우 이동할 수 있다.")
        void test6() {
            // given
            Po po = new Po(Team.HAN, new Coordinate(5, 5));
            Board board = new BoardFixture()
                .addPiece(5, 5, po)
                .anyPieceNotPo(6, 5) // <- 포다리
                .build();

            // when
            final var canMove = po.canMove(new Coordinate(8, 5), board);

            // then
            assertThat(canMove).isTrue();
        }
    }
}
