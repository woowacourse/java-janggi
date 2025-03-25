package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.janggi.domain.Board;
import domain.janggi.domain.Color;
import domain.janggi.domain.Position;
import domain.janggi.piece.Chariot;
import domain.janggi.piece.Piece;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ChariotTest {

    @Test
    void 차가_갈수있는_위치를_계산한다() {
        Board board = new Board(List.of());
        Piece chariot = new Chariot(new Position(1, 1), Color.BLUE, board);

        board.putPieces(List.of(
                new Chariot(new Position(1, 2), Color.BLUE, board),
                new Chariot(new Position(10, 1), Color.BLUE, board)
        ));

        Set<Position> position = chariot.getMovablePositions();
        assertThat(position).hasSize(8);
    }
}
