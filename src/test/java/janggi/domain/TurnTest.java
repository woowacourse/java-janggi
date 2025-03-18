package janggi.domain;

import static janggi.domain.Team.BLUE;
import static janggi.domain.Team.RED;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TurnTest {

    @Test
    @DisplayName("올바른 팀 사이즈를 검증합니다.")
    void validateTeamSizeTest() {

        //given

        //when & then
        assertThatThrownBy(() -> new Turn(List.of(RED, RED, BLUE)))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("올바른 팀 사이즈가 아닙니다.");
    }
}
