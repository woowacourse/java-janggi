package domain.piece;

import domain.TestFixture;
import domain.position.Position;
import domain.Team;
import domain.piece.Horse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class HorseTest {
    private TestFixture testBoard;
    private Horse horse;

    @BeforeEach
    void setUp() {
        horse = new Horse(Team.CHO);
        testBoard = new TestFixture();
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
        testBoard.setPiece(new Position(4, 5), new Pawn(Team.CHO));

        boolean isCanMove = horse.canMove(currentPosition, targetPosition, testBoard);
        assertThat(isCanMove).isFalse();
    }
}
