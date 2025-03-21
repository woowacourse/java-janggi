package piece;

import static org.assertj.core.api.Assertions.assertThat;

import board.Board;
import board.BoardFixture;
import coordinate.Coordinate;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import team.Team;

class JolTest {

    @Nested
    @DisplayName("이동 가능한 후보를 반환하는 테스트")
    class MovableCandidatesTest {

        @Test
        @DisplayName("HAN 팀의 졸의 출발 좌표가 (5,5)일 때 이동 가능한 좌표 후보 3개를 반환한다.")
        void test1() {
            // given
            Jol jol = new Jol(Team.HAN);

            // when
            Set<Coordinate> movableCandidates = jol.findMovableCandidates(new Coordinate(5, 5));

            // then
            assertThat(movableCandidates).containsOnly(
                    new Coordinate(4, 5),
                    new Coordinate(6, 5),
                    new Coordinate(5, 6)
            );
        }

        @Test
        @DisplayName("CHO 팀의 졸의 출발 좌표가 (5,5)일 때 이동 가능한 좌표 후보 3개를 반환한다.")
        void test2() {
            // given
            Jol jol = new Jol(Team.CHO);

            // when
            Set<Coordinate> movableCandidates = jol.findMovableCandidates(new Coordinate(5, 5));

            // then
            assertThat(movableCandidates).containsOnly(
                    new Coordinate(4, 5),
                    new Coordinate(6, 5),
                    new Coordinate(5, 4)
            );
        }

        @Test
        @DisplayName("HAN 팀의 졸의 출발 좌표가 (1,4)일 때 보드판을 벗어난 좌표는 후보에서 제외한다.")
        void test3() {
            // given
            Jol jol = new Jol(Team.HAN);

            // when
            Set<Coordinate> movableCandidates = jol.findMovableCandidates(new Coordinate(1, 4));

            // then
            assertThat(movableCandidates).containsOnly(
                    new Coordinate(2, 4),
                    new Coordinate(1, 5)
            );
        }

        @Test
        @DisplayName("CHO 팀의 졸의 출발 좌표가 (1,7)일 때 보드판을 벗어난 좌표는 후보에서 제외한다.")
        void test4() {
            // given
            Jol jol = new Jol(Team.CHO);

            // when
            Set<Coordinate> movableCandidates = jol.findMovableCandidates(new Coordinate(1, 7));

            // then
            assertThat(movableCandidates).containsOnly(
                    new Coordinate(2, 7),
                    new Coordinate(1, 6)
            );
        }
    }

    @Nested
    @DisplayName("장애물을 고려한 움직임 가능 여부 테스트")
    class CanMoveConsideringObstaclesTest {

        @Test
        @DisplayName("졸은 장애물을 고려하지 않아도 된다.")
        void test1() {
            // given
            Jol jol = new Jol(Team.HAN);
            Board board = new BoardFixture()
                    .addPiece(4, 5, new Sang(Team.HAN))
                    .addPiece(6, 5, new Sang(Team.HAN))
                    .addPiece(5, 6, new Sang(Team.HAN))
                    .build();

            // when
            boolean result = jol.canMoveConsideringObstacles(board, new Coordinate(5, 5), new Coordinate(5, 6));

            // then
            assertThat(result).isTrue();
        }
    }

    @Nested
    @DisplayName("경로를 찾는 경우의 수 테스트")
    class FindPathsTest {

        @Test
        @DisplayName("졸은 경로가 없다.")
        void test1() {
            // given
            Jol jol = new Jol(Team.HAN);

            // when
            Set<Coordinate> paths = jol.findPaths(new Coordinate(5, 5), new Coordinate(5, 6));

            // then
            assertThat(paths).isEmpty();
        }
    }
}
