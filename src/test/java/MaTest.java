import static org.assertj.core.api.Assertions.assertThat;

import model.Ma;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MaTest {
    @Test
    @DisplayName("마 기물 생성 테스트")
    public void test1() {
        String team = "red";

        Ma ma = new Ma(team);

        assertThat(ma.getTeam()).isEqualTo(team);
    }

    @Test
    @DisplayName("마 이동 가능 여부 판별 테스트")
    public void test2() {
        Ma ma = new Ma("red");
        assertThat(ma.canMove(0, 0, 2, 1)).isTrue();
    }
}
