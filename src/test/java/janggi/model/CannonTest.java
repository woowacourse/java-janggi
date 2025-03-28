package janggi.model;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.model.Color;
import janggi.model.OccupiedPositions;
import janggi.model.PieceIdentity;
import janggi.model.PieceType;
import janggi.model.Position;
import janggi.model.piece.Chariot;
import java.util.Map;
import java.util.Set;
import janggi.model.piece.Cannon;
import janggi.model.piece.Piece;
import org.junit.jupiter.api.Test;

class CannonTest {

    Position position = new Position(8, 2);

    @Test
    void 포가_움직일_수_있는_위치들을_반환한다() {
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
            new Position(8, 8)
        );
    }

    @Test
    void 궁성_가장자리_영역에서_포의_움직일_수_있는_위치들을_반환한다() {
        Piece cannon = new Cannon(Color.RED);
        Position point = new Position(10, 4);

        OccupiedPositions occupiedPositions = new OccupiedPositions(Map.of(
                new Position(10, 2), new PieceIdentity(Color.RED, PieceType.CANNON),
                new Position(9, 5), new PieceIdentity(Color.RED, PieceType.CHARIOT),
                new Position(2, 4), new PieceIdentity(Color.RED, PieceType.CHARIOT),
                new Position(10, 7), new PieceIdentity(Color.RED, PieceType.CHARIOT)

        ));
        Set<Position> points = cannon.calculateMovablePositions(point, occupiedPositions);

        assertThat(points).containsExactlyInAnyOrder(
                new Position(8, 6),
                new Position(1, 4),
                new Position(10, 8),
                new Position(10, 9)
        );
    }
}
