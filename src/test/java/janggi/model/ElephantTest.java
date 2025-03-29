package janggi.model;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.model.Board;
import janggi.model.Color;
import janggi.model.OccupiedPositions;
import janggi.model.PieceIdentity;
import janggi.model.PieceType;
import janggi.model.Position;
import java.util.Map;
import java.util.Set;
import janggi.model.piece.Elephant;
import janggi.model.piece.Piece;
import org.junit.jupiter.api.Test;

class ElephantTest {

    Position position = new Position(10, 3);

    @Test
    void 상이_움직일_수_있는_위치들을_반환한다() {
        Board board = new Board();
        Piece elephant = new Elephant(Color.BLUE);
        board.putPiece(position, elephant);

        OccupiedPositions occupiedPositions = new OccupiedPositions(Map.of(
                new Position(7, 5), new PieceIdentity(Color.BLUE, PieceType.CHARIOT),
                new Position(8, 6), new PieceIdentity(Color.RED, PieceType.CHARIOT)

        ));
        Set<Position> points = elephant.calculateMovablePositions(position, occupiedPositions);

        assertThat(points).contains(
                new Position(8, 6),
                new Position(7, 1)
        );
    }
}
