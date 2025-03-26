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
import janggi.model.piece.Piece;
import janggi.model.piece.Soldier;
import org.junit.jupiter.api.Test;

class SoldierTest {

    Position position = new Position(9, 5);

    @Test
    void 쫄이_움직일_수_있는_위치들을_반환한다() {
        Board board = new Board();
        Piece soldier = new Soldier(Color.BLUE);
        board.putPiece(position, soldier);

        OccupiedPositions occupiedPositions = new OccupiedPositions(Map.of(
                new Position(9, 6), new PieceIdentity(Color.BLUE, PieceType.CHARIOT),
                new Position(9, 4), new PieceIdentity(Color.RED, PieceType.CHARIOT)

        ));
        Set<Position> points = soldier.calculateMovablePositions(position, occupiedPositions);

        assertThat(points).contains(
                new Position(9, 4),
                new Position(8, 5)
        );
    }
}
