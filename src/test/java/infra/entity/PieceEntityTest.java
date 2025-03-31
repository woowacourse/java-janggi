package infra.entity;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

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
}
