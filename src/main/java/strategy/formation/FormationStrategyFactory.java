package strategy.formation;

import domain.game.FormationType;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

public class FormationStrategyFactory {
    private static final Map<FormationType, Supplier<InitialFormationStrategy>> STRATEGIES = createStrategies();

    public InitialFormationStrategy create(FormationType formationType) {
        final Supplier<InitialFormationStrategy> strategy = STRATEGIES.get(formationType);
        if (strategy == null) {
            throw new IllegalArgumentException("지원하지 않는 상차림입니다.");
        }
        return strategy.get();
    }

    private static Map<FormationType, Supplier<InitialFormationStrategy>> createStrategies() {
        final Map<FormationType, Supplier<InitialFormationStrategy>> strategies = new EnumMap<>(FormationType.class);
        strategies.put(FormationType.INNER, InnerFormationStrategy::new);
        strategies.put(FormationType.OUTER, OuterFormationStrategy::new);
        strategies.put(FormationType.LEFT, LeftFormationStrategy::new);
        strategies.put(FormationType.RIGHT, RightFormationStrategy::new);
        return strategies;
    }
}
