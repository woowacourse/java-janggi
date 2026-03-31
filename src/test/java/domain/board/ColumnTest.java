package domain.board;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Col 열거형 테스트")
class ColumnTest {

    @Test
    @DisplayName("toCol: 소문자 a-i를 올바른 Col로 변환한다")
    void toColConvertsLowercaseChar() {
        assertThat(Column.toCol('a')).isEqualTo(Column.A);
        assertThat(Column.toCol('e')).isEqualTo(Column.E);
        assertThat(Column.toCol('i')).isEqualTo(Column.I);
    }

    @Test
    @DisplayName("toCol: 대문자 A-I를 올바른 Col로 변환한다")
    void toColConvertsUppercaseChar() {
        assertThat(Column.toCol('A')).isEqualTo(Column.A);
        assertThat(Column.toCol('E')).isEqualTo(Column.E);
        assertThat(Column.toCol('I')).isEqualTo(Column.I);
    }

    @Test
    @DisplayName("toCol: 유효하지 않은 col은 예외를 던진다")
    void toColThrowsForInvalidChar() {
        assertThatThrownBy(() -> Column.toCol('j'))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Column.toCol('z'))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Column.toCol('J'))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
