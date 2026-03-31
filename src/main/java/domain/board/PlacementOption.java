package domain.board;

import domain.board.cho.DefaultChoPlacement;
import domain.board.cho.InnerHorseChoPlacement;
import domain.board.cho.LeftInnerHorseChoPlacement;
import domain.board.cho.RightInnerHorseChoPlacement;
import domain.board.han.DefaultHanPlacement;
import domain.board.han.InnerHorseHanPlacement;
import domain.board.han.LeftInnerHorseHanPlacement;
import domain.board.han.RightInnerHorseHanPlacement;
import java.util.Arrays;

public enum PlacementOption {

    HAN_DEFAULT("1", new DefaultHanPlacement()),
    HAN_INNER_HORSE("2", new InnerHorseHanPlacement()),
    HAN_LEFT_INNER_HORSE("3", new LeftInnerHorseHanPlacement()),
    HAN_RIGHT_INNER_HORSE("4", new RightInnerHorseHanPlacement()),

    CHO_DEFAULT("1", new DefaultChoPlacement()),
    CHO_INNER_HORSE("2", new InnerHorseChoPlacement()),
    CHO_LEFT_INNER_HORSE("3", new LeftInnerHorseChoPlacement()),
    CHO_RIGHT_INNER_HORSE("4", new RightInnerHorseChoPlacement());

    private static final String ERROR_INVALID_PLACEMENT_OPTION = "잘못된 입력 값입니다. 1~4 값을 입력해주세요.";

    private final String input;
    private final PlacementStrategy strategy;

    PlacementOption(String input, PlacementStrategy strategy) {
        this.input = input;
        this.strategy = strategy;
    }

    public static PlacementStrategy hanFrom(String input) {
        return Arrays.stream(values())
                .filter(option -> option.name().startsWith("HAN"))
                .filter(option -> option.input.equals(input))
                .map(option -> option.strategy)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ERROR_INVALID_PLACEMENT_OPTION));
    }

    public static PlacementStrategy choFrom(String input) {
        return Arrays.stream(values())
                .filter(option -> option.name().startsWith("CHO"))
                .filter(option -> option.input.equals(input))
                .map(option -> option.strategy)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ERROR_INVALID_PLACEMENT_OPTION));
    }
}
