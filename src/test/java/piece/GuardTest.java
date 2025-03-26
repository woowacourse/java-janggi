package piece;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.Test;

import board.Board;
import board.Position;

class GuardTest {

    private final Position initPosition = new Position(4, 3);

    @Test
    void 사의_이동할_수_있는_위치를_계산한다() {
        Piece piece = new Guard(Team.RED);
        Board board = new Board(Map.of(
                initPosition, piece,
                new Position(3, 3), new Soldier(Team.RED),
                new Position(4, 4), new Soldier(Team.BLUE)
        ));

        assertThat(piece.getMovablePositions(initPosition, board)).containsExactlyInAnyOrder(
                new Position(4, 4),
                new Position(5, 3),
                new Position(4, 2)
        );
    }

}
