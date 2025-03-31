package infra.entity;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PieceEntityTest {

    @Nested
    class ValidCases {

        @Test
        @DisplayName("PieceEntity는 id가 같으면 같다.")
        void equals_and_hashCode() {
            // given
            PieceEntity piece1 = new PieceEntity(1L, "CANNON", "RED", 0, 0);
            PieceEntity piece2 = new PieceEntity(1L, "CANNON", "RED", 0, 0);
            PieceEntity piece3 = new PieceEntity(2L, "CANNON", "RED", 0, 0);

            // when & then
            assertSoftly(softly -> {
                softly.assertThat(piece1).isEqualTo(piece2);
                softly.assertThat(piece1.hashCode()).isEqualTo(piece2.hashCode());

                softly.assertThat(piece1).isNotEqualTo(piece3);
                softly.assertThat(piece1.hashCode()).isNotEqualTo(piece3.hashCode());
            });
        }
    }

    @Nested
    class InvalidCases {

        @ParameterizedTest
        @DisplayName("PieceEntity는 dtype 또는 team이 null 또는 공백일 수 없다.")
        @CsvSource(
            {
                ", RED",
                "'', RED",
                "CANNON, ",
                "CANNON, ''",
                " , "
            }
        )
        void validateNotBlank(
            String dtype,
            String team
        ) {
            // when & then
            assertThatThrownBy(() -> new PieceEntity(dtype, team, 0, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("dtype과 team은 null이거나 공백일 수 없습니다.");
        }

        @ParameterizedTest
        @DisplayName("PieceEntity는 columnIndex 또는 rowIndex가 음수일 수 없다.")
        @CsvSource(
            {
                "-1, 0",
                "0, -1",
                "-5, -3"
            }
        )
        void validateNonNegative(
            int columnIndex,
            int rowIndex
        ) {
            // when & then
            assertThatThrownBy(() -> new PieceEntity("HORSE", "GREEN", columnIndex, rowIndex))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("0 이상");
        }
    }
}
