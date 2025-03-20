import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import piece.Piece;
import piece.PieceType;
import piece.Position;
import piece.Team;
import strategy.SangMoveStrategy;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class PieceTest {

    @Test
    void 피스는_좌표와_움직임_전략을_가진다() {
        // given

        // when
        Position position = new Position(1, 1);

        // then
        Assertions.assertThatNoException().isThrownBy(() -> new Piece(position, new SangMoveStrategy(), PieceType.CHA,
                Team.BLUE));
    }
}
