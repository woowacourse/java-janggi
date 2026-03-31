package domain.board;

import domain.place.Place;
import domain.place.moveStrategy.ElephantMoveStrategy;
import domain.place.moveStrategy.HorseMoveStrategy;
import domain.place.palaceMoveStrategy.PalaceEmptyMoveStrategy;
import domain.place.piece.Elephant;
import domain.place.piece.Horse;
import domain.place.piece.Side;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public enum HorseElephantFormation {
    SANG_MA_SANG_MA("상마상마", List.of(HorseElephantFormation::elephant,
            HorseElephantFormation::horse,
            HorseElephantFormation::elephant,
            HorseElephantFormation::horse)),
    MA_SANG_MA_SANG("마상마상", List.of(HorseElephantFormation::horse,
            HorseElephantFormation::elephant,
            HorseElephantFormation::horse,
            HorseElephantFormation::elephant)),
    SANG_MA_MA_SANG("상마마상", List.of(HorseElephantFormation::elephant,
            HorseElephantFormation::horse,
            HorseElephantFormation::horse,
            HorseElephantFormation::elephant)),
    MA_SANG_SANG_MA("마상상마", List.of(HorseElephantFormation::horse,
            HorseElephantFormation::elephant,
            HorseElephantFormation::elephant,
            HorseElephantFormation::horse));

    private final String name;
    private final List<Function<Side, Place>> formationMethod;

    HorseElephantFormation(String name, List<Function<Side, Place>> formationMethod) {
        this.name = name;
        this.formationMethod = formationMethod;
    }

    public static HorseElephantFormation from(String input) {
        return Arrays.stream(values())
                .filter(h -> h.name.equals(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 잘못된 배치 입력입니다."));
    }

    private static Place horse(Side side) {
        return new Horse(side, new HorseMoveStrategy(), new PalaceEmptyMoveStrategy());
    }

    private static Place elephant(Side side) {
        return new Elephant(side, new ElephantMoveStrategy(), new PalaceEmptyMoveStrategy());
    }

    public List<Function<Side, Place>> getFormationMethod() {
        return formationMethod;
    }

}
