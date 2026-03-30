package domain.board;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Row 열거형 테스트")
class RowTest {

    @Test
    @DisplayName("reverse: ZERO <-> NINE, ONE <-> EIGHT 등 대칭적으로 뒤집는다")
    void reverseReturnsSymmetricRow() {
        assertThat(Row.ZERO.reverse()).isEqualTo(Row.NINE);
        assertThat(Row.NINE.reverse()).isEqualTo(Row.ZERO);
        assertThat(Row.ONE.reverse()).isEqualTo(Row.EIGHT);
        assertThat(Row.FOUR.reverse()).isEqualTo(Row.FIVE);
        assertThat(Row.FIVE.reverse()).isEqualTo(Row.FOUR);
    }

    @Test
    @DisplayName("toRow: 유효한 숫자 문자를 올바른 Row로 변환한다")
    void toRowConvertsValidChar() {
        assertThat(Row.toRow('0')).isEqualTo(Row.ZERO);
        assertThat(Row.toRow('1')).isEqualTo(Row.ONE);
        assertThat(Row.toRow('9')).isEqualTo(Row.NINE);
        assertThat(Row.toRow('5')).isEqualTo(Row.FIVE);
    }

    @Test
    @DisplayName("toRow: 유효하지 않은 문자는 예외를 던진다")
    void toRowThrowsForInvalidChar() {
        assertThatThrownBy(() -> Row.toRow('a'))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
        assertThatThrownBy(() -> Row.toRow(' '))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Row.toRow('!'))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
