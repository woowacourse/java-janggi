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

class ByeongTest {

    @Nested
    @DisplayName("이동 가능한 후보를 반환하는 테스트")
    class MovableCandidatesTest {

        @Test
        @DisplayName("병의 출발 좌표가 (5,5)일 때 이동 가능한 좌표 후보 3개를 반환한다.")
        void test1() {
            // given
            Byeong byeong = new Byeong();

            // when
            Set<Coordinate> movableCandidates = byeong.findMovableCandidates(new Coordinate(5, 5));

            // then
            assertThat(movableCandidates).containsOnly(
                    new Coordinate(5, 6),
                    new Coordinate(6, 5),
                    new Coordinate(4, 5)
            );
        }

        @Test
        @DisplayName("병의 출발 좌표가 (1,4)일 때 보드판을 벗어난 좌표는 후보에서 제외한다.")
        void test2() {
            // given
            Byeong byeong = new Byeong();

            // when
            Set<Coordinate> movableCandidates = byeong.findMovableCandidates(new Coordinate(1, 4));

            // then
            assertThat(movableCandidates).containsOnly(
                    new Coordinate(2, 4),
                    new Coordinate(1, 5)
            );
        }

        @Test
        @DisplayName("병의 좌표가 (4,8)으로 궁성으로 진입했을 때 대각선 좌표까지 후보에 포함한다.")
        void test3() {
            // given
            Byeong byeong = new Byeong();

            // when
            Set<Coordinate> movableCandidates = byeong.findMovableCandidates(new Coordinate(4, 8));

            // then
            assertThat(movableCandidates).containsOnly(
                    new Coordinate(3, 8),
                    new Coordinate(5, 8),
                    new Coordinate(4, 9),
                    new Coordinate(5, 9)
            );
        }
    }

    @Nested
    @DisplayName("장애물을 고려한 움직임 가능 여부 테스트")
    class CanMoveConsideringObstaclesTest {

        @Test
        @DisplayName("병은 장애물을 고려하지 않아도 된다.")
        void test1() {
            // given
            Byeong byeong = new Byeong();
            Board board = new BoardFixture()
                    .addPiece(4, 5, new Sang(Team.HAN))
                    .addPiece(6, 5, new Sang(Team.HAN))
                    .addPiece(5, 6, new Sang(Team.HAN))
                    .build();

            // when
            boolean result = byeong.canMoveConsideringObstacles(board, new Coordinate(5, 5), new Coordinate(5, 6));

            // then
            assertThat(result).isTrue();
        }
    }

    @Nested
    @DisplayName("경로를 찾는 경우의 수 테스트")
    class FindPathsTest {

        @Test
        @DisplayName("병은 경로가 없다.")
        void test1() {
            // given
            Byeong byeong = new Byeong();

            // when
            Set<Coordinate> paths = byeong.findPaths(new Coordinate(5, 5), new Coordinate(5, 6));

            // then
            assertThat(paths).isEmpty();
        }
    }
}
