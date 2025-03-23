package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Board;
import domain.Color;
import domain.Position;
import java.util.List;
import org.junit.jupiter.api.Test;

class KingTest {

    private final Position initPosition = new Position(5, 2);

    @Test
    void 궁의_이동할_수_있는_위치를_계산한다() {
        Board board = new Board(List.of());
        Piece piece = new King(initPosition, Color.RED, board);
        board.putPiece(new Solider(new Position(5,3), Color.BLUE, board));
        board.putPiece(new Solider(new Position(4,2), Color.RED, board));

        assertThat(piece.getMovablePositions()).hasSize(3);
    }
}
