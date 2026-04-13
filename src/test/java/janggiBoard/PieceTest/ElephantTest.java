package janggiBoard.PieceTest;

import domain.Position;
import domain.Team;
import domain.piece.Blank;
import domain.piece.Elephant;
import domain.piece.Piece;
import domain.piece.PieceProvider;
import domain.strategy.ElephantStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ElephantTest {

    private TestPieceProvider testBoard;
    private Elephant elephant;

    @BeforeEach
    void setUp() {
        elephant = new Elephant(Team.CHO, new ElephantStrategy());
        testBoard = new TestPieceProvider();
    }

    @Test
    void 상이_목적지에_갈_수_있다() {
        Position currentPosition = new Position(0, 2);
        Position targetPosition = new Position(3, 4);

        testBoard.setAllBlank();
        boolean isCanMove = elephant.canMove(currentPosition, targetPosition, testBoard);
        assertThat(isCanMove).isTrue();
    }

    @Test
    void 상이_목적지에_갈_수_없다() {
        Position currentPosition = new Position(5, 5);
        Position targetPosition = new Position(3, 8);

        testBoard.setAllBlank();
        testBoard.setBlank(new Position(5, 6));

        boolean isCanMove = elephant.canMove(currentPosition, targetPosition, testBoard);
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
