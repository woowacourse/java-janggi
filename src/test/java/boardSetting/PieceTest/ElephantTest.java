package boardSetting.PieceTest;

import boardSetting.TestFixture;
import domain.position.Position;
import domain.Team;
import domain.piece.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ElephantTest {

    private TestFixture testBoard;
    private Elephant elephant;

    @BeforeEach
    void setUp() {
        elephant = new Elephant(Team.CHO);
        testBoard = new TestFixture();
    }

    @Test
    void 상이_목적지에_갈_수_있다() {
        Position currentPosition = new Position(5, 5);
        Position targetPosition = new Position(3, 8);

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
}
