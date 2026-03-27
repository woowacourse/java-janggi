package janggiBoard.PieceTest;

import domain.Position;
import domain.Team;
import domain.piece.Blank;
import domain.piece.Horse;
import domain.piece.Piece;
import domain.piece.PieceProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class HorseTest {
    private TestPieceProvider testBoard;
    private Horse horse;

    @BeforeEach
    void setUp() {
        horse = new Horse(Team.CHO);
        testBoard = new TestPieceProvider();
    }

    @Test
    void 마가_목적지에_갈_수_있다() {
        Position currentPosition = new Position(5, 5);
        Position targetPosition = new Position(3, 4);

        testBoard.setAllBlank();
        boolean isCanMove = horse.canMove(currentPosition, targetPosition, testBoard);
        assertThat(isCanMove).isTrue();
    }

    @Test
    void 마가_목적지에_갈_수_없다() {
        Position currentPosition = new Position(5, 5);
        Position targetPosition = new Position(3, 4);

        testBoard.setAllBlank();
        testBoard.setBlank(new Position(4, 5));

        boolean isCanMove = horse.canMove(currentPosition, targetPosition, testBoard);
        assertThat(isCanMove).isFalse();
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
