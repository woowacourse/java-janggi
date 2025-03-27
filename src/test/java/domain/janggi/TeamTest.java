package domain.janggi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class TeamTest {

    @Nested
    class ValidCases {

        @DisplayName("현재 팀의 반대 팀을 반환한다.")
        @Test
        void opposite() {
            assertAll(
                    () -> assertThat(Team.GREEN.opposite()).isEqualTo(Team.RED),
                    () -> assertThat(Team.RED.opposite()).isEqualTo(Team.GREEN)
            );
        }

        @DisplayName("Team의 name에 해당하는 Team을 반환한다.")
        @Test
        void from() {
            assertAll(
                    () -> assertThat(Team.from("green")).isEqualTo(Team.GREEN),
                    () -> assertThat(Team.from("red")).isEqualTo(Team.RED)
            );
        }
    }

    @Nested
    class InvalidCases {

        @DisplayName("Team의 name에 해당하는 Team이 없다면 예외가 발생한다.")
        @Test
        void from() {
            assertThatThrownBy(() -> Team.from("yello"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("해당하는 팀이 없습니다.");
        }
    }
}
