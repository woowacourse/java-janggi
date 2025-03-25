package model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;

class HorseTest {

    Position position = new Position(10, 2);

    @Test
    void 마가_움직일_수_있는_위치들을_반환한다() {
        Board board = new Board();
        Piece horse = new Horse(Color.BLUE);
        board.putPiece(position, horse);

        OccupiedPositions occupiedPositions = new OccupiedPositions(Map.of(
                new Position(8, 1), new PieceIdentity(Color.BLUE, PieceType.CHARIOT),
                new Position(9, 4), new PieceIdentity(Color.RED, PieceType.CHARIOT)

        ));
        Set<Position> points = horse.calculateMovablePositions(position, occupiedPositions);

        assertThat(points).contains(new Position(8, 3));
    }
}
