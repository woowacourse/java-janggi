package domain.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class FormationCommandTest {

    @ParameterizedTest
    @CsvSource(value = {"1:FIRST", "2:SECOND", "3:THIRD", "4:FOURTH"}, delimiter = ':')
    void 포메이션_입력_정상_동작(String input, FormationCommand expected) {
        assertThat(FormationCommand.from(input)).isEqualTo(expected);
    }

    @Test
    void 포메이션_입력이_1_2_3_4_중_하나가_아니면_예외가_발생한다() {
        assertThatThrownBy(() -> FormationCommand.from("0"))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> FormationCommand.from("5"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
