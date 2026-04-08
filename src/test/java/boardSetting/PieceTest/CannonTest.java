package boardSetting.PieceTest;

import boardSetting.TestFixture;
import domain.position.Position;
import domain.Team;
import domain.piece.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CannonTest {

    private Cannon cannon;
    private TestFixture testBoard;

    @BeforeEach
    public void setUp() {
        cannon = new Cannon(Team.CHO);
        testBoard = new TestFixture();
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
}
