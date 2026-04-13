package view.parser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.board.Formation;
import org.junit.jupiter.api.Test;

class FormationCommandTest {

    @Test
    void 포메이션_입력이_1_2_3_4_일때_정상_동작한다() {
        assertThat(FormationCommand.from("1").toFormation()).isEqualTo(Formation.LEFT_ELEPHANT);
        assertThat(FormationCommand.from("2").toFormation()).isEqualTo(Formation.RIGHT_ELEPHANT);
        assertThat(FormationCommand.from("3").toFormation()).isEqualTo(Formation.OUTER_ELEPHANT);
        assertThat(FormationCommand.from("4").toFormation()).isEqualTo(Formation.INNER_ELEPHANT);
    }

    @Test
    void 포메이션_입력에_공백이_포함되어도_정상_동작한다() {
        assertThat(FormationCommand.from("1  ").toFormation()).isEqualTo(Formation.LEFT_ELEPHANT);
        assertThat(FormationCommand.from("  2").toFormation()).isEqualTo(Formation.RIGHT_ELEPHANT);
        assertThat(FormationCommand.from("  3  ").toFormation()).isEqualTo(Formation.OUTER_ELEPHANT);
    }

    @Test
    void 포메이션_입력이_1_2_3_4_중_하나가_아니면_예외가_발생한다() {
        assertThatThrownBy(() -> FormationCommand.from("0"))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> FormationCommand.from("5"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
