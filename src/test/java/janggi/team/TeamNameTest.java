package janggi.team;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TeamNameTest {

    @DisplayName("정상: 팀 이름이 일치하는지 확인")
    @Test
    void matchTeamName() {
        TeamName teamName = TeamName.CHO;

        assertThat(teamName.matchTeamName("초")).isTrue();
    }

    @DisplayName("정상: 팀 이름을 반환하는지 확인")
    @Test
    void convertTeamName() {
        assertThatCode(() -> TeamName.from("초")).doesNotThrowAnyException();
    }

    @DisplayName("예외: 존재하지 않는 팀 이름인 경우")
    @Test
    void invalidTeamName() {
        assertThatThrownBy(() -> TeamName.from("테스트"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
