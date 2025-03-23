package janggi.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class PlayerTest {

    @Test
    @DisplayName("플레이어는 이름과 팀을 기반으로 생성한다")
    void createPlayerWithName() {
        //given
        String name = "flint";
        Team team = Team.RED;

        //when
        Player player = new Player(name, team);

        //then
        assertAll(() -> {
            assertThat(player.getName()).isEqualTo(name);
            assertThat(player.getTeam()).isEqualTo(team);
        });
    }
}
