package domain.board;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Col 열거형 테스트")
class ColTest {

    @Test
    @DisplayName("toCol: 소문자 a-i를 올바른 Col로 변환한다")
    void toColConvertsLowercaseChar() {
        assertThat(Col.toCol('a')).isEqualTo(Col.A);
        assertThat(Col.toCol('e')).isEqualTo(Col.E);
        assertThat(Col.toCol('i')).isEqualTo(Col.I);
    }

    @Test
    @DisplayName("toCol: 대문자 A-I를 올바른 Col로 변환한다")
    void toColConvertsUppercaseChar() {
        assertThat(Col.toCol('A')).isEqualTo(Col.A);
        assertThat(Col.toCol('E')).isEqualTo(Col.E);
        assertThat(Col.toCol('I')).isEqualTo(Col.I);
    }

    @Test
    @DisplayName("toCol: 유효하지 않은 col은 예외를 던진다")
    void toColThrowsForInvalidChar() {
        assertThatThrownBy(() -> Col.toCol('j'))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Col.toCol('z'))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Col.toCol('J'))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
