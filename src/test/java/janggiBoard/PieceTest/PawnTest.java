package janggiBoard.PieceTest;

import domain.Position;
import domain.Team;
import domain.piece.Pawn;
import domain.piece.Piece;
import domain.piece.PieceProvider;
import domain.strategy.PawnStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class PawnTest {

    private Pawn pawn;
    private TestPieceProvider testBoard;

    @BeforeEach
    public void setUp() {
        pawn = new Pawn(Team.CHO, new PawnStrategy());
        testBoard = new TestPieceProvider();
    }

    @Test
    void 졸은_목적지에_갈_수_있다() {
        Position currentPosition = new Position(6, 4);
        Position targetPosition = new Position(5, 4);

        testBoard.setAllBlank();
        boolean isPawnMove = pawn.canMove(currentPosition, targetPosition, testBoard);
        assertThat(isPawnMove).isTrue();
    }

    @Test
    void 졸은_목적지에_갈_수_없다() {
        Position currentPosition = new Position(6, 4);
        Position targetPosition = new Position(5, 4);

        testBoard.setAllBlank();
        testBoard.setBlank(new Position(5, 4));

        boolean isPawnMove = pawn.canMove(currentPosition, targetPosition, testBoard);

        assertThat(isPawnMove).isFalse();
    }

    private static class TestPieceProvider implements PieceProvider {
        private final Map<Position, Boolean> boardState = new HashMap<>();
        private boolean defaultState = true;

        void setBlank(Position position) {
            boardState.put(position, false);
        }

        void setAllBlank() {
            this.defaultState = true;
        }

        @Override
        public boolean isBlank(Position position) {
            return boardState.getOrDefault(position, defaultState);
        }

        @Override
        public Piece getPiece(Position position) {
            return new Pawn(Team.CHO, new PawnStrategy());
        }
    }
}
