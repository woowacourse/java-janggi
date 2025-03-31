package infra.entity;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

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
}
