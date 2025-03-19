import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class PlayerTest {

    @Nested
    @DisplayName("플레이어를 생성할 때")
    class testMakeTeam {

        @Test
        @DisplayName("한나라와 초나라 중 하나의 팀을 갖는다")
        void testNewTeam() {
            // given
            Player player = new Player(Team.CHO);

            // when
            Team team = player.getTeam();

            // then
            assertThat(team).isEqualTo(Team.CHO);
        }

    }
}
