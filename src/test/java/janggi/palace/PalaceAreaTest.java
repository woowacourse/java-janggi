package janggi.palace;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PalaceAreaTest {
    @DisplayName("정상: 참인 경우, 궁 안에 속함을 반환")
    @Test
    void fromTrue() {
        PalaceArea palaceArea = PalaceArea.from(true);

        assertThat(palaceArea).isEqualTo(PalaceArea.INSIDE);
    }

    @DisplayName("정상: 거짓인 경우, 궁 밖에 속함을 반환")
    @Test
    void fromFalse() {
        PalaceArea palaceArea = PalaceArea.from(false);

        assertThat(palaceArea).isEqualTo(PalaceArea.OUTSIDE);
    }
}
