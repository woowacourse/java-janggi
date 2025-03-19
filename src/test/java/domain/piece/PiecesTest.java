package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.direction.Directions;
import java.util.List;
import org.junit.jupiter.api.Test;

class PiecesTest {

    @Test
    void 특정_위치에_있는_기물을_반환한다() {
        // given
        Position position = Position.of(1, 5);
        Horse expected = new Horse(1, 5, new Directions(List.of()));

        Pieces pieces = new Pieces(List.of(
                new King(2, 5, new Directions(List.of())),
                expected,
                new Pawn(5, 7, new Directions(List.of()))
        ));

        // when
        Piece result = pieces.findByPosition(position);

        // then
        assertThat(result).isEqualTo(expected);
    }
}
