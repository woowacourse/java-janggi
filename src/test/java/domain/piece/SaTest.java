package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.BoardFixture;
import domain.Coordinate;
import domain.Team;
import domain.board.Board;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class SaTest {

    @Nested
    @DisplayName("이동 가능한 후보를 반환하는 테스트")
    class MovableCandidatesTest {

        @Test
        @DisplayName("사의 출발 좌표가 (5,5)일 때 이동 가능한 좌표 후보 4개를 반환한다.")
        void test1() {
            // given
            Sa sa = new Sa(Team.HAN);

            // when
            Set<Coordinate> movableCandidates = sa.findMovableCandidates(new Coordinate(5, 5));

            // then
            assertThat(movableCandidates).containsOnly(
                    new Coordinate(4, 5),
                    new Coordinate(6, 5),
                    new Coordinate(5, 6),
                    new Coordinate(5, 4)
            );
        }

        @Test
        @DisplayName("사의 출발 좌표가 (4,1)일 때 보드판을 벗어난 좌표는 후보에서 제외한다.")
        void test2() {
            // given
            Sa sa = new Sa(Team.HAN);

            // when
            Set<Coordinate> movableCandidates = sa.findMovableCandidates(new Coordinate(4, 1));

            // then
            assertThat(movableCandidates).containsOnly(
                    new Coordinate(3, 1),
                    new Coordinate(5, 1),
                    new Coordinate(4, 2),
                    new Coordinate(5, 2)
            );
        }

        @Nested
        @DisplayName("사가 궁성 내에서 대각선을 따라 움직일 수 있다.")
        class InCastleMovableCandidatesTest {

            @Test
            @DisplayName("사의 출발 좌표가 (5,2)일 때 대각선 4개의 방향 또한 후보로 반환한다.")
            void test1() {
                // given
                Sa sa = new Sa(Team.HAN);

                // when
                Set<Coordinate> movableCandidates = sa.findMovableCandidates(new Coordinate(5, 2));

                // then
                assertThat(movableCandidates).contains(
                    new Coordinate(4, 1),
                    new Coordinate(4, 3),
                    new Coordinate(6, 1),
                    new Coordinate(6, 3)
                );
            }

            @Test
            @DisplayName("사의 출발 좌표가 (4,1)일 때 궁성의 대각선을 따라 우측 하단을 후보로 반환한다.")
            void test2() {
                // given
                Sa sa = new Sa(Team.HAN);

                // when
                Set<Coordinate> movableCandidates = sa.findMovableCandidates(new Coordinate(4, 1));

                // then
                assertThat(movableCandidates).contains(new Coordinate(5, 2));
            }

            @Test
            @DisplayName("사의 출발 좌표가 (4,3)일 때 궁성의 대각선을 따라 우측 상단을 후보로 반환한다.")
            void test3() {
                // given
                Sa sa = new Sa(Team.HAN);

                // when
                Set<Coordinate> movableCandidates = sa.findMovableCandidates(new Coordinate(4, 3));

                // then
                assertThat(movableCandidates).contains(new Coordinate(5, 2));
            }

            @Test
            @DisplayName("사의 출발 좌표가 (6,1)일 때 궁성의 대각선을 따라 좌측 하단을 후보로 반환한다.")
            void test4() {
                // given
                Sa sa = new Sa(Team.HAN);

                // when
                Set<Coordinate> movableCandidates = sa.findMovableCandidates(new Coordinate(6, 1));

                // then
                assertThat(movableCandidates).contains(new Coordinate(5, 2));
            }

            @Test
            @DisplayName("사의 출발 좌표가 (6,3)일 때 궁성의 대각선을 따라 좌측 상단을 후보로 반환한다.")
            void test5() {
                // given
                Sa sa = new Sa(Team.HAN);

                // when
                Set<Coordinate> movableCandidates = sa.findMovableCandidates(new Coordinate(6, 3));

                // then
                assertThat(movableCandidates).contains(new Coordinate(5, 2));
            }
        }
    }

    @Nested
    @DisplayName("장애물을 고려한 움직임 가능 여부 테스트")
    class CanMoveConsideringObstaclesTest {

        @Test
        @DisplayName("사는 장애물을 고려하지 않아도 된다.")
        void test1() {
            // given
            Sa sa = new Sa(Team.HAN);
            Board board = new BoardFixture()
                    .addPiece(4, 5, new Sang(Team.HAN))
                    .addPiece(6, 5, new Sang(Team.HAN))
                    .addPiece(5, 6, new Sang(Team.HAN))
                    .addPiece(5, 4, new Sang(Team.HAN))
                    .build();

            // when
            boolean result = sa.canMoveConsideringObstacles(board, new Coordinate(5, 5), new Coordinate(5, 6));

            // then
            assertThat(result).isTrue();
        }
    }

    @Nested
    @DisplayName("경로를 찾는 경우의 수 테스트")
    class FindPathsTest {

        @Test
        @DisplayName("사는 경로가 없다.")
        void test1() {
            // given
            Sa sa = new Sa(Team.HAN);

            // when
            final var paths = sa.findPaths(new Coordinate(5, 5), new Coordinate(5, 6));

            // then
            assertThat(paths).isEmpty();
        }
    }
}
