package piece;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.Test;

import board.Board;
import board.Position;

class ElephantTest {

    private final Position initPosition = new Position(4, 4);

    /**
     * .  차(다른팀)  .  O  .  .  .  .  .
     * O  .  .  .  .  .  O  .  .
     * .  .  .  .  .  .  .  .  .
     * .  .  .  상  .  .  .  .  .
     * .  .  .  .  .  .  .  .  .
     * O  .  차  .  .  .  O  .
     * .  X  .  .  .  O  .  .  .
     * .  .  .  .  .  .  .  .  .
     * .  .  .  .  .  .  .  .  .
     * .  .  .  .  .  .  .  .  .
     */
    @Test
    void 상이_움직일_수_있는_위치들을_계산한다() {
        Piece piece = new Elephant(Team.BLUE);
        Board board = new Board(Map.of(
                initPosition, piece,
                new Position(1, 2), new Chariot(Team.RED),
                new Position(6, 3), new Chariot(Team.RED)
        ));

        assertThat(piece.getMovablePositions(initPosition, board)).containsExactlyInAnyOrder(
                new Position(1, 2),
                new Position(1, 6),
                new Position(7, 6),
                new Position(2, 1),
                new Position(6, 1),
                new Position(2, 7),
                new Position(6, 7)
        );
    }

}
