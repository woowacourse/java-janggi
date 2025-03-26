package piece;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.Test;

import board.Board;
import board.Position;

class SoldierTest {

    private final Position initPosition = new Position(4, 3);

    @Test
    void 한나라_졸의_이동할_수_있는_위치를_계산한다() {
        Piece piece = new Soldier(Team.RED);
        Board board = new Board(Map.of(
                initPosition, piece,
                new Position(4, 4), new Soldier(Team.BLUE),
                new Position(5, 3), new Soldier(Team.RED)
        ));

        assertThat(piece.getMovablePositions(initPosition, board)).containsExactlyInAnyOrder(
                new Position(4, 4),
                new Position(4, 2)
        );
    }

    @Test
    void 초나라_졸의_이동할_수_있는_위치를_계산한다() {
        Piece piece = new Soldier(Team.BLUE);
        Board board = new Board(Map.of(
                initPosition, piece,
                new Position(4, 4), new Soldier(Team.RED),
                new Position(5, 3), new Soldier(Team.RED)

        ));

        assertThat(piece.getMovablePositions(initPosition, board)).containsExactlyInAnyOrder(
                new Position(4, 4),
                new Position(4, 2),
                new Position(3, 3)
        );
    }

}
