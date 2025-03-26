package janggi.model;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.model.Color;
import janggi.model.OccupiedPositions;
import janggi.model.PieceIdentity;
import janggi.model.PieceType;
import janggi.model.Position;
import java.util.Map;
import java.util.Set;
import janggi.model.piece.Chariot;
import org.junit.jupiter.api.Test;

class ChariotTest {

    @Test
    void 차가_움직일_수_있는_위치들을_반환한다() {
        Chariot chariot = new Chariot(Color.RED);
        Position point = new Position(4, 4);

        OccupiedPositions occupiedPositions = new OccupiedPositions(Map.of(
                new Position(5, 4), new PieceIdentity(Color.RED, PieceType.CHARIOT)

        ));
        Set<Position> points = chariot.calculateMovablePositions(point, occupiedPositions);

        assertThat(points).hasSize(11);
    }

    @Test
    void 궁성_가장자리_영역에서_차가_움직일_수_있는_위치들을_반환한다() {
        Chariot chariot = new Chariot(Color.RED);
        Position point = new Position(8, 6);

        OccupiedPositions occupiedPositions = new OccupiedPositions(Map.of(
                new Position(8, 3), new PieceIdentity(Color.RED, PieceType.CHARIOT),
                new Position(8, 7), new PieceIdentity(Color.RED, PieceType.CHARIOT)

        ));
        Set<Position> points = chariot.calculateMovablePositions(point, occupiedPositions);

        assertThat(points).containsExactlyInAnyOrder(
                new Position(8, 4),
                new Position(8, 5),
                new Position(9, 6),
                new Position(10, 6),
                new Position(9, 5),
                new Position(10, 4),
                new Position(7, 6),
                new Position(6, 6),
                new Position(5, 6),
                new Position(4, 6),
                new Position(3, 6),
                new Position(2, 6),
                new Position(1, 6)
        );
    }
}
