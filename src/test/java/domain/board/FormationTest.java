package domain.board;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import parser.Command;

class FormationTest {

    @Test
    void 선택값으로_포메이션을_찾는다() {
        assertThat(Formation.from(Command.FIRST)).isEqualTo(Formation.LEFT_ELEPHANT);
        assertThat(Formation.from(Command.SECOND)).isEqualTo(Formation.RIGHT_ELEPHANT);
        assertThat(Formation.from(Command.THIRD)).isEqualTo(Formation.OUTER_ELEPHANT);
        assertThat(Formation.from(Command.FOURTH)).isEqualTo(Formation.INNER_ELEPHANT);
    }
}
