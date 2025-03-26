package janggi.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.model.Board;
import janggi.model.Color;
import janggi.model.OccupiedPositions;
import janggi.model.PieceIdentity;
import janggi.model.PieceType;
import janggi.model.Position;
import janggi.model.piece.Guard;
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

    @Test
    void 궁성_영역_가장자리에서_쫄이_움직일_수_있는_위치를_계산한다() {
        Board board = new Board();
        Piece soldier = new Soldier(Color.RED);
        board.putPiece(new Position(8, 6), soldier);

        OccupiedPositions occupiedPositions = new OccupiedPositions(Map.of());
        Set<Position> points = soldier.calculateMovablePositions(new Position(8, 6), occupiedPositions);

        assertThat(points).contains(
                new Position(9, 6),
                new Position(8, 5),
                new Position(9, 5)
        );
    }

    @Test
    void 궁성_영역_가운데에서_쫄이_움직일_수_있는_위치를_계산한다() {
        Board board = new Board();
        Piece soldier = new Soldier(Color.RED);
        board.putPiece(position, soldier);

        OccupiedPositions occupiedPositions = new OccupiedPositions(Map.of());
        Set<Position> points = soldier.calculateMovablePositions(position, occupiedPositions);

        assertThat(points).contains(
                new Position(9, 6),
                new Position(9, 4),
                new Position(10, 4),
                new Position(10, 5),
                new Position(10, 6)
        );
    }
}
