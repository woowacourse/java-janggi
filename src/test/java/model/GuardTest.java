package model;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.model.Board;
import janggi.model.Color;
import janggi.model.OccupiedPositions;
import janggi.model.PieceIdentity;
import janggi.model.PieceType;
import janggi.model.Position;
import java.util.Map;
import java.util.Set;
import janggi.model.piece.Guard;
import janggi.model.piece.Piece;
import org.junit.jupiter.api.Test;

class GuardTest {

    Position position = new Position(9, 5);

    @Test
    void 사가_움직일_수_있는_위치들을_반환한다() {
        Board board = new Board();
        Piece guard = new Guard(Color.BLUE);
        board.putPiece(position, guard);

        OccupiedPositions occupiedPositions = new OccupiedPositions(Map.of(
                new Position(9, 6), new PieceIdentity(Color.BLUE, PieceType.CHARIOT),
                new Position(9, 4), new PieceIdentity(Color.RED, PieceType.CHARIOT)

        ));
        Set<Position> points = guard.calculateMovablePositions(position, occupiedPositions);

        assertThat(points).contains(
                new Position(9, 4),
                new Position(8, 4),
                new Position(8, 5),
                new Position(8, 6),
                new Position(10, 4),
                new Position(10, 5),
                new Position(10, 6)
        );
    }
}
