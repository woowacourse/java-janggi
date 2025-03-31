package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.janggi.domain.Board;
import domain.janggi.domain.Color;
import domain.janggi.domain.Position;
import domain.janggi.piece.Chariot;
import domain.janggi.piece.Horse;
import domain.janggi.piece.Piece;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

class HorseTest {

    private final Position initPosition = new Position(2, 1);

    @Test
    void 마가_움직일_수_있는_위치들을_계산한다() {
        Board board = new Board(List.of());
        Piece piece = new Horse(initPosition, Color.BLUE, board);
        board.putPieces(List.of(
                new Chariot(new Position(2, 2), Color.BLUE, board),
                new Chariot(new Position(1, 3), Color.BLUE, board),
                new Chariot(new Position(4, 2), Color.RED, board)
        ));

        Set<Position> positions = piece.getMovablePositions();

        assertThat(positions).hasSize(1);
    }
}
