package domain.board;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Col 열거형 테스트")
class ColumnTest {

    @Test
    @DisplayName("toCol: 소문자 a-i를 올바른 Col로 변환한다")
    void toColumnConvertsLowercaseChar() {
        assertThat(Column.toColumn('a')).isEqualTo(Column.A);
        assertThat(Column.toColumn('e')).isEqualTo(Column.E);
        assertThat(Column.toColumn('i')).isEqualTo(Column.I);
    }

    @Test
    @DisplayName("toCol: 대문자 A-I를 올바른 Col로 변환한다")
    void toColumnConvertsUppercaseChar() {
        assertThat(Column.toColumn('A')).isEqualTo(Column.A);
        assertThat(Column.toColumn('E')).isEqualTo(Column.E);
        assertThat(Column.toColumn('I')).isEqualTo(Column.I);
    }

    @Test
    @DisplayName("toCol: 유효하지 않은 col은 예외를 던진다")
    void toColumnThrowsForInvalidChar() {
        assertThatThrownBy(() -> Column.toColumn('j'))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Column.toColumn('z'))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Column.toColumn('J'))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
