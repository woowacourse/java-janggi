package domain;

import static org.assertj.core.api.Assertions.assertThat;
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
    }
}
