package piece;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import board.Board;
import board.Position;

class KingTest {

    private final Position initPosition = new Position(5, 2);

    @Test
    void 궁의_이동할_수_있는_위치를_계산한다() {
        Board board = new Board(List.of());
        Piece piece = new King(initPosition, Team.RED);
        board.putPiece(new Solider(new Position(5, 3), Team.BLUE));
        board.putPiece(new Solider(new Position(4, 2), Team.RED));

        assertThat(piece.getMovablePositions(board)).containsExactlyInAnyOrder(
                new Position(5,3),
                new Position(5,1),
                new Position(6,2)
        );
    }

}
