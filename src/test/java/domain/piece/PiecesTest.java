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

    @Test
    void 좌표_목록에_일치하는_기물_개수를_계산한다() {
        // given
        Pieces pieces = new Pieces(List.of(
                new King(2, 5, new Directions(List.of())),
                new Horse(1, 5, new Directions(List.of())),
                new Pawn(5, 7, new Directions(List.of())),
                new Pawn(8, 1, new Directions(List.of())),
                new Pawn(2, 4, new Directions(List.of()))
        ));

        List<Position> positions = List.of(
                Position.of(1, 5),
                Position.of(2, 5),
                Position.of(5, 7)
        );

        // when
        int count = pieces.countPiecesInPositions(positions);

        // then
        assertThat(count).isEqualTo(3);
    }

    @Test
    void 기물의_좌표를_변경한다() {
        // given
        King piece = new King(2, 5, new Directions(List.of()));
        Position position = Position.of(3, 5);

        Pieces pieces = new Pieces(List.of(
                piece,
                new Horse(1, 5, new Directions(List.of()))
        ));

        // when
        pieces.updatePosition(piece, position);

        // then
        assertThat(pieces.findByPosition(position))
                .isEqualTo(piece);
    }
}
