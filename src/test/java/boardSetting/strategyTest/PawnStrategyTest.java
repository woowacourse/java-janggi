package boardSetting.strategyTest;

import boardSetting.TestFixture;
import domain.Team;
import domain.position.Position;
import domain.strategy.PawnStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PawnStrategyTest {

    private PawnStrategy pawnStrategy;
    private TestFixture testBoard;

    @BeforeEach
    void setUp() {
        pawnStrategy = new PawnStrategy();
        testBoard = new TestFixture();
    }

    @Test
    void 졸은_4가지_이동_후보_모두_반환() {
        Position currentPosition = new Position(5, 5);
        testBoard.setAllBlank();

        List<Position> candidates = pawnStrategy.getMoveCandidates(currentPosition, Team.CHO, testBoard);

        assertThat(candidates).hasSize(3)
                .containsExactlyInAnyOrder(
                        new Position(4, 5), new Position(5, 4), new Position(5, 6)
                );
    }
}
