package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.janggi.domain.Board;
import domain.janggi.domain.Color;
import domain.janggi.domain.Position;
import domain.janggi.piece.Cannon;
import domain.janggi.piece.Chariot;
import domain.janggi.piece.Piece;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CannonTest {

    private final Position initPosition = new Position(3, 5);

    @Test
    void 포가_움직일_수_있는_위치들을_계산한다() {
        Board board = new Board(List.of());
        Piece piece = new Cannon(initPosition, Color.BLUE, board);
        board.putPieces(List.of(
                new Chariot(new Position(5, 5), Color.BLUE, board),
                new Cannon(new Position(8, 5), Color.RED, board),
                new Cannon(new Position(3, 8), Color.BLUE, board),
                new Chariot(new Position(1, 5), Color.RED, board),
                new Chariot(new Position(3, 4), Color.RED, board)
        ));

        Set<Position> positions = piece.getMovablePositions();

        assertThat(positions).hasSize(5);
    }
}
