package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Board;
import domain.Position;
import domain.Team;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

class ElephantTest {

    private final Position initPosition = new Position(3, 1);

    @Test
    void 마가_움직일_수_있는_위치들을_계산한다() {
        Board board = new Board(List.of());
        Piece piece = new Elephant(initPosition, Team.BLUE, board);
        board.putPiece(new Chariot(new Position(2, 3), Team.BLUE, board));
        board.putPiece(new Chariot(new Position(1, 4), Team.BLUE, board));
        board.putPiece(new Chariot(new Position(5, 4), Team.BLUE, board));
        board.putPiece(new Chariot(new Position(6, 3), Team.RED, board));

        Set<Position> positions = piece.getMovablePositions();

        assertThat(positions).hasSize(1);
    }
}
