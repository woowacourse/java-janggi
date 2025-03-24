package player;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static player.Nation.CHO;
import static player.Nation.HAN;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NationTest {

    @Test
    @DisplayName("수비 팀 선정 테스트")
    void getDefenseTeamTest() {

        //when-then
        assertThat(CHO.getDefenseNation()).isEqualTo(HAN);
        assertThat(HAN.getDefenseNation()).isEqualTo(CHO);
    }

}
