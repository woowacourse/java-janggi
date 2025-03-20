package object.piece;

import java.util.List;
import javax.swing.PopupFactory;
import object.Coordinate;
import object.strategy.SoldierStrategy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import object.strategy.ElephantStrategy;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class PieceTest {

    @Test
    void 피스는_좌표와_움직임_전략을_가진다() {
        // given

        // when
        Coordinate coordinate = new Coordinate(1, 1);

        // then
        Assertions.assertThatNoException().isThrownBy(() -> new Piece(Team.BLUE, new ElephantStrategy(),
                PieceType.CHARIOT, coordinate
        ));
    }

    @Test
    void 피스는_올바른_위치로_이동할_수_있다() {
        // given
        Piece piece = new Piece(
                Team.RED,
                new MoveRule(new SoldierStrategy(), PieceType.SOLIDER),
                new Coordinate(0, 0)
        );
        Coordinate destination = new Coordinate(1, 0);

        // when
        Piece movedPiece = piece.move(destination, new Pieces(List.of()));

        // then
        boolean expected = true;
        boolean actual = movedPiece.isSamePosition(destination);

        Assertions.assertThat(actual).isEqualTo(expected);
    }
}
