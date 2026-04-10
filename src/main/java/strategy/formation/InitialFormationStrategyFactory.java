package strategy.formation;

import java.util.Map;
import java.util.function.Supplier;

public final class InitialFormationStrategyFactory {

    private static final int MIN_CHOICE = 1;
    private static final int MAX_CHOICE = 4;

    private static final Map<Integer, Supplier<InitialFormationStrategy>> CREATORS =
            Map.of(
                    1, InnerFormationStrategy::new,
                    2, OuterFormationStrategy::new,
                    3, LeftFormationStrategy::new,
                    4, RightFormationStrategy::new);

    private InitialFormationStrategyFactory() {}

    public static InitialFormationStrategy from(int choice) {
        validate(choice);
        return CREATORS.get(choice).get();
    }

    private static void validate(int choice) {
        if (choice < MIN_CHOICE) {
            throw new IllegalArgumentException("상차림 번호는 1~4 사이여야 합니다.");
        }
        if (choice > MAX_CHOICE) {
            throw new IllegalArgumentException("상차림 번호는 1~4 사이여야 합니다.");
        }
    }
}
