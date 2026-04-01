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
import strategy.InnerElephantFormationStrategy;

public class InnerElephantFormationStrategyTest {
    private final InitializeStrategy strategy = new InnerElephantFormationStrategy();

    @Test
    void 초나라_마상상마_배치가_올바르게_생성된다() {
        Map<Position, Piece> result = strategy.initialize(Team.CHO);

        assertThat(result.get(Position.from(10, 3)))
                .isInstanceOf(Elephant.class);
        assertThat(result.get(Position.from(10, 2)))
                .isInstanceOf(Horse.class);
        assertThat(result.get(Position.from(10, 7)))
                .isInstanceOf(Elephant.class);
        assertThat(result.get(Position.from(10, 8)))
                .isInstanceOf(Horse.class);
    }

    @Test
    void 한나라_마상상마_배치가_올바르게_생성된다() {
        Map<Position, Piece> result = strategy.initialize(Team.HAN);

        assertThat(result.get(Position.from(1, 3)))
                .isInstanceOf(Elephant.class);
        assertThat(result.get(Position.from(1, 2)))
                .isInstanceOf(Horse.class);
        assertThat(result.get(Position.from(1, 7)))
                .isInstanceOf(Elephant.class);
        assertThat(result.get(Position.from(1, 8)))
                .isInstanceOf(Horse.class);
    }
}
