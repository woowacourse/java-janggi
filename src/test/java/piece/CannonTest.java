package piece;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.Test;

import board.Board;
import board.Position;

class CannonTest {

    private final Position initPosition = new Position(5, 4);

    /**
     * . . . 졸(같은팀) . . . . .
     * . . . . . . . . .
     * . . . 졸 . . . . .
     * . . . . . . . . .
     * 졸. 졸 포 . 졸 . .포
     * . . . . . . . . .
     * . . . 포 . . . . .
     * . . . . . . . . .
     * . . . . . . . . .
     * . . . . . . . . .
     */
    @Test
    void 포가_움직일_수_있는_위치들을_계산한다() {
        Piece piece = new Cannon(Team.BLUE);
        Board board = new Board(Map.of(
                initPosition, piece,
                new Position(1, 4), new Soldier(Team.BLUE),
                new Position(3, 4), new Soldier(Team.BLUE),
                new Position(5, 6), new Soldier(Team.BLUE),
                new Position(5, 9), new Cannon(Team.RED),
                new Position(7, 4), new Cannon(Team.RED),
                new Position(5, 3), new Soldier(Team.BLUE),
                new Position(5, 1), new Soldier(Team.RED)

        ));

        assertThat(piece.getMovablePositions(initPosition, board)).containsExactlyInAnyOrder(
                new Position(2, 4),
                new Position(5, 1),
                new Position(5, 2),
                new Position(5, 7),
                new Position(5, 8)
        );
    }

}
