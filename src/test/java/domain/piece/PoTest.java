package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Board;
import domain.Coordinate;
import domain.Team;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PoTest {

    @Test
    @DisplayName("포의 출발 좌표가 (5,5)일 때 이동 가능한 좌표 후보 13개를 반환한다.")
    void test1() {
        // given
        Po po = new Po(Team.HAN);

        // when
        Set<Coordinate> movableCandidates = po.findMovableCandidates(new Coordinate(5, 5));

        // then
        assertThat(movableCandidates).containsOnly(
                new Coordinate(1, 5),
                new Coordinate(2, 5),
                new Coordinate(3, 5), // 왼쪽

                new Coordinate(7, 5),
                new Coordinate(8, 5),
                new Coordinate(9, 5), // 오른쪽

                new Coordinate(5, 1),
                new Coordinate(5, 2),
                new Coordinate(5, 3), // 위쪽

                new Coordinate(5, 7),
                new Coordinate(5, 8),
                new Coordinate(5, 9),
                new Coordinate(5, 10) // 아래쪽
        );
    }

    @Nested
    @DisplayName("장애물을 고려한 움직임 가능 여부 테스트")
    class CanMoveConsideringObstaclesTest {

        @Test
        @DisplayName("포가 (5,5) -> (8,5) 으로 이동할 때 장애물이 하나가 아닐 경우 false를 반환한다.")
        void test1() {
            // given
            Po po = new Po(Team.HAN);
            Board board = new BoardBuilder()
                    .addPiece(5, 5, po)
                    .addPiece(6, 5, new Ma(Team.CHO))
                    .addPiece(7, 5, new Sang(Team.CHO))
                    .build();

            // when
            boolean result = po.canMoveConsideringObstacles(board, new Coordinate(5, 5), new Coordinate(8, 5));

            // then
            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("포가 (5,5) -> (8,5) 으로 이동할 때 장애물이 하나이면서 그 장애물이 포인 경우 false를 반환한다.")
        void test2() {
            // given
            Po po = new Po(Team.HAN);
            Board board = new BoardBuilder()
                    .addPiece(5, 5, po)
                    .addPiece(7, 5, new Po(Team.CHO))
                    .build();

            // when
            boolean result = po.canMoveConsideringObstacles(board, new Coordinate(5, 5), new Coordinate(8, 5));

            // then
            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("포가 (5,5) -> (8,5) 으로 이동할 때 장애물이 하나이면서 그 장애물이 포가 아니면서 도착 좌표에 포가 있으면 false를 반환한다.")
        void test3() {
            // given
            Po po = new Po(Team.HAN);
            Board board = new BoardBuilder()
                    .addPiece(5, 5, po)
                    .addPiece(6, 5, new Ma(Team.CHO))
                    .addPiece(8, 5, new Po(Team.CHO))
                    .build();

            // when
            boolean result = po.canMoveConsideringObstacles(board, new Coordinate(5, 5), new Coordinate(8, 5));

            // then
            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("포가 (5,5) -> (8,5) 으로 이동할 때 장애물이 하나이면서 그 장애물이 포가 아니면서 도착 좌표에 포가 없을 경우 true를 반환한다.")
        void test4() {
            // given
            Po po = new Po(Team.HAN);
            Board board = new BoardBuilder()
                    .addPiece(5, 5, po)
                    .addPiece(6, 5, new Ma(Team.CHO))
                    .addPiece(8, 5, new Sang(Team.CHO))
                    .build();

            // when
            boolean result = po.canMoveConsideringObstacles(board, new Coordinate(5, 5), new Coordinate(8, 5));

            // then
            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("포가 (5,5) -> (8,5) 으로 이동할 때 장애물이 하나이면서 그 장애물이 포가 아니면서 도착 좌표에 피스가 없을 경우 true를 반환한다.")
        void test5() {
            // given
            Po po = new Po(Team.HAN);
            Board board = new BoardBuilder()
                    .addPiece(5, 5, po)
                    .addPiece(7, 5, new Sang(Team.CHO))
                    .build();

            // when
            boolean result = po.canMoveConsideringObstacles(board, new Coordinate(5, 5), new Coordinate(8, 5));

            // then
            assertThat(result).isTrue();
        }
    }

    @Nested
    @DisplayName("경로를 찾는 경우의 수 테스트")
    class FindPathsTest {

        @Test
        @DisplayName("포가 (5,5) -> (5,1) 으로 이동할 때 (5,4), (5,3), (5,2)를 반환한다.")
        void test1() {
            Po po = new Po(Team.HAN);

            // when
            Set<Coordinate> paths = po.findPaths(new Coordinate(5, 5), new Coordinate(5, 1));

            // then
            assertThat(paths).containsOnly(
                    new Coordinate(5, 4),
                    new Coordinate(5, 3),
                    new Coordinate(5, 2)
            );
        }

        @Test
        @DisplayName("포가 (5,5) -> (5,10) 으로 이동할 때 (5,6), (5,7), (5,8), (5,9)를 반환한다.")
        void test2() {
            Po po = new Po(Team.HAN);

            // when
            Set<Coordinate> paths = po.findPaths(new Coordinate(5, 5), new Coordinate(5, 10));

            // then
            assertThat(paths).containsOnly(
                    new Coordinate(5, 6),
                    new Coordinate(5, 7),
                    new Coordinate(5, 8),
                    new Coordinate(5, 9)
            );
        }

        @Test
        @DisplayName("포가 (5,5) -> (1,5) 으로 이동할 때 (2,5), (3,5), (4,5)를 반환한다.")
        void test3() {
            Po po = new Po(Team.HAN);

            // when
            Set<Coordinate> paths = po.findPaths(new Coordinate(5, 5), new Coordinate(1, 5));

            // then
            assertThat(paths).containsOnly(
                    new Coordinate(2, 5),
                    new Coordinate(3, 5),
                    new Coordinate(4, 5)
            );
        }

        @Test
        @DisplayName("포가 (5,5) -> (9,5) 으로 이동할 때 (6,5), (7,5), (8,5)를 반환한다.")
        void test4() {
            Po po = new Po(Team.HAN);

            // when
            Set<Coordinate> paths = po.findPaths(new Coordinate(5, 5), new Coordinate(9, 5));

            // then
            assertThat(paths).containsOnly(
                    new Coordinate(6, 5),
                    new Coordinate(7, 5),
                    new Coordinate(8, 5)
            );
        }
    }
}
