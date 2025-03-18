import static org.assertj.core.api.Assertions.assertThat;

import model.Jang;
import model.Ma;
import model.Point;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class MaTest {
    @Test
    @DisplayName("마 기물 생성 테스트")
    public void test1() {
        String team = "red";

        Ma ma = new Ma(team);

        assertThat(ma.getTeam()).isEqualTo(team);
    }

    @Nested
    @DisplayName("마 이동 가능 여부 판별 테스트")
    class MaMovableTest {
        @Test
        @DisplayName("가능")
        public void test1() {
            Ma ma = new Ma("red");
            assertThat(ma.canMove(0, 0, 2, 1)).isTrue();
        }

        @Test
        @DisplayName("불가능")
        public void test2() {
            Ma ma = new Ma("red");
            assertThat(ma.canMove(0, 0, 2, 2)).isFalse();
        }
    }

    @Nested
    @DisplayName("마 이동 경로 계산 테스트")
    class MaCalculatePathTest {
        @Test
        @DisplayName("중간 경유지 포함 여부 테스트")
        public void test1() {
            Ma ma = new Ma("red");
            Point point =new Point(0,1);    // 중간 경유지

            assertThat(ma.calculatePath(0,0,1,2).contains(point)).isTrue();
        }

        @Test
        @DisplayName("종점 포함 여부 테스트")
        public void test2() {
            Ma ma = new Ma("red");
            Point point =new Point(1,2);

            assertThat(ma.calculatePath(0,0,1,2).contains(point)).isTrue();
        }
    }

}
