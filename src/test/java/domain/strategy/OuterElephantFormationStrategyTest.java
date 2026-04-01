package domain.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Position;
import domain.Team;
import domain.piece.Elephant;
import domain.piece.Horse;
import domain.piece.Piece;
import java.util.Map;
import org.junit.jupiter.api.Test;
import strategy.InitializeStrategy;
import strategy.OuterElephantFormationStrategy;

public class OuterElephantFormationStrategyTest {
    private final InitializeStrategy strategy = new OuterElephantFormationStrategy();

    @Test
    void 초나라_상마마상_배치가_올바르게_생성된다() {
        Map<Position, Piece> result = strategy.initialize(Team.CHO);

        assertThat(result.get(Position.from(10, 2)))
                .isInstanceOf(Elephant.class);
        assertThat(result.get(Position.from(10, 3)))
                .isInstanceOf(Horse.class);
        assertThat(result.get(Position.from(10, 8)))
                .isInstanceOf(Elephant.class);
        assertThat(result.get(Position.from(10, 7)))
                .isInstanceOf(Horse.class);
    }

    @Test
    void 한나라_상마마상_배치가_올바르게_생성된다() {
        Map<Position, Piece> result = strategy.initialize(Team.HAN);

        assertThat(result.get(Position.from(1, 2)))
                .isInstanceOf(Elephant.class);
        assertThat(result.get(Position.from(1, 3)))
                .isInstanceOf(Horse.class);
        assertThat(result.get(Position.from(1, 8)))
                .isInstanceOf(Elephant.class);
        assertThat(result.get(Position.from(1, 7)))
                .isInstanceOf(Horse.class);
    }
}
