package janggi.domain.board;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Side;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.Horse;
import janggi.domain.piece.Piece;
import java.util.List;
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
        List<Piece> pieces = Formation.LEFT_ELEPHANT.getPieceOrders(Side.CHO);

        assertThat(pieces.get(0)).isInstanceOf(Elephant.class);
        assertThat(pieces.get(1)).isInstanceOf(Horse.class);
        assertThat(pieces.get(2)).isInstanceOf(Elephant.class);
        assertThat(pieces.get(3)).isInstanceOf(Horse.class);
    }
}
