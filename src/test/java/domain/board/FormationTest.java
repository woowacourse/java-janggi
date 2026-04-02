package domain.board;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Position;
import domain.Side;
import domain.piece.Elephant;
import domain.piece.Horse;
import domain.piece.Piece;
import java.util.HashMap;
import java.util.Map;
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

    @Test
    void LEFT_ELEPHANT은_상마상마로_배치한다() {
        Map<Position, Piece> pieces = new HashMap<>();

        Formation.LEFT_ELEPHANT.placeElephant(pieces, Side.CHO);

        assertThat(pieces.get(Position.of(1, 0))).isInstanceOf(Elephant.class);
        assertThat(pieces.get(Position.of(2, 0))).isInstanceOf(Horse.class);
        assertThat(pieces.get(Position.of(6, 0))).isInstanceOf(Elephant.class);
        assertThat(pieces.get(Position.of(7, 0))).isInstanceOf(Horse.class);
    }
}
