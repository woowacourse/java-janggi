import static org.assertj.core.api.Assertions.assertThat;

import model.Sa;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SaTest {
    @Test
    @DisplayName("사 기물 생성 테스트")
    public void test1() {
        String team = "red";

        Sa sa = new Sa(team);

        assertThat(sa.getTeam()).isEqualTo(team);
    }

    @Test
    @DisplayName("사 이동 가능 여부 판별 테스트")
    public void test2() {
        Sa sa = new Sa("red");
        assertThat(sa.canMove(0, 0, 1, 0)).isTrue();
    }
}
