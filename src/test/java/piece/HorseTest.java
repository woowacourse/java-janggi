package piece;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import board.Board;
import board.Position;

class HorseTest {

    private final Position initPosition = new Position(4, 4);

    /**
     * .  .  .  .  .  .  .  .  .
     * .  .  X  .  X  .  .  .  .
     * .  O  .  마  .  O  .  .  .
     * .  .  .  마  .  .  .  .  .
     * .  O  .  .  .  마(다른팀)  .  .  .
     * .  .  마  .  O  .  .  .
     * .  .  .  .  .  .  .  .  .
     * .  .  .  .  .  .  .  .  .
     * .  .  .  .  .  .  .  .  .
     * .  .  .  .  .  .  .  .  .
     */
    @Test
    void 마가_움직일_수_있는_위치들을_계산한다() {
        Board board = new Board(List.of());
        Piece piece = new Horse(initPosition, Team.BLUE);
        board.putPieces(List.of(
                new Chariot(new Position(3, 4), Team.BLUE),
                new Chariot(new Position(6, 3), Team.BLUE),
                new Chariot(new Position(5, 6), Team.RED)
        ));

        Set<Position> positions = piece.getMovablePositions(board);

        assertThat(positions).containsExactlyInAnyOrder(
                new Position(3, 2),
                new Position(5, 2),
                new Position(6, 5),
                new Position(5, 6),
                new Position(3, 6)
        );
    }

}
