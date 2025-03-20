package janggiGame.piece;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DynastyTest {
    @DisplayName("나라는 한나라(Han)와 초나라(Cho)로 나뉜다.")
    @Test
    void dynastyDivideHanCho() {
        // given
        Dynasty[] dynasties = Dynasty.values();

        // when
        int actual = dynasties.length;

        // then
        assertThat(actual).isEqualTo(2);
    }
}