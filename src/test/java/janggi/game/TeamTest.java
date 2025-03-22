package janggi.game;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class TeamTest {

    @Nested
    @DisplayName("팀 변경 테스트")
    class ChangeTest {

        @Test
        @DisplayName("현재 팀이 한나라라면 초나라로 변경할 수 있다.")
        void fromHanToCho() {
            Team currentTeam = Team.HAN;

            assertThat(currentTeam.reverse()).isEqualTo(Team.CHO);
        }

        @Test
        @DisplayName("현재 팀이 초나라라면 한나라로 변경할 수 있다.")
        void fromChoToHan() {
            Team currentTeam = Team.CHO;

            assertThat(currentTeam.reverse()).isEqualTo(Team.HAN);
        }
    }
}
