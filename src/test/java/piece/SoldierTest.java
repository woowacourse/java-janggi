package piece;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import board.Board;
import board.Position;

class SoldierTest {

    private final Position initPosition = new Position(4, 3);

    @Test
    void 한나라_졸의_이동할_수_있는_위치를_계산한다() {
        Board board = new Board(List.of());
        Piece piece = new Soldier(initPosition, Team.RED);
        board.putPieces(List.of(
                new Soldier(new Position(4, 4), Team.BLUE),
                new Soldier(new Position(5, 3), Team.RED)
        ));

        assertThat(piece.getMovablePositions(board)).containsExactlyInAnyOrder(
                new Position(4, 4),
                new Position(4, 2)
        );
    }

    @Test
    void 초나라_졸의_이동할_수_있는_위치를_계산한다() {
        Board board = new Board(List.of());
        Piece piece = new Soldier(initPosition, Team.BLUE);
        board.putPieces(List.of(
                new Soldier(new Position(4, 4), Team.RED),
                new Soldier(new Position(5, 3), Team.RED)
        ));

        assertThat(piece.getMovablePositions(board)).containsExactlyInAnyOrder(
                new Position(4, 4),
                new Position(4, 2),
                new Position(3, 3)
        );
    }

}
