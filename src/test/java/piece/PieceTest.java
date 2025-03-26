package piece;

import move.SangMoveBehavior;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import piece.player.Team;
import piece.position.JanggiPosition;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PieceTest {

    @Test
    void 피스는_좌표와_움직임_전략을_가진다() {

        JanggiPosition position = new JanggiPosition(1, 1);

        Assertions.assertThatNoException()
                .isThrownBy(() -> new Piece(position, new SangMoveBehavior(), Team.BLUE));
    }
}
