package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.janggi.domain.Board;
import domain.janggi.domain.Color;
import domain.janggi.domain.Position;
import domain.janggi.piece.King;
import domain.janggi.piece.Piece;
import domain.janggi.piece.Solider;
import java.util.List;
import org.junit.jupiter.api.Test;

class KingTest {

    private final Position initPosition = new Position(5, 2);

    @Test
    void 궁의_이동할_수_있는_위치를_계산한다() {
        Board board = new Board(List.of());
        Piece piece = new King(initPosition, Color.RED, board);
        board.putPieces(List.of(
                new Solider(new Position(5,3), Color.BLUE, board),
                new Solider(new Position(4,2), Color.RED, board)
        ));

        assertThat(piece.getMovablePositions()).hasSize(3);
    }
}
