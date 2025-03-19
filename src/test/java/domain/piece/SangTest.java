package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Board;
import domain.BoardFixture;
import domain.Coordinate;
import domain.Team;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class SangTest {

    @Nested
    @DisplayName("이동 가능한 후보를 반환하는 테스트")
    class MovableCandidatesTest {

        @Test
        @DisplayName("상의 출발 좌표가 (5,5)일 때 이동 가능한 좌표 후보 8개를 반환한다.")
        void test1() {
            // given
            Sang sang = new Sang(Team.HAN);

            // when
            Set<Coordinate> movableCandidates = sang.findMovableCandidates(new Coordinate(5, 5));

            // then
            assertThat(movableCandidates).containsOnly(
                    new Coordinate(2, 3),
                    new Coordinate(3, 2),
                    new Coordinate(7, 2),
                    new Coordinate(8, 3),
                    new Coordinate(2, 7),
                    new Coordinate(3, 8),
                    new Coordinate(7, 8),
                    new Coordinate(8, 7)
            );
        }

        @Test
        @DisplayName("상의 출발 좌표가 (3,1)일 때 보드판을 벗어난 좌표는 후보에서 제외한다.")
        void test2() {
            // given
            Sang sang = new Sang(Team.HAN);

            // when
            Set<Coordinate> movableCandidates = sang.findMovableCandidates(new Coordinate(3, 1));

            // then
            assertThat(movableCandidates).containsOnly(
                    new Coordinate(1, 4),
                    new Coordinate(5, 4),
                    new Coordinate(6, 3)
            );
        }
    }

    @Nested
    @DisplayName("장애물을 고려한 움직임 가능 여부 테스트")
    class CanMoveConsideringObstaclesTest {

        @Test
        @DisplayName("상이 (5,5) -> (3,2) 으로 이동할 때 (5,4)를 거치기 때문에 false 반환한다.")
        void test1() {
            // given
            Sang sang = new Sang(Team.HAN);
            Board board = new BoardFixture()
                    .addPiece(5, 5, sang)
                    .addPiece(5, 4, new Sang(Team.CHO))
                    .build();

            // when
            boolean result = sang.canMoveConsideringObstacles(board, new Coordinate(5, 5), new Coordinate(3, 2));

            // then
            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("상이 (5,5) -> (3,2) 으로 이동할 때 장애물이 하나도 없을 경우 true 반환한다.")
        void test2() {
            // given
            Sang sang = new Sang(Team.HAN);
            Board board = new BoardFixture()
                    .addPiece(5, 5, sang)
                    .build();

            // when
            boolean result = sang.canMoveConsideringObstacles(board, new Coordinate(5, 5), new Coordinate(3, 2));

            // then
            assertThat(result).isTrue();
        }
    }

    @Nested
    @DisplayName("경로를 찾는 경우의 수 테스트")
    class FindPathsTest {

        @Test
        @DisplayName("상이 (5,5) -> (2,3) 으로 이동할 때 (4,5), (3,4)를 반환한다.")
        void test1() {
            // given
            Sang sang = new Sang(Team.HAN);

            // when
            Set<Coordinate> path1 = sang.findPaths(new Coordinate(5, 5), new Coordinate(2, 3));

            // then
            assertThat(path1).containsOnly(
                    new Coordinate(4, 5),
                    new Coordinate(3, 4)
            );
        }

        @Test
        @DisplayName("상이 (5,5) -> (2,7) 으로 이동할 때 (4,5), (3,6)를 반환한다.")
        void test2() {
            // given
            Sang sang = new Sang(Team.HAN);

            // when
            Set<Coordinate> path1 = sang.findPaths(new Coordinate(5, 5), new Coordinate(2, 7));

            // then
            assertThat(path1).containsOnly(
                    new Coordinate(4, 5),
                    new Coordinate(3, 6)
            );
        }

        @Test
        @DisplayName("상이 (5,5) -> (3,8) 으로 이동할 때 (5,6), (4,7)를 반환한다.")
        void test3() {
            // given
            Sang sang = new Sang(Team.HAN);

            // when
            Set<Coordinate> path1 = sang.findPaths(new Coordinate(5, 5), new Coordinate(3, 8));

            // then
            assertThat(path1).containsOnly(
                    new Coordinate(5, 6),
                    new Coordinate(4, 7)
            );
        }

        @Test
        @DisplayName("상이 (5,5) -> (7,8) 으로 이동할 때 (5,6), (6,7)를 반환한다.")
        void test4() {
            // given
            Sang sang = new Sang(Team.HAN);

            // when
            Set<Coordinate> path1 = sang.findPaths(new Coordinate(5, 5), new Coordinate(7, 8));

            // then
            assertThat(path1).containsOnly(
                    new Coordinate(5, 6),
                    new Coordinate(6, 7)
            );
        }

        @Test
        @DisplayName("상이 (5,5) -> (8,7) 으로 이동할 때 (6,5), (7,6)를 반환한다.")
        void test5() {
            // given
            Sang sang = new Sang(Team.HAN);

            // when
            Set<Coordinate> path1 = sang.findPaths(new Coordinate(5, 5), new Coordinate(8, 7));

            // then
            assertThat(path1).containsOnly(
                    new Coordinate(6, 5),
                    new Coordinate(7, 6)
            );
        }

        @Test
        @DisplayName("상이 (5,5) -> (8,3) 으로 이동할 때 (6,5), (7,4)를 반환한다.")
        void test6() {
            // given
            Sang sang = new Sang(Team.HAN);

            // when
            Set<Coordinate> path1 = sang.findPaths(new Coordinate(5, 5), new Coordinate(8, 3));

            // then
            assertThat(path1).containsOnly(
                    new Coordinate(6, 5),
                    new Coordinate(7, 4)
            );
        }

        @Test
        @DisplayName("상이 (5,5) -> (7,2) 으로 이동할 때 (5,4), (6,3)를 반환한다.")
        void test7() {
            // given
            Sang sang = new Sang(Team.HAN);

            // when
            Set<Coordinate> path1 = sang.findPaths(new Coordinate(5, 5), new Coordinate(7, 2));

            // then
            assertThat(path1).containsOnly(
                    new Coordinate(5, 4),
                    new Coordinate(6, 3)
            );
        }

        @Test
        @DisplayName("상이 (5,5) -> (3,2) 으로 이동할 때 (5,4), (4,3)를 반환한다.")
        void test8() {
            // given
            Sang sang = new Sang(Team.HAN);

            // when
            Set<Coordinate> path1 = sang.findPaths(new Coordinate(5, 5), new Coordinate(3, 2));

            // then
            assertThat(path1).containsOnly(
                    new Coordinate(5, 4),
                    new Coordinate(4, 3)
            );
        }
    }
}
