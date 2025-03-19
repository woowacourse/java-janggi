package model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class SaTest {
    @Test
    @DisplayName("사 기물 생성 테스트")
    public void test1() {
        Team team = Team.RED;

        Sa sa = new Sa(team);

        assertThat(sa.getTeam()).isEqualTo(team);
    }

    @Nested
    @DisplayName("사 이동 가능 여부 판별 테스트")
    class SaMovableTest {
        @Test
        @DisplayName("사 이동 가능 테스트")
        public void test2() {
            Sa sa = new Sa(Team.RED);
            assertThat(sa.isValidPoint(0, 0, 1, 0)).isTrue();
        }

        @Test
        @DisplayName("사 이동 불가능 테스트")
        public void test3() {
            Sa sa = new Sa(Team.RED);
            assertThat(sa.isValidPoint(0, 0, 2, 0)).isFalse();
        }
    }
    @Nested
    @DisplayName("장 이동 경로 계산 테스트")
    class SaCalculatePathTest {
        @Test
        @DisplayName("수직")
        public void test1() {
            Sa sa = new Sa(Team.RED);
            Point point =new Point(0,1);

            assertThat(sa.calculatePath(0,0,0,1).contains(point)).isTrue();
        }

        @Test
        @DisplayName("수평")
        public void test2() {
            Sa sa = new Sa(Team.RED);
            Point point =new Point(1,0);

            assertThat(sa.calculatePath(0,0,1,0).contains(point)).isTrue();
        }
    }
}
