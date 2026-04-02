package parser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CommandTest {

    @Test
    void 포메이션_입력이_1_2_3_4_일때_정상_동작한다() {
        Assertions.assertThat(Command.from("1")).isEqualTo(Command.FIRST);
        assertThat(Command.from(" 2 ")).isEqualTo(Command.SECOND);
        assertThat(Command.from("3")).isEqualTo(Command.THIRD);
        assertThat(Command.from("4")).isEqualTo(Command.FOURTH);
    }

    @Test
    void 포메이션_입력이_1_2_3_4_중_하나가_아니면_예외가_발생한다() {
        assertThatThrownBy(() -> Command.from("0"))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> Command.from("5"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
