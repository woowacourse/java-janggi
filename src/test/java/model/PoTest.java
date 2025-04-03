package model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.HashMap;
import java.util.Map;
import model.piece.Cha;
import model.piece.Jang;
import model.piece.Ma;
import model.piece.Po;
import model.piece.Piece;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PoTest {
    @Test
    @DisplayName("포 기물 생성 테스트")
    void test1() {
        Team team = Team.RED;

        Po po = new Po(team);

        assertThat(po.getTeam()).isEqualTo(team);
    }

    @Nested
    @DisplayName("포 이동 가능 여부 판별 테스트")
    class PoMovableTest {
        @Test
        @DisplayName("포 이동 가능 여부 판별 테스트")
        void test2() {
            Po Po = new Po(Team.RED);
            assertThat(Po.isValidPoint(Point.of(0,0), Point.of(100,0))).isTrue();
        }

        @Test
        @DisplayName("포 이동 가능 여부 판별 테스트")
        void test4() {
            Po Po = new Po(Team.RED);
            assertThat(Po.isValidPoint(Point.of(0, 0), Point.of(100, 0))).isTrue();
        }

        @Test
        @DisplayName("포 이동 불가능 여부 판별 테스트")
        void test3() {
            Po Po = new Po(Team.RED);
            assertThatThrownBy(() -> Po.isValidPoint(Point.of(0, 0), Point.of(10, 10))).isInstanceOf(
                    IllegalArgumentException.class);
        }

        @Test
        @DisplayName("궁성 안에서 포 이동 가능 여부 판별 테스트")
        void test5() {
            Po Po = new Po(Team.BLUE);
            assertThat(Po.isValidPoint(Point.of(3, 0), Point.of(5, 2))).isTrue();
        }

        @Test
        @DisplayName("궁성 안에서 포 이동 불가능 여부 판별 테스트")
        void test6() {
            Po Po = new Po(Team.BLUE);
            assertThatThrownBy(() -> Po.isValidPoint(Point.of(3, 1), Point.of(4, 2))).isInstanceOf(
                    IllegalArgumentException.class);
        }

        @Test
        @DisplayName("궁성 안에서 포 이동 가능 여부 판별 테스트")
        void test7() {
            Po Po = new Po(Team.BLUE);
            assertThat(Po.isValidPoint(Point.of(3, 1), Point.of(3, 9))).isTrue();
        }
    }

    @Nested
    @DisplayName("포 이동 경로 계산 테스트")
    class PoCalculatePathTest {
        @Test
        @DisplayName("수직으로 이동 가능 테스트")
        void test1() {
            Po po = new Po(Team.RED);
            Point point1 = new Point(0, 1);
            Point point2 = new Point(0, 2);
            Point point3 = new Point(0, 3);
            Point point4 = new Point(0, 4);
            Point point5 = new Point(0, 7);

            Path path = po.calculatePath(Point.of(0, 0), Point.of(0, 7));

            assertAll(
                    () -> assertThat(path.contains(point1)).isTrue(),
                    () -> assertThat(path.contains(point2)).isTrue(),
                    () -> assertThat(path.contains(point3)).isTrue(),
                    () -> assertThat(path.contains(point4)).isTrue(),
                    () -> assertThat(path.contains(point5)).isTrue()
            );
        }

        @Test
        @DisplayName("수평으로 이동 가능 테스트")
        void test2() {
            Po po = new Po(Team.RED);
            Point point1 = new Point(1, 0);
            Point point2 = new Point(2, 0);
            Point point3 = new Point(3, 0);
            Point point4 = new Point(6, 0);
            Point point5 = new Point(7, 0);

            Path path = po.calculatePath(Point.of(0, 0), Point.of(7, 0));

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
    class PoIsProhibitedPathTest {
        @Nested
        @DisplayName("장애물이 없는 경우  이동 가능 테스트")
        class NoProhibitedPathTest {
            @Test
            void test1(){
                Po po = new Po(Team.RED);
                Map<Piece, Boolean> pieces = new HashMap<>();
                assertThat(po.canMove(pieces)).isFalse();
            }
        }
        @Nested
        @DisplayName("장애물이 1개 경우")
        class OneProhibitedPathTest {
            @Test
            @DisplayName("중간에 1개 - 포가 아닌 경우 이동 가능 테스트")
            void test2(){
                Po po = new Po(Team.RED);
                Map<Piece, Boolean> pieces = new HashMap<>();
                pieces.put(new Cha(Team.BLUE), false);
                assertThat(po.canMove(pieces)).isTrue();
            }

            @Test
            @DisplayName("중간에 1개 - 포인 경우인 경우 이동 불가능 테스트")
            void test3(){
                Po po = new Po(Team.RED);
                Map<Piece, Boolean> pieces = new HashMap<>();
                pieces.put(new Po(Team.BLUE), false);
                assertThat(po.canMove(pieces)).isFalse();
            }

            @Test
            @DisplayName("종점에 1개인 경우 이동 불가능 테스트")
            void test4(){
                Po po = new Po(Team.RED);
                Map<Piece, Boolean> pieces = new HashMap<>();
                pieces.put(new Cha(Team.BLUE), true);
                assertThat(po.canMove(pieces)).isFalse();
            }
        }

        @Nested
        @DisplayName("장애물이 1개 경우")
        class TwoProhibitedPathTest {
            @Test
            @DisplayName("중간 2개인 경우 이동 불가능 테스트")
            void test1(){
                Po po = new Po(Team.RED);
                Map<Piece, Boolean> pieces = new HashMap<>();
                pieces.put(new Cha(Team.BLUE), false);
                pieces.put(new Jang(Team.BLUE), false);
                assertThat(po.canMove(pieces)).isFalse();
            }

            @Test
            @DisplayName("포가 하나라도 존재인 경우 이동 불가능 테스트")
            void test2(){
                Po po = new Po(Team.RED);
                Map<Piece, Boolean> pieces = new HashMap<>();
                pieces.put(new Cha(Team.BLUE), true);
                pieces.put(new Po(Team.BLUE), false);
                assertThat(po.canMove(pieces)).isFalse();
            }

            @Test
            @DisplayName("포가 없고 종점에 아군인 경우 이동 불가능 테스트")
            void test3(){
                Po po = new Po(Team.RED);
                Map<Piece, Boolean> pieces = new HashMap<>();
                pieces.put(new Cha(Team.RED), true);
                pieces.put(new Ma(Team.BLUE), false);
                assertThat(po.canMove(pieces)).isFalse();
            }

            @Test
            @DisplayName("포가 없고 종점에 적군인 경우 이동 가능 테스트")
            void test4(){
                Po po = new Po(Team.RED);
                Map<Piece, Boolean> pieces = new HashMap<>();
                pieces.put(new Cha(Team.RED), false);
                pieces.put(new Ma(Team.BLUE), true);
                assertThat(po.canMove(pieces)).isTrue();
            }
        }

        @Nested
        @DisplayName("장애물이 3개 이상인 경우 이동 불가능 테스트")
        class ThreeProhibitedPathTest {
            @Test
            void test1() {
                Po po = new Po(Team.RED);
                Map<Piece, Boolean> pieces = new HashMap<>();
                pieces.put(new Cha(Team.RED), false);
                pieces.put(new Cha(Team.RED), false);
                pieces.put(new Cha(Team.RED), false);
                pieces.put(new Ma(Team.BLUE), true);
                assertThat(po.canMove(pieces)).isFalse();
            }
        }

    }
}
