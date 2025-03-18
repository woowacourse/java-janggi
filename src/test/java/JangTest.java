import static org.assertj.core.api.Assertions.assertThat;

import model.Jang;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JangTest {


    @Test
    @DisplayName("장 기물 생성 테스트")
    public void test1() {
        String team = "red";

        Jang jang = new Jang(team);

        assertThat(jang.getTeam()).isEqualTo(team);
    }

    @Test
    @DisplayName("장 이동 가능 여부 판별 테스트")
    public void test2() {
        Jang jang = new Jang("red");
        assertThat(jang.canMove(0, 0, 1, 0)).isTrue();
    }
}
