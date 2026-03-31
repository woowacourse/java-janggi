package model.board.strategy;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import model.board.HorseElephantStrategy;

public enum ElephantSetup {
    INNER_ELEPHANT("1. 마상상마", new InnerElephant()),
    OUTER_ELEPHANT("2. 상마마상", new OuterElephant()),
    RIGHT_ELEPHANT("3. 마상마상", new RightElephant()),
    LEFT_ELEPHANT("4. 상마상마", new LeftElephant());

    private final String arrangement;
    private final HorseElephantStrategy strategy;

    ElephantSetup(String arrangement, HorseElephantStrategy strategy) {
        this.arrangement = arrangement;
        this.strategy = strategy;
    }

    public static List<String> arrangementList() {
        return Arrays.stream(values())
                .map(setup -> setup.arrangement)
                .collect(Collectors.toList());
    }

    public static HorseElephantStrategy init(int index) {
        validateNumber(index);
        return ElephantSetup.values()[index - 1].strategy;
    }

    private static void validateNumber(int index) {
        if (index < 1 || index > 4) {
            throw new IllegalArgumentException("[ERROR] 올바른 번호를 선택해주세요.");
        }
    }
}
