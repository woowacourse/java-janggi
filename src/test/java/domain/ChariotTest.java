package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import org.junit.jupiter.api.Test;

class ChariotTest {

    @Test
    void 차가_갈수있는_위치를_계산한다() {
        Board board = new Board(Set.of());
        Chariot chariot = new Chariot(new Position(1, 1), Team.BLUE, board);

        board.putPiece(new Chariot(new Position(2,1), Team.BLUE, board));
        board.putPiece(new Chariot(new Position(1,10), Team.BLUE, board));

        Set<Position> position = chariot.getMovablePositions();
        assertThat(position).hasSize(8);
    }

}
