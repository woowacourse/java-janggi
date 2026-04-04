package domain.board;

import domain.place.moveStrategy.ElephantMoveStrategy;
import domain.place.moveStrategy.HorseMoveStrategy;
import domain.place.piece.Elephant;
import domain.place.piece.Horse;
import domain.place.piece.Piece;
import domain.place.piece.Side;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public enum HorseElephantFormation {
    SANG_MA_SANG_MA("1", List.of(HorseElephantFormation::elephant,
            HorseElephantFormation::horse,
            HorseElephantFormation::elephant,
            HorseElephantFormation::horse)),
    MA_SANG_MA_SANG("2", List.of(HorseElephantFormation::horse,
            HorseElephantFormation::elephant,
            HorseElephantFormation::horse,
            HorseElephantFormation::elephant)),
    SANG_MA_MA_SANG("3", List.of(HorseElephantFormation::elephant,
            HorseElephantFormation::horse,
            HorseElephantFormation::horse,
            HorseElephantFormation::elephant)),
    MA_SANG_SANG_MA("4", List.of(HorseElephantFormation::horse,
            HorseElephantFormation::elephant,
            HorseElephantFormation::elephant,
            HorseElephantFormation::horse));

    private final String name;
    private final List<Function<Side, Piece>> formationMethod;

    HorseElephantFormation(String name, List<Function<Side, Piece>> formationMethod) {
        this.name = name;
        this.formationMethod = formationMethod;
    }

    public static HorseElephantFormation from(String input) {
        return Arrays.stream(values())
                .filter(h -> h.name.equals(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 잘못된 배치 번호입니다."));
    }

    private static Piece horse(Side side) {
        return new Horse(side, new HorseMoveStrategy());
    }

    private static Piece elephant(Side side) {
        return new Elephant(side, new ElephantMoveStrategy());
    }

    public List<Function<Side, Piece>> getFormationMethod() {
        return formationMethod;
    }
}
