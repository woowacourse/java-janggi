package model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import java.util.Set;
import model.piece.Cannon;
import model.piece.Piece;
import org.junit.jupiter.api.Test;

class CannonTest {

    Position position = new Position(8, 2);

    @Test
    void 차가_움직일_수_있는_위치들을_반환한다() {
        Piece piece = new Cannon(Color.RED);

        OccupiedPositions occupiedPositions = new OccupiedPositions(Map.of(
                new Position(3, 2), new PieceIdentity(Color.RED, PieceType.CHARIOT),
                new Position(1, 2), new PieceIdentity(Color.BLUE, PieceType.CHARIOT),
                new Position(8, 7), new PieceIdentity(Color.BLUE, PieceType.CHARIOT),
                new Position(8, 9), new PieceIdentity(Color.BLUE, PieceType.CANNON),
                new Position(9, 2), new PieceIdentity(Color.BLUE, PieceType.CANNON)

        ));
        Set<Position> points = piece.calculateMovablePositions(position, occupiedPositions);

        assertThat(points).contains(
            new Position(1, 2),
            new Position(2, 2),
            new Position(2, 2),
            new Position(8, 8)
        );
    }
}
