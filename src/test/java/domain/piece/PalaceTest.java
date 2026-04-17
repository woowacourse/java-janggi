package domain.piece;

import domain.TestFixture;
import domain.position.Position;
import domain.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class PalaceTest {

    private TestFixture testBoard;
    private King king;
    private Guard guard;

    @BeforeEach
    void setUp() {
        king = new King(Team.CHO);
        guard = new Guard(Team.CHO);
        testBoard = new TestFixture();
    }

    @Test
    void 궁이_목적지에_갈_수_있다() {
        Position currentPosition = new Position(8, 4);
        Position targetPosition = new Position(9, 4);

        testBoard.setAllBlank();
        boolean isKingMove = king.canMove(currentPosition, targetPosition, testBoard);
        assertThat(isKingMove).isTrue();

    }

    @Test
    void 목적지에_같은_팀_기물이_있다면_궁은_목적지에_갈_수_없다() {
        Position currentPosition = new Position(8, 4);
        Position targetPosition = new Position(9, 4);

        testBoard.setAllBlank();
        testBoard.setPiece(new Position(9, 4), new Pawn(Team.CHO));

        assertThatThrownBy(() -> king.canMove(currentPosition, targetPosition, testBoard))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 같은 팀의 기물은 잡을 수 없습니다.");
    }

    @Test
    void 사가_목적지에_갈_수_있다() {
        Position currentPosition = new Position(9,3);
        Position targetPosition = new Position(9,4);

        testBoard.setAllBlank();
        boolean isGuardMove = guard.canMove(currentPosition, targetPosition, testBoard);
        assertThat(isGuardMove).isTrue();
    }

    @Test
    void 목적지에_같은_팀_기물이_있다면_사는_목적지에_갈_수_없다() {
        Position currentPosition = new Position(9,3);
        Position targetPosition = new Position(9,4);

        testBoard.setAllBlank();
        testBoard.setPiece(new Position(9, 4), new Pawn(Team.CHO));

        assertThatThrownBy(() -> guard.canMove(currentPosition, targetPosition, testBoard))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 같은 팀의 기물은 잡을 수 없습니다.");
    }
}
