package piece;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.Test;

import board.Board;
import board.Position;

class KingTest {

    private final Position initPosition = new Position(5, 2);

    @Test
    void 궁의_이동할_수_있는_위치를_계산한다() {
        Piece piece = new King(Team.RED);
        Board board = new Board(Map.of(
                initPosition, piece,
                new Position(5, 3), new Soldier(Team.BLUE),
                new Position(4, 2), new Soldier(Team.RED)
        ));

        assertThat(piece.getMovablePositions(initPosition, board)).containsExactlyInAnyOrder(
                new Position(5, 3),
                new Position(5, 1),
                new Position(6, 2)
        );
    }

}
