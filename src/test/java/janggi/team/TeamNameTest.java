package janggi.team;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TeamNameTest {

    @DisplayName("정상: 팀 이름을 반환")
    @Test
    void checkTeamName() {
        assertThatCode(() -> TeamName.from("cho"))
                .doesNotThrowAnyException();
    }

    @DisplayName("예외: 팀 이름이 존재하지 않는 경우")
    @Test
    void checkInvalidTeamName() {
        assertThatThrownBy(() -> TeamName.from("invalid"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
