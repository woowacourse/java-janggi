package boardSetting.strategyTest;

import boardSetting.TestFixture;
import domain.Team;
import domain.position.Position;
import domain.strategy.PalaceStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class PalaceStrategyTest {

    private PalaceStrategy palaceStrategy;
    private TestFixture testBoard;

    @BeforeEach
    void setUp() {
        palaceStrategy = new PalaceStrategy();
        testBoard = new TestFixture();
    }

    @Test
    void 궁과_사가_주변에_장애물이_없다면_8가지_후보_모두_반환() {
        Position currentPosition = new Position(5, 5);
        testBoard.setAllBlank();

        List<Position> candidates = palaceStrategy.getMoveCandidates(currentPosition, Team.CHO, testBoard);

        assertThat(candidates).hasSize(8)
                .containsExactlyInAnyOrder(
                        new Position(4, 5), new Position(4, 4),
                        new Position(4, 6), new Position(5, 4),
                        new Position(5, 6), new Position(6, 4),
                        new Position(6, 5), new Position(6, 6)
                );
    }
}
