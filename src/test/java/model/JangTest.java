package model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class JangTest {

    @Test
    @DisplayName("장 기물 생성 테스트")
    public void test1() {
        String team = "red";

        Jang jang = new Jang(team);

        assertThat(jang.getTeam()).isEqualTo(team);
    }

    @Nested
    @DisplayName("장 이동 가능 여부 판별 테스트")
    class JangMovableTest {
        @Test
        @DisplayName("장 이동 가능 테스트")
        public void test2() {
            Jang jang = new Jang("red");
            assertThat(jang.isValidPoint(0, 0, 1, 0)).isTrue();
        }

        @Test
        @DisplayName("장 이동 불가능 테스트")
        public void test3() {
            Jang jang = new Jang("red");
            assertThat(jang.isValidPoint(0, 0, 2, 0)).isFalse();
        }
    }
    @Nested
    @DisplayName("장 이동 경로 계산 테스트")
    class JangCalculatePathTest {
        @Test
        @DisplayName("수직")
        public void test1() {
            Jang jang = new Jang("red");
            Point point =new Point(0,1);

            assertThat(jang.calculatePath(0,0,0,1).contains(point)).isTrue();
        }

        @Test
        @DisplayName("수평")
        public void test2() {
            Jang jang = new Jang("red");
            Point point =new Point(1,0);

            assertThat(jang.calculatePath(0,0,1,0).contains(point)).isTrue();
        }
    }

}
