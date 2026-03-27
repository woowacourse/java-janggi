package janggiBoard.PieceTest;

import domain.Position;
import domain.Team;
import domain.piece.Blank;
import domain.piece.Car;
import domain.piece.Piece;
import domain.piece.PieceProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class CarTest {

    private Car car;
    private TestPieceProvider testBoard;

    @BeforeEach
    public void setUp() {
        car = new Car(Team.CHO);
        testBoard = new TestPieceProvider();
    }

    @Test
    void 차는_목적지에_갈_수_있다() {
        Position currentPosition = new Position(5, 5);
        Position targetPosition = new Position(7, 5);

        testBoard.setAllBlank();
        boolean isCarMove = car.canMove(currentPosition, targetPosition, testBoard);
        assertThat(isCarMove).isTrue();
    }

    @Test
    void 차는_목적지에_갈_수_없다() {
        Position currentPosition = new Position(5, 5);
        Position targetPosition = new Position(7, 5);

        testBoard.setAllBlank();
        testBoard.setBlank(new Position(6, 5));

        boolean isCarMove = car.canMove(currentPosition, targetPosition, testBoard);

        assertThat(isCarMove).isFalse();
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
