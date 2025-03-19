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

class MaTest {

    @Nested
    @DisplayName("이동 가능한 후보를 반환하는 테스트")
    class MovableCandidatesTest {

        @Test
        @DisplayName("마의 출발 좌표가 (5,5)일 때 이동 가능한 좌표 후보 8개를 반환한다.")
        void test1() {
            // given
            Ma ma = new Ma(Team.HAN);

            // when
            Set<Coordinate> movableCandidates = ma.findMovableCandidates(new Coordinate(5, 5));

            // then
            assertThat(movableCandidates).containsOnly(
                    new Coordinate(3, 4),
                    new Coordinate(3, 6),
                    new Coordinate(4, 3),
                    new Coordinate(4, 7),
                    new Coordinate(6, 3),
                    new Coordinate(6, 7),
                    new Coordinate(7, 4),
                    new Coordinate(7, 6)
            );
        }
    }

    @Nested
    @DisplayName("장애물을 고려한 움직임 가능 여부 테스트")
    class CanMoveConsideringObstaclesTest {

        @Test
        @DisplayName("마가 (5,5) -> (4,3) 으로 이동할 때 (5,4)를 거치기 때문에 false 반환한다.")
        void test1() {
            // given
            Ma ma = new Ma(Team.CHO);
            Board board = new BoardFixture()
                    .addPiece(5, 5, ma)
                    .addPiece(5, 4, new Sang(Team.CHO))
                    .build();

            // when
            boolean result = ma.canMoveConsideringObstacles(board, new Coordinate(5, 5), new Coordinate(4, 3));

            // then
            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("마가 (5,5) -> (4,3) 으로 이동할 때 장애물이 하나도 없을 경우 true를 반환한다.")
        void test2() {
            // given
            Ma ma = new Ma(Team.CHO);
            Board board = new BoardFixture()
                    .addPiece(5, 5, ma)
                    .build();

            // when
            boolean result = ma.canMoveConsideringObstacles(board, new Coordinate(5, 5), new Coordinate(4, 3));

            // then
            assertThat(result).isTrue();
        }
    }

    @Nested
    @DisplayName("경로를 찾는 경우의 수 테스트")
    class FindPathsTest {

        @Test
        @DisplayName("마가 (5,5) -> (3,4) OR (3,6)으로 이동할 때 (4,5)를 반환한다.")
        void test1() {
            // given
            Ma ma = new Ma(Team.HAN);

            // when
            Set<Coordinate> path1 = ma.findPaths(new Coordinate(5, 5), new Coordinate(3, 4));
            Set<Coordinate> path2 = ma.findPaths(new Coordinate(5, 5), new Coordinate(3, 6));

            // then
            assertThat(path1).containsOnly(new Coordinate(4, 5));
            assertThat(path2).containsOnly(new Coordinate(4, 5));
        }

        @Test
        @DisplayName("마가 (5,5) -> (4,3) OR (6,3)으로 이동할 때 (5,4)를 반환한다.")
        void test2() {
            // given
            Ma ma = new Ma(Team.HAN);

            // when
            Set<Coordinate> path1 = ma.findPaths(new Coordinate(5, 5), new Coordinate(4, 3));
            Set<Coordinate> path2 = ma.findPaths(new Coordinate(5, 5), new Coordinate(6, 3));

            // then
            assertThat(path1).containsOnly(new Coordinate(5, 4));
            assertThat(path2).containsOnly(new Coordinate(5, 4));
        }

        @Test
        @DisplayName("마가 (5,5) -> (7,4) OR (7,6)으로 이동할 때 (6,5)를 반환한다.")
        void test3() {
            // given
            Ma ma = new Ma(Team.HAN);

            // when
            Set<Coordinate> path1 = ma.findPaths(new Coordinate(5, 5), new Coordinate(7, 4));
            Set<Coordinate> path2 = ma.findPaths(new Coordinate(5, 5), new Coordinate(7, 6));

            // then
            assertThat(path1).containsOnly(new Coordinate(6, 5));
            assertThat(path2).containsOnly(new Coordinate(6, 5));
        }

        @Test
        @DisplayName("마가 (5,5) -> (4,7) OR (6,7)으로 이동할 때 (5,6)를 반환한다.")
        void test4() {
            // given
            Ma ma = new Ma(Team.HAN);

            // when
            Set<Coordinate> path1 = ma.findPaths(new Coordinate(5, 5), new Coordinate(4, 7));
            Set<Coordinate> path2 = ma.findPaths(new Coordinate(5, 5), new Coordinate(6, 7));

            // then
            assertThat(path1).containsOnly(new Coordinate(5, 6));
            assertThat(path2).containsOnly(new Coordinate(5, 6));
        }
    }
}
