package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

class ElephantTest {

    private final Position initPosition = new Position(3, 1);

    @Test
    void 마가_움직일_수_있는_위치들을_계산한다() {
        Board board = new Board(List.of());
        Piece piece = new Elephant(initPosition, Team.BLUE);
        board.putPiece(new Chariot(new Position(2, 3), Team.BLUE));
        board.putPiece(new Chariot(new Position(1, 4), Team.BLUE));
        board.putPiece(new Chariot(new Position(5, 4), Team.BLUE));
        board.putPiece(new Chariot(new Position(6, 3), Team.RED));

        Set<Position> positions = piece.getMovablePositions(board);

        assertThat(positions).hasSize(1);
    }
}
