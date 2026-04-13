package strategy.formation;

import static org.assertj.core.api.Assertions.assertThat;

import domain.game.FormationType;
import org.junit.jupiter.api.Test;

class FormationStrategyFactoryTest {
    private final FormationStrategyFactory formationStrategyFactory = new FormationStrategyFactory();

    @Test
    void 상차림_타입에_맞는_초기_배치_전략을_생성한다() {
        assertThat(formationStrategyFactory.create(FormationType.INNER)).isInstanceOf(InnerFormationStrategy.class);
        assertThat(formationStrategyFactory.create(FormationType.OUTER)).isInstanceOf(OuterFormationStrategy.class);
        assertThat(formationStrategyFactory.create(FormationType.LEFT)).isInstanceOf(LeftFormationStrategy.class);
        assertThat(formationStrategyFactory.create(FormationType.RIGHT)).isInstanceOf(RightFormationStrategy.class);
    }
}
