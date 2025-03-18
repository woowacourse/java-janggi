import static org.assertj.core.api.Assertions.assertThat;

import model.Point;
import model.Sang;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class SangTest {
    @Test
    @DisplayName("상 기물 생성 테스트")
    public void test1() {
        String team = "red";

        Sang sang = new Sang(team);

        assertThat(sang.getTeam()).isEqualTo(team);
    }

    @Nested
    @DisplayName("상 이동 가능 여부 판별 테스트")
    class SangMovableTest {
        @Test
        @DisplayName("가능")
        public void test1() {
            Sang sang = new Sang("red");
            assertThat(sang.canMove(0, 0, -2, -3)).isTrue();
        }

        @Test
        @DisplayName("불가능")
        public void test2() {
            Sang sang = new Sang("red");
            assertThat(sang.canMove(0, 0, -2, -2)).isFalse();
        }
    }

    @Nested
    @DisplayName("상 이동 경로 계산 테스트")
    class MaCalculatePathTest {
        @Test
        @DisplayName("중간 경유지 포함 여부 테스트_1")
        public void test1() {
            Sang sang = new Sang("red");
            Point point = new Point(0, 1);    // 중간 경유지

            assertThat(sang.calculatePath(0, 0, 2, 3).contains(point)).isTrue();
        }

        @Test
        @DisplayName("중간 경유지 포함 여부 테스트_2")
        public void test2() {
            Sang sang = new Sang("red");
            Point point = new Point(1, 2);    // 중간 경유지

            assertThat(sang.calculatePath(0, 0, 2, 3).contains(point)).isTrue();
        }

        @Test
        @DisplayName("종점 포함 여부 테스트")
        public void test3() {
            Sang sang = new Sang("red");
            Point point = new Point(2, 3);

            assertThat(sang.calculatePath(0, 0, 2, 3).contains(point)).isTrue();
        }
    }
}
