package domain.board.formation;

import domain.board.formation.cho.DefaultChoPlacement;
import domain.board.formation.cho.InnerHorseChoPlacement;
import domain.board.formation.cho.LeftInnerHorseChoPlacement;
import domain.board.formation.cho.RightInnerHorseChoPlacement;
import domain.board.formation.han.DefaultHanPlacement;
import domain.board.formation.han.InnerHorseHanPlacement;
import domain.board.formation.han.LeftInnerHorseHanPlacement;
import domain.board.formation.han.RightInnerHorseHanPlacement;

import java.util.Map;
import view.FormationType;

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
