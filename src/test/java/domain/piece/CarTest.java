package domain.piece;

import domain.TestFixture;
import domain.position.Position;
import domain.Team;
import domain.piece.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class CarTest {

    private Car car;
    private TestFixture testBoard;

    @BeforeEach
    public void setUp() {
        car = new Car(Team.CHO);
        testBoard = new TestFixture();
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
        testBoard.setPiece(new Position(6, 5), new Pawn(Team.CHO));

        boolean isCarMove = car.canMove(currentPosition, targetPosition, testBoard);

        assertThat(isCarMove).isFalse();
    }
}
