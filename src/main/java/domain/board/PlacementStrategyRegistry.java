package domain.board;

import domain.board.cho.*;
import domain.board.han.*;

import java.util.Map;

public final class PlacementStrategyRegistry {
    private static final Map<FormationType, PlacementStrategy> CHO = Map.of(
            FormationType.DEFAULT, new DefaultChoPlacement(),
            FormationType.INNER_HORSE, new InnerHorseChoPlacement(),
            FormationType.LEFT_INNER_HORSE, new LeftInnerHorseChoPlacement(),
            FormationType.RIGHT_INNER_HORSE, new RightInnerHorseChoPlacement()
    );

    private static final Map<FormationType, PlacementStrategy> HAN = Map.of(
            FormationType.DEFAULT, new DefaultHanPlacement(),
            FormationType.INNER_HORSE, new InnerHorseHanPlacement(),
            FormationType.LEFT_INNER_HORSE, new LeftInnerHorseHanPlacement(),
            FormationType.RIGHT_INNER_HORSE, new RightInnerHorseHanPlacement()
    );

    public static PlacementStrategy forCho(FormationType type) {
        return CHO.get(type);
    }

    public static PlacementStrategy forHan(FormationType type) {
        return HAN.get(type);
    }
}
