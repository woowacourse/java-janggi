package janggiGame.piece;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DynastyTest {
    @DisplayName("나라는 한나라(Han)와 초나라(Cho)로 나뉜다.")
    @Test
    void dynastyDivideHanCho() {
        // given
        Dynasty[] dynasties = Arrays.stream(Dynasty.values())
                .filter(dynasty -> dynasty != Dynasty.EMPTY)
                .toArray(Dynasty[]::new);

        // when
        int actual = dynasties.length;

        // then
        assertThat(actual).isEqualTo(2);
    }
}