package domain.piece;

import domain.direction.Directions;
import domain.direction.PieceDirection;
import domain.piece.category.Horse;
import domain.piece.category.King;
import domain.spatial.Position;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PiecesTest {

    @Test
    void 좌표에_기물이_존재하는지_판단한다() {
        // given
        Position position = new Position(3, 5);
        Pieces pieces = new Pieces(List.of(new King(new Position(2, 5), new Directions(List.of(), false)),
                new Horse(new Position(3, 5), new Directions(List.of(), false))));

        // when
        boolean result = pieces.existByPosition(position);

        // then
        assertThat(result).isTrue();
    }

    @Test
    void 왕이_존재하는지_판단하다() {
        // given
        Pieces pieces = new Pieces(new ArrayList<>());
        Pieces piecesInKing = new Pieces(List.of(new King(new Position(5, 2), PieceDirection.KING.get())));

        // when & then
        assertThat(pieces.existKing()).isFalse();
        assertThat(piecesInKing.existKing()).isTrue();
    }
}
