package piece;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;

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
        Board board = new Board(List.of());
        Piece chariot = new Chariot(new Position(4, 4), Team.BLUE);

        board.putPiece(new Chariot(new Position(2, 4), Team.BLUE));
        board.putPiece(new Chariot(new Position(5, 4), Team.BLUE));
        board.putPiece(new Chariot(new Position(4, 7), Team.RED));

        Set<Position> position = chariot.getMovablePositions(board);
        assertThat(position).containsExactlyInAnyOrder(
                new Position(3,4),
                new Position(4,3),
                new Position(4,2),
                new Position(4,1),
                new Position(4,5),
                new Position(4,6),
                new Position(4,7)
        );

    }

}
