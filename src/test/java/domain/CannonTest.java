package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CannonTest {

    private final Position initPosition = new Position(5, 3);

    @Test
    void 포가_움직일_수_있는_위치들을_계산한다() {
        Board board = new Board(List.of());
        Piece piece = new Cannon(initPosition, Team.BLUE, board);
        board.putPiece(new Chariot(new Position(5, 5), Team.BLUE, board));
        board.putPiece(new Cannon(new Position(5, 8), Team.RED, board));
        board.putPiece(new Chariot(new Position(7, 2), Team.RED, board));
        board.putPiece(new Cannon(new Position(8, 3), Team.BLUE, board));
        board.putPiece(new Chariot(new Position(5, 2), Team.RED, board));
        board.putPiece(new Chariot(new Position(5, 1), Team.RED, board));
        board.putPiece(new Chariot(new Position(4, 3), Team.RED, board));

        Set<Position> positions = piece.getMovablePositions();

        assertThat(positions).hasSize(6);
    }
}
