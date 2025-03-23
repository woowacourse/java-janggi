package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Board;
import domain.Color;
import domain.Position;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

class HorseTest {

    private final Position initPosition = new Position(2, 1);

    @Test
    void 마가_움직일_수_있는_위치들을_계산한다() {
        Board board = new Board(List.of());
        Piece piece = new Horse(initPosition, Color.BLUE, board);
        board.putPiece(new Chariot(new Position(2, 2), Color.BLUE, board));
        board.putPiece(new Chariot(new Position(1, 3), Color.BLUE, board));
        board.putPiece(new Chariot(new Position(4, 2), Color.RED, board));

        Set<Position> positions = piece.getMovablePositions();

        assertThat(positions).hasSize(1);
    }
}
