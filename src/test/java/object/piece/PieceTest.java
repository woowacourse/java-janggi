package object.piece;

import java.util.List;
import object.Coordinate;
import object.strategy.GuardStrategy;
import object.strategy.SoldierStrategy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import object.strategy.ElephantStrategy;

public class PieceTest {

    @Test
    void 피스는_좌표와_움직임_전략을_가진다() {
        // given

        // when
        Coordinate coordinate = new Coordinate(1, 1);

        // then
        Assertions.assertThatNoException().isThrownBy(() -> new Piece(Team.BLUE, new ElephantStrategy(), coordinate));
    }

    @Test
    void 피스는_올바른_위치로_이동할_수_있다() {
        // given
        Piece piece = new Piece(Team.RED, new SoldierStrategy(), new Coordinate(0, 0));
        Coordinate destination = new Coordinate(1, 0);

        // when
        Piece movedPiece = piece.move(destination, new Pieces(List.of()));

        // then
        boolean expected = true;
        boolean actual = movedPiece.isSamePosition(destination);

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("Piece는 자신의 pieceType을 반환할 수 있다.")
    @Test
    void pieceTypeReturnTest() {
        // given
        Piece piece = new Piece(Team.BLUE, new GuardStrategy(), new Coordinate(0, 0));

        // when
        PieceType pieceType = piece.getPieceType();

        // then
        Assertions.assertThat(pieceType).isEqualTo(PieceType.GUARD);
    }
}
