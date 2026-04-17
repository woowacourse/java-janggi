package domain.piece;

import domain.TestFixture;
import domain.position.Position;
import domain.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class PawnTest {

    private TestFixture testBoard;
    private Pawn pawn;

    @BeforeEach
    void setUp() {
        pawn = new Pawn(Team.CHO);
        testBoard = new TestFixture();
    }

    @Test
    void 졸이_목적지에_갈_수_있다() {
        Position currentPosition = new Position(8, 4);
        Position targetPosition = new Position(7, 4);

        testBoard.setAllBlank();
        boolean isPawnMove = pawn.canMove(currentPosition, targetPosition, testBoard);
        assertThat(isPawnMove).isTrue();

    }

    @Test
    void 졸은_뒤로_갈_수_없다() {
        Position currentPosition = new Position(6, 4);
        Position targetPosition = new Position(9, 4);

        testBoard.setAllBlank();
        testBoard.setPiece(new Position(9, 4), new Pawn(Team.CHO));

        assertThatThrownBy(() -> pawn.canMove(currentPosition, targetPosition, testBoard))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 위치로 이동할 수 없는 기물입니다.");

    }

    @Test
    void 목적지에_같은_팀_기물이_있다면_졸은_목적지에_갈_수_없다() {
        Position currentPosition = new Position(8, 4);
        Position targetPosition = new Position(7, 4);

        testBoard.setAllBlank();
        testBoard.setPiece(new Position(7, 4), new Pawn(Team.CHO));

        assertThatThrownBy(() -> pawn.canMove(currentPosition, targetPosition, testBoard))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 같은 팀의 기물은 잡을 수 없습니다.");
    }
}
