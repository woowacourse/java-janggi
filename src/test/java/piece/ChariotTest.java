package piece;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.Test;

import board.Board;
import board.Position;

class ChariotTest {

    /**
     * . . . . . . . . .
     * . . . 차(같은팀) . . . . .
     * . . . . . . . . .
     * . . . 차 . . 차(다른팀) . .
     * . . . 차(같은팀) . . . . .
     * . . . . . . . . .
     * . . . . . . . . .
     * . . . . . . . . .
     * . . . . . . . . .
     * . . . . . . . . .
     */
    @Test
    void 차가_갈수있는_위치를_계산한다() {
        Piece chariot = new Chariot(Team.BLUE);
        Position initPosition = new Position(4, 4);
        Board board = new Board(Map.of(
                initPosition, chariot,
                new Position(2, 4), new Chariot(Team.BLUE),
                new Position(5, 4), new Chariot(Team.BLUE),
                new Position(4, 7), new Chariot(Team.RED)
        ));

        assertThat(chariot.getMovablePositions(initPosition, board)).containsExactlyInAnyOrder(
                new Position(3, 4),
                new Position(4, 3),
                new Position(4, 2),
                new Position(4, 1),
                new Position(4, 5),
                new Position(4, 6),
                new Position(4, 7)
        );
    }

}
