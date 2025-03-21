package model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.HashMap;
import java.util.Map;
import model.piece.Cha;
import model.piece.Piece;
import model.piece.Sang;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class SangTest {
    @Test
    @DisplayName("상 기물 생성 테스트")
    public void test1() {
        Team team = Team.RED;

        Sang sang = new Sang(team);

        assertThat(sang.getTeam()).isEqualTo(team);
    }

    @Nested
    @DisplayName("상 이동 가능 여부 판별 테스트")
    class SangMovableTest {
        @Test
        @DisplayName("가능")
        public void test1() {
            Sang sang = new Sang(Team.RED);
            assertThat(sang.isValidPoint(Point.of(0,0), Point.of(-2,-3))).isTrue();
        }

        @Test
        @DisplayName("불가능")
        public void test2() {
            Sang sang = new Sang(Team.RED);
            assertThat(sang.isValidPoint(Point.of(0,0), Point.of(-2,-2))).isFalse();
        }
    }

    @Nested
    @DisplayName("상 이동 경로 계산 테스트")
    class MaCalculatePathTest {

        @Test
        @DisplayName("경로 테스트_1")
        public void test1() {
            Sang sang = new Sang(Team.RED);
            Point point = new Point(0, 1);
            Point point2 = new Point(1, 2);
            Point point3 = new Point(2, 3);


            Path path = sang.calculatePath(Point.of(0,0), Point.of(2,3));
            assertAll(
                    () -> assertThat(path.contains(point)).isTrue(),
                    () -> assertThat(path.contains(point2)).isTrue(),
                    () -> assertThat(path.contains(point3)).isTrue()
            );
        }

        @Test
        @DisplayName("경로 테스트_2")
        public void test2() {
            Sang sang = new Sang(Team.RED);
            Point point = new Point(0, -1);
            Point point2 = new Point(-1, -2);
            Point point3 = new Point(-2, -3);
            Path path = sang.calculatePath(Point.of(0,0), Point.of(-2,-3));
            assertAll(
                    () -> assertThat(path.contains(point)).isTrue(),
                    () -> assertThat(path.contains(point2)).isTrue(),
                    () -> assertThat(path.contains(point3)).isTrue()
            );
        }
    }

    @Nested
    @DisplayName("상 경로 방해 테스트")
    class SangIsProhibitedPathTest {

        @Test
        @DisplayName("중간 아군")
        public void test1() {
            Sang sang = new Sang(Team.RED);
            Map<Piece,Boolean> pieces = new HashMap<>();
            pieces.put(new Cha(Team.RED),false);

            assertThat(sang.canMove(pieces)).isFalse();
        }

        @Test
        @DisplayName("중간 아군 2명")
        public void test5() {
            Sang sang = new Sang(Team.RED);
            Map<Piece,Boolean> pieces = new HashMap<>();
            pieces.put(new Cha(Team.RED),false);
            pieces.put(new Cha(Team.RED),false);

            assertThat(sang.canMove(pieces)).isFalse();
        }

        @Test
        @DisplayName("종점 아군")
        public void test2() {
            Sang sang = new Sang(Team.RED);
            Map<Piece,Boolean> pieces = new HashMap<>();
            pieces.put(new Cha(Team.RED),true);

            assertThat(sang.canMove(pieces)).isFalse();
        }


        @Test
        @DisplayName("중간 적군")
        public void test3() {
            Sang sang = new Sang(Team.RED);
            Map<Piece,Boolean> pieces = new HashMap<>();
            pieces.put(new Cha(Team.BLUE),false);

            assertThat(sang.canMove(pieces)).isFalse();
        }

        @Test
        @DisplayName("종점 적군")
        public void test4() {
            Sang sang = new Sang(Team.RED);
            Map<Piece,Boolean> pieces = new HashMap<>();
            pieces.put(new Cha(Team.BLUE),true);

            assertThat(sang.canMove(pieces)).isTrue();
        }
    }
}
