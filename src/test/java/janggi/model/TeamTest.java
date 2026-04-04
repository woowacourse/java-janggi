package janggi.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TeamTest {

    @DisplayName("displayName으로 Team을 반환한다.")
    @Test
    void from_cho() {
        assertThat(Team.from("초")).isEqualTo(Team.CHO);
    }

    @DisplayName("displayName으로 Team을 반환한다.")
    @Test
    void from_han() {
        assertThat(Team.from("한")).isEqualTo(Team.HAN);
    }

    @DisplayName("존재하지 않는 displayName이면 예외가 발생한다.")
    @Test
    void from_invalid() {
        assertThatThrownBy(() -> Team.from("없음"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("알 수 없는 팀입니다.");
    }

    @DisplayName("한나라 덤 점수는 1.5점이다.")
    @Test
    void bonusScore_han() {
        assertThat(Team.HAN.bonusScore()).isEqualTo(new Score(1.5));
    }

    @DisplayName("초나라 덤 점수는 0점이다.")
    @Test
    void bonusScore_cho() {
        assertThat(Team.CHO.bonusScore()).isEqualTo(Score.zero());
    }
}
