package janggiBoard.PieceTest;

import domain.PieceProvider;
import domain.position.Position;
import domain.Team;
import domain.piece.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CannonTest {

    private Cannon cannon;
    private TestPieceProvider testBoard;

    @BeforeEach
    public void setUp() {
        cannon = new Cannon(Team.CHO);
        testBoard = new TestPieceProvider();
    }

    @Test
    void 포는_다리를_넘어_이동할_수_있다() {
        Position currentPosition = new Position(5, 5);
        Position targetPosition = new Position(8, 5);

        testBoard.setAllBlank();
        testBoard.setPiece(new Position(6, 5), new Guard(Team.CHO));

        boolean isCannonMove = cannon.canMove(currentPosition, targetPosition, testBoard);
        assertThat(isCannonMove).isTrue();
    }

    @Test
    void 포는_포를_다리로_삼을_수_없다() {
        Position currentPosition = new Position(5, 5);
        Position targetPosition = new Position(8, 5);

        testBoard.setAllBlank();
        testBoard.setPiece(new Position(6, 5), new Cannon(Team.CHO));

        boolean isCannonMove = cannon.canMove(currentPosition, targetPosition, testBoard);
        assertThat(isCannonMove).isFalse();
    }

    @Test
    void 포는_포를_잡을_수_없다() {
        Position currentPosition = new Position(5, 5);
        Position targetPosition = new Position(8, 5);

        testBoard.setAllBlank();
        testBoard.setPiece(new Position(6, 5), new Guard(Team.CHO));
        testBoard.setPiece(new Position(8, 5), new Cannon(Team.HAN));

        boolean isCannonMove = cannon.canMove(currentPosition, targetPosition, testBoard);
        assertThat(isCannonMove).isFalse();
    }

    private static class TestPieceProvider implements PieceProvider {
        private final Map<Position, Piece> pieces = new HashMap<>();

        void setPiece(Position pos, Piece piece) {
            pieces.put(pos, piece);
        }

        void setAllBlank() {
            pieces.clear();
        }

        @Override
        public boolean isBlank(Position position) {
            return !pieces.containsKey(position);
        }

        @Override
        public Piece getPiece(Position position) {
            return pieces.getOrDefault(position, new Blank());
        }
    }
}
