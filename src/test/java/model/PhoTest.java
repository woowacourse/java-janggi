package model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class PhoTest {
    @Test
    @DisplayName("포 기물 생성 테스트")
    public void test1() {
        Team team = Team.RED;

        Pho Pho = new Pho(team);

        assertThat(Pho.getTeam()).isEqualTo(team);
    }

    @Nested
    @DisplayName("포 이동 가능 여부 판별 테스트")
    class PhoMovableTest {
        @Test
        @DisplayName("포 이동 가능 여부 판별 테스트")
        public void test2() {
            Pho Pho = new Pho(Team.RED);
            assertThat(Pho.isValidPoint(Point.of(0,0), Point.of(100,0))).isTrue();
        }

        @Test
        @DisplayName("포 이동 불가능 여부 판별 테스트")
        public void test3() {
            Pho Pho = new Pho(Team.RED);
            assertThat(Pho.isValidPoint(Point.of(0,0), Point.of(10,10))).isFalse();
        }
    }

    @Nested
    @DisplayName("포 이동 경로 계산 테스트")
    class PhoCalculatePathTest {
        @Test
        @DisplayName("수직 테스트")
        public void test1() {
            Pho Pho = new Pho(Team.RED);
            Point point1 = new Point(0, 1);
            Point point2 = new Point(0, 2);
            Point point3 = new Point(0, 3);
            Point point4 = new Point(0, 4);
            Point point5 = new Point(0, 7);

            Path path = Pho.calculatePath(Point.of(0,0), Point.of(0,7));

            assertAll(
                    () -> assertThat(path.contains(point1)).isTrue(),
                    () -> assertThat(path.contains(point2)).isTrue(),
                    () -> assertThat(path.contains(point3)).isTrue(),
                    () -> assertThat(path.contains(point4)).isTrue(),
                    () -> assertThat(path.contains(point5)).isTrue()
            );
        }

        @Test
        @DisplayName("수평 테스트")
        public void test2() {
            Pho Pho = new Pho(Team.RED);
            Point point1 = new Point(1, 0);
            Point point2 = new Point(2, 0);
            Point point3 = new Point(3, 0);
            Point point4 = new Point(6, 0);
            Point point5 = new Point(7, 0);

            Path path = Pho.calculatePath(Point.of(0,0), Point.of(7,0));

            assertAll(
                    () -> assertThat(path.contains(point1)).isTrue(),
                    () -> assertThat(path.contains(point2)).isTrue(),
                    () -> assertThat(path.contains(point3)).isTrue(),
                    () -> assertThat(path.contains(point4)).isTrue(),
                    () -> assertThat(path.contains(point5)).isTrue()
            );
        }
    }

    @Nested
    @DisplayName("포 경로 방해 테스트")
    class PhoIsProhibitedPathTest {
        @Nested
        @DisplayName("장애물이 없는 경우")
        class NoProhibitedPathTest {
            @Test
            void test1(){
                Pho pho = new Pho(Team.RED);
                Map<Piece, Boolean> pieces = new HashMap<>();
                assertThat(pho.canMove(pieces)).isTrue();
            }
        }
        @Nested
        @DisplayName("장애물이 1개 경우")
        class OneProhibitedPathTest {
            @Test
            @DisplayName("중간에 1개 - 포가 아닌 경우")
            void test2(){
                Pho pho = new Pho(Team.RED);
                Map<Piece, Boolean> pieces = new HashMap<>();
                pieces.put(new Cha(Team.BLUE), false);
                assertThat(pho.canMove(pieces)).isTrue();
            }

            @Test
            @DisplayName("중간에 1개 - 포인 경우")
            void test3(){
                Pho pho = new Pho(Team.RED);
                Map<Piece, Boolean> pieces = new HashMap<>();
                pieces.put(new Pho(Team.BLUE), false);
                assertThat(pho.canMove(pieces)).isFalse();
            }

            @Test
            @DisplayName("종점에 1개인 경우")
            void test4(){
                Pho pho = new Pho(Team.RED);
                Map<Piece, Boolean> pieces = new HashMap<>();
                pieces.put(new Cha(Team.BLUE), true);
                assertThat(pho.canMove(pieces)).isFalse();
            }
        }

        @Nested
        @DisplayName("장애물이 1개 경우")
        class TwoProhibitedPathTest {
            @Test
            @DisplayName("중간 2개")
            void test1(){
                Pho pho = new Pho(Team.RED);
                Map<Piece, Boolean> pieces = new HashMap<>();
                pieces.put(new Cha(Team.BLUE), false);
                pieces.put(new Jang(Team.BLUE), false);
                assertThat(pho.canMove(pieces)).isFalse();
            }

            @Test
            @DisplayName("포가 하나라도 존재")
            void test2(){
                Pho pho = new Pho(Team.RED);
                Map<Piece, Boolean> pieces = new HashMap<>();
                pieces.put(new Cha(Team.BLUE), true);
                pieces.put(new Pho(Team.BLUE), false);
                assertThat(pho.canMove(pieces)).isFalse();
            }

            @Test
            @DisplayName("포가 없고 종점에 아군")
            void test3(){
                Pho pho = new Pho(Team.RED);
                Map<Piece, Boolean> pieces = new HashMap<>();
                pieces.put(new Cha(Team.RED), true);
                pieces.put(new Ma(Team.BLUE), false);
                assertThat(pho.canMove(pieces)).isFalse();
            }

            @Test
            @DisplayName("포가 없고 종점에 적군")
            void test4(){
                Pho pho = new Pho(Team.RED);
                Map<Piece, Boolean> pieces = new HashMap<>();
                pieces.put(new Cha(Team.RED), false);
                pieces.put(new Ma(Team.BLUE), true);
                assertThat(pho.canMove(pieces)).isTrue();
            }
        }

        @Nested
        @DisplayName("장애물이 3개 이상인 경우")
        class ThreeProhibitedPathTest {
            @Test
            void test1() {
                Pho pho = new Pho(Team.RED);
                Map<Piece, Boolean> pieces = new HashMap<>();
                pieces.put(new Cha(Team.RED), false);
                pieces.put(new Cha(Team.RED), false);
                pieces.put(new Cha(Team.RED), false);
                pieces.put(new Ma(Team.BLUE), true);
                assertThat(pho.canMove(pieces)).isFalse();
            }
        }

    }
}
