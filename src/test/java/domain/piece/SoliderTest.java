package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.janggi.domain.Board;
import domain.janggi.domain.Color;
import domain.janggi.domain.Position;
import domain.janggi.piece.Piece;
import domain.janggi.piece.Solider;
import java.util.List;
import org.junit.jupiter.api.Test;

class SoliderTest {

    private final Position initPosition = new Position(4, 3);

    @Test
    void 졸의_이동할_수_있는_위치를_계산한다() {
        Board board = new Board(List.of());
        Piece piece = new Solider(initPosition, Color.RED, board);
        board.putPieces(List.of(
                new Solider(new Position(4,4), Color.BLUE, board),
                new Solider(new Position(3,3), Color.RED, board)
        ));
        assertThat(piece.getMovablePositions()).hasSize(2);
    }
}
