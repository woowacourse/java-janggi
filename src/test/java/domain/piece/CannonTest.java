package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Board;
import domain.Color;
import domain.Position;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CannonTest {

    private final Position initPosition = new Position(3, 5);

    @Test
    void 포가_움직일_수_있는_위치들을_계산한다() {
        Board board = new Board(List.of());
        Piece piece = new Cannon(initPosition, Color.BLUE, board);
        board.putPiece(new Chariot(new Position(5, 5), Color.BLUE, board)); // 2칸 가능
        board.putPiece(new Cannon(new Position(8, 5), Color.RED, board));
        board.putPiece(new Cannon(new Position(3, 8), Color.BLUE, board)); // 0칸 가능
        board.putPiece(new Chariot(new Position(1, 5), Color.RED, board)); // 0칸 가능
        board.putPiece(new Chariot(new Position(3, 4), Color.RED, board)); // 3칸 가능

        Set<Position> positions = piece.getMovablePositions();

        assertThat(positions).hasSize(5);
    }
}
