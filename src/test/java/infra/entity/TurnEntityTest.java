package infra.entity;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class TurnEntityTest {

    @Nested
    class ValidCases {

        @Test
        @DisplayName("TurnEntity는 id가 같으면 같다.")
        void equals_and_hashCode() {
            // given
            TurnEntity turn1 = new TurnEntity(1L, "GREEN");
            TurnEntity turn2 = new TurnEntity(1L, "GREEN");
            TurnEntity turn3 = new TurnEntity(2L, "GREEN");

            // when & then
            assertSoftly(softly -> {
                softly.assertThat(turn1).isEqualTo(turn2);
                softly.assertThat(turn1.hashCode()).isEqualTo(turn2.hashCode());

                softly.assertThat(turn1).isNotEqualTo(turn3);
                softly.assertThat(turn1.hashCode()).isNotEqualTo(turn3.hashCode());
            });
        }
    }

    @Nested
    class InvalidCases {

        @ParameterizedTest
        @DisplayName("TurnEntity는 team이 null 또는 공백일 수 없다.")
        @ValueSource(
            strings = {
                "",
                " "
            }
        )
        @NullSource
        void validateNotBlank(String team) {
            // when & then
            assertThatThrownBy(() -> new TurnEntity(team))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("team은 null이거나 공백일 수 없습니다.");
        }
    }
}
