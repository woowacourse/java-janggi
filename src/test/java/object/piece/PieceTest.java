package object.piece;

import object.Coordinate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import object.strategy.SangMoveStrategy;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class PieceTest {

    @Test
    void 피스는_좌표와_움직임_전략을_가진다() {
        // given

        // when
        Coordinate coordinate = new Coordinate(1, 1);

        // then
        Assertions.assertThatNoException().isThrownBy(() -> new Piece(coordinate, new SangMoveStrategy(), PieceType.CHARIOT,
                Team.BLUE));
    }
}
