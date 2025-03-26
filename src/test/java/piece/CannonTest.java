package piece;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;

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
        Board board = new Board(List.of());
        Piece piece = new Cannon(initPosition, Team.BLUE);
        board.putPiece(new Solider(new Position(1, 4), Team.BLUE));
        board.putPiece(new Solider(new Position(3, 4), Team.BLUE));
        board.putPiece(new Solider(new Position(5, 6), Team.BLUE));
        board.putPiece(new Cannon(new Position(5, 9), Team.RED));
        board.putPiece(new Cannon(new Position(7, 4), Team.RED));
        board.putPiece(new Solider(new Position(5, 3), Team.BLUE));
        board.putPiece(new Solider(new Position(5, 1), Team.RED));

        Set<Position> positions = piece.getMovablePositions(board);

        assertThat(positions).containsExactlyInAnyOrder(
                new Position(2, 4),
                new Position(5, 1),
                new Position(5, 2),
                new Position(5, 7),
                new Position(5, 8)
        );
    }

}
