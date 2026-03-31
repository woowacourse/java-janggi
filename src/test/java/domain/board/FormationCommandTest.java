package domain.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class FormationCommandTest {

    @Test
    void 포메이션_입력이_1_2_3_4_일때_정상_동작한다() {
        assertThat(FormationCommand.from("1")).isEqualTo(FormationCommand.FIRST);
        assertThat(FormationCommand.from(" 2 ")).isEqualTo(FormationCommand.SECOND);
        assertThat(FormationCommand.from("3")).isEqualTo(FormationCommand.THIRD);
        assertThat(FormationCommand.from("4")).isEqualTo(FormationCommand.FOURTH);
    }

    @Test
    void 포메이션_입력이_1_2_3_4_중_하나가_아니면_예외가_발생한다() {
        assertThatThrownBy(() -> FormationCommand.from("0"))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> FormationCommand.from("5"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
