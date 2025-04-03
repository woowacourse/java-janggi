package janggi.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.db.DbTest;
import java.util.Map;
import java.util.Set;
import janggi.model.piece.King;
import janggi.model.piece.Piece;
import org.junit.jupiter.api.Test;

class KingTest extends DbTest {

    Position position = new Position(9, 5);

    @Test
    void 왕이_움직일_수_있는_위치들을_반환한다() {
        Board board = new Board();
        Piece king = new King(Color.BLUE);
        board.putPiece(position, king);

        OccupiedPositions occupiedPositions = new OccupiedPositions(Map.of(
                new Position(9, 6), new PieceIdentity(Color.BLUE, PieceType.CHARIOT),
                new Position(9, 4), new PieceIdentity(Color.RED, PieceType.CHARIOT)

        ));
        Set<Position> points = king.calculateMovablePositions(position, occupiedPositions);

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
    void 궁성_영역_가장자리에서_왕이_움직일_수_있는_위치를_계산한다() {
        Board board = new Board();
        Piece king = new King(Color.BLUE);
        board.putPiece(new Position(8, 6), king);

        OccupiedPositions occupiedPositions = new OccupiedPositions(Map.of());
        Set<Position> points = king.calculateMovablePositions(new Position(8, 6), occupiedPositions);

        assertThat(points).contains(
                new Position(9, 6),
                new Position(8, 5),
                new Position(9, 5)
        );
    }

    @Test
    void 궁성_영역_가운데에서_왕이_움직일_수_있는_위치를_계산한다() {
        Board board = new Board();
        Piece king = new King(Color.BLUE);
        board.putPiece(position, king);

        OccupiedPositions occupiedPositions = new OccupiedPositions(Map.of());
        Set<Position> points = king.calculateMovablePositions(position, occupiedPositions);

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
    void 왕은_궁성영역_밖으로_이동할_수_없다() {

        Board board = new Board();
        JanggiGame janggiGame = new JanggiGame(board, new Turn(Color.BLUE), mockConnection());

        Piece king = new King(Color.BLUE);
        board.putPiece(position, king);

        assertThatThrownBy(() -> janggiGame.playTurn(position, new Position(10, 7)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
