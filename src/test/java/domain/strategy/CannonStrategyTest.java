package domain.strategy;

import domain.TestFixture;
import domain.position.Position;
import domain.Team;
import domain.piece.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
 public class CannonStrategyTest {

    private CannonStrategy cannonStrategy;
    private TestFixture testBoard;
    private final Position currentPosition = new Position(0, 0);

    @BeforeEach
    public void setUp() {
        cannonStrategy = new CannonStrategy();
        testBoard = new TestFixture();
        testBoard.setPiece(currentPosition, new Cannon(Team.CHO));
    }

    @Test
    void 포가_기물_한개를_넘어_빈칸으로_이동하는지_확인한다() {
        testBoard.setPiece(new Position(2, 0), new Guard(Team.HAN));

        List<Position> candidates = cannonStrategy.getMoveCandidates(currentPosition, Team.CHO, testBoard);

        assertThat(candidates).contains(new Position(3, 0), new Position(9, 0));
        assertThat(candidates).doesNotContain(new Position(1, 0), new Position(2, 0));
    }

    @Test
    void 포는_다른포를_건너뛸_수_없다() {
        testBoard.setPiece(new Position(2, 0), new Cannon(Team.HAN));

        List<Position> candidates = cannonStrategy.getMoveCandidates(currentPosition, Team.CHO, testBoard);

        assertThat(candidates).isEmpty();
    }

     @Test
     void 포는_적_기물을_건너뛸_수_있다() {
         Position currentPosition = new Position(0, 0);
         testBoard.setPiece(new Position(4, 0), new Pawn(Team.HAN));

         List<Position> candidates = cannonStrategy.getMoveCandidates(currentPosition, Team.CHO, testBoard);
         assertThat(candidates).contains(new Position(5, 0));
         assertThat(candidates).doesNotContain(new Position(3, 0));
     }

    @Test
    void 포는_기물이_없으면_이동할_수_없다() {
        Position currentPosition = new Position(0, 0);
        testBoard.setAllBlank();

        List<Position> candidates = cannonStrategy.getMoveCandidates(currentPosition, Team.CHO, testBoard);
        assertThat(candidates.size()).isEqualTo(0);
    }

    @Test
    void 포는_다른포를_잡을_수_없다() {
        Position currentPosition = new Position(0, 0);
        testBoard.setPiece(new Position(2, 0), new Guard(Team.CHO));
        testBoard.setPiece(new Position(4, 0), new Cannon(Team.HAN));

        List<Position> candidates = cannonStrategy.getMoveCandidates(currentPosition, Team.CHO, testBoard);
        assertThat(candidates).contains(new Position(3, 0));
        assertThat(candidates).doesNotContain(new Position(4, 0));
    }
}
