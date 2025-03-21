package model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import model.piece.Cha;
import model.piece.Jang;
import model.piece.Piece;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class JangTest {

    @Test
    @DisplayName("장 기물 생성 테스트")
    public void test1() {
        Team team = Team.RED;

        Jang jang = new Jang(team);

        assertThat(jang.getTeam()).isEqualTo(team);
    }

    @Nested
    @DisplayName("장 이동 가능 여부 판별 테스트")
    class JangMovableTest {
        @Test
        @DisplayName("장 이동 가능 테스트")
        public void test2() {
            Jang jang = new Jang(Team.RED);
            assertThat(jang.isValidPoint(Point.of(0, 0), Point.of(1, 0))).isTrue();
        }

        @Test
        @DisplayName("장 이동 불가능 테스트")
        public void test3() {
            Jang jang = new Jang(Team.RED);
            assertThat(jang.isValidPoint(Point.of(0, 0), Point.of(2, 0))).isFalse();
        }
    }

    @Nested
    @DisplayName("장 이동 경로 계산 테스트")
    class JangCalculatePathTest {
        @Test
        @DisplayName("수직")
        public void test1() {
            Jang jang = new Jang(Team.RED);
            Point point = new Point(0, 1);

            assertThat(jang.calculatePath(Point.of(0, 0), Point.of(0, 1)).contains(point)).isTrue();
        }

        @Test
        @DisplayName("수평")
        public void test2() {
            Jang jang = new Jang(Team.RED);
            Point point = new Point(1, 0);

            assertThat(jang.calculatePath(Point.of(0, 0), Point.of(1, 0)).contains(point)).isTrue();
        }
    }

    @Nested
    @DisplayName("장 경로 방해 테스트")
    class JangIsProhibitedPathTest {

        @Test
        @DisplayName("아군")
        public void test3() {
            Jang jang = new Jang(Team.RED);
            Map<Piece, Boolean> pieces = new HashMap<>();
            pieces.put(new Cha(Team.RED), true);

            assertThat(jang.canMove(pieces)).isFalse();
        }

        @Test
        @DisplayName("적군")
        public void test4() {
            Jang jang = new Jang(Team.RED);
            Map<Piece, Boolean> pieces = new HashMap<>();
            pieces.put(new Cha(Team.BLUE), true);
            assertThat(jang.canMove(pieces)).isTrue();
        }
    }

}
