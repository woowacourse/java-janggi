package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.janggi.domain.Board;
import domain.janggi.domain.Color;
import domain.janggi.domain.Position;
import domain.janggi.piece.Chariot;
import domain.janggi.piece.Elephant;
import domain.janggi.piece.Piece;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ElephantTest {

    private final Position initPosition = new Position(1, 3);

    @Test
    void 상이_움직일_수_있는_위치들을_계산한다() {
        Board board = new Board(List.of());
        Piece piece = new Elephant(initPosition, Color.BLUE, board);
        board.putPieces(List.of(
                new Chariot(new Position(3, 2), Color.BLUE, board),
                new Chariot(new Position(4, 1), Color.BLUE, board),
                new Chariot(new Position(4, 5), Color.BLUE, board),
                new Chariot(new Position(3, 6), Color.RED, board)
        ));

        Set<Position> positions = piece.getMovablePositions();

        assertThat(positions).hasSize(1);
    }
}
