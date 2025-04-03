package janggi.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.db.DbTest;
import janggi.db.MockConnection;
import janggi.model.piece.Chariot;
import java.util.Map;
import java.util.Set;
import janggi.model.piece.Guard;
import janggi.model.piece.Piece;
import org.junit.jupiter.api.Test;

class GuardTest extends DbTest {

    Position position = new Position(9, 5);

    @Test
    void 사가_움직일_수_있는_위치들을_반환한다() {
        Piece guard = new Guard(Color.BLUE);

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

    @Test
    void 궁성_영역_가장자리에서_사가_움직일_수_있는_위치를_계산한다() {
        Piece guard = new Guard(Color.BLUE);

        OccupiedPositions occupiedPositions = new OccupiedPositions(Map.of());
        Set<Position> points = guard.calculateMovablePositions(new Position(8, 6), occupiedPositions);

        assertThat(points).contains(
                new Position(9, 6),
                new Position(8, 5),
                new Position(9, 5)
        );
    }

    @Test
    void 궁성_영역_가운데에서_사가_움직일_수_있는_위치를_계산한다() {
        Piece guard = new Guard(Color.BLUE);

        OccupiedPositions occupiedPositions = new OccupiedPositions(Map.of());
        Set<Position> points = guard.calculateMovablePositions(position, occupiedPositions);

        assertThat(points).contains(
                new Position(9, 6),
                new Position(9, 4),
                new Position(8, 4),
                new Position(8, 5),
                new Position(8, 6),
                new Position(10, 4),
                new Position(10, 5),
                new Position(10, 6)
        );
    }

    @Test
    void 사는_궁성영역_밖으로_이동할_수_없다() {
        Board board = new Board();
        JanggiGame janggiGame = new JanggiGame(board, new Turn(Color.BLUE), mockConnection());

        Piece guard = new Chariot(Color.BLUE);
        board.putPiece(position, guard);

        janggiGame.playTurn(position, new Position(10, 6));

        assertThatThrownBy(() ->janggiGame.playTurn(position, new Position(10, 6)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
