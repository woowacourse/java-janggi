package domain.strategy;

import domain.TestFixture;
import domain.Team;
import domain.position.Position;
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
        Position currentPosition = new Position(8,4);
        testBoard.setAllBlank();

        List<Position> candidates = palaceStrategy.getMoveCandidates(currentPosition, Team.CHO, testBoard);

        assertThat(candidates).hasSize(8)
                .containsExactlyInAnyOrder(
                        new Position(7,3), new Position(7,4),
                        new Position(7,5), new Position(8,3),
                        new Position(8,5), new Position(9,3),
                        new Position(9,4), new Position(9,5)
                );
    }

    @Test
    void 궁과_사는_궁성_내부에서만_이동한다() {
        Position currentPosition = new Position(8,3);
        testBoard.setAllBlank();

        List<Position> candidates = palaceStrategy.getMoveCandidates(currentPosition, Team.CHO, testBoard);

        assertThat(candidates).hasSize(5)
                .containsExactlyInAnyOrder(
                        new Position(7,3), new Position(7,4),
                        new Position(8,4), new Position(9,3),
                        new Position(9,4)
                );
    }
}
