package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TeamTest {

    @DisplayName("팀을 뒤바꿀 수 있다.")
    @Test
    void changeTeam() {
        //given
        final Team han = Team.HAN;

        //when
        final Team actual = han.changeTeam();

        //then
        assertThat(actual).isEqualTo(Team.CHO);
    }

}
