import static org.assertj.core.api.Assertions.assertThat;

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

}
