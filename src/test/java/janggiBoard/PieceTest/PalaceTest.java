package janggiBoard.PieceTest;

import domain.Position;
import domain.Team;
import domain.piece.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class PalaceTest {

    private TestPieceProvider testBoard;
    private King king;
    private Guard guard;

    @BeforeEach
    void setUp() {
        king = new King(Team.CHO);
        guard = new Guard(Team.CHO);
        testBoard = new TestPieceProvider();
    }

    @Test
    void 궁이_목적지에_갈_수_있다() {
        Position currentPosition = new Position(5, 5);
        Position targetPosition = new Position(4, 5);

        testBoard.setAllBlank();
        boolean isKingMove = king.canMove(currentPosition, targetPosition, testBoard);
        assertThat(isKingMove).isTrue();

    }

    @Test
    void 궁이_목적지에_갈_수_없다() {
        Position currentPosition = new Position(5, 5);
        Position targetPosition = new Position(4, 5);

        testBoard.setAllBlank();
        testBoard.setBlank(new Position(4, 5));
        boolean isKingMove = king.canMove(currentPosition, targetPosition, testBoard);
        assertThat(isKingMove).isFalse();
    }

    @Test
    void 사가_목적지에_갈_수_있다() {
        Position currentPosition = new Position(5, 5);
        Position targetPosition = new Position(4, 5);

        testBoard.setAllBlank();
        boolean isGuardMove = guard.canMove(currentPosition, targetPosition, testBoard);
        assertThat(isGuardMove).isTrue();
    }

    @Test
    void 사가_목적지에_갈_수_없다() {
        Position currentPosition = new Position(5, 5);
        Position targetPosition = new Position(4, 5);

        testBoard.setAllBlank();
        testBoard.setBlank(new Position(4, 5));
        boolean isGuardMove = guard.canMove(currentPosition, targetPosition, testBoard);
        assertThat(isGuardMove).isFalse();
    }


    private static class TestPieceProvider implements PieceProvider {
        private final Map<Position, Boolean> boardState = new HashMap<>();
        private boolean defaultState = true;

        void setBlank(Position pos) {
            boardState.put(pos, false);
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
            return new Blank();
        }
    }
}
