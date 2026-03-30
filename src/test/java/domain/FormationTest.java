package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class FormationTest {

    @Test
    void 선택값으로_포메이션을_찾는다() {
        assertThat(Formation.from(FormationCommand.FIRST)).isEqualTo(Formation.LEFT_ELEPHANT);
        assertThat(Formation.from(FormationCommand.SECOND)).isEqualTo(Formation.RIGHT_ELEPHANT);
        assertThat(Formation.from(FormationCommand.THIRD)).isEqualTo(Formation.OUTER_ELEPHANT);
        assertThat(Formation.from(FormationCommand.FOURTH)).isEqualTo(Formation.INNER_ELEPHANT);
    }

    @Test
    void LEFT_ELEPHANT은_상마상마로_배치한다() {
        Map<Position, Piece> pieces = new HashMap<>();

        Formation.LEFT_ELEPHANT.placeElephant(pieces, Side.CHO);

        assertThat(pieces.get(new Position(1, 0))).isInstanceOf(Elephant.class);
        assertThat(pieces.get(new Position(2, 0))).isInstanceOf(Horse.class);
        assertThat(pieces.get(new Position(6, 0))).isInstanceOf(Elephant.class);
        assertThat(pieces.get(new Position(7, 0))).isInstanceOf(Horse.class);
    }
}
