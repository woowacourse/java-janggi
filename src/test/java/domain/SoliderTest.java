package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

class SoliderTest {

    private final Position initPosition = new Position(4, 3);

    @Test
    void 졸의_이동할_수_있는_위치를_계산한다() {
        Board board = new Board(List.of());
        Piece piece = new Solider(initPosition, Team.RED);
        board.putPiece(new Solider(new Position(4,4), Team.BLUE));
        board.putPiece(new Solider(new Position(3,3), Team.RED));

        assertThat(piece.getMovablePositions(board)).hasSize(2);
    }
}
