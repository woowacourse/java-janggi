package controller;

import domain.board.formation.FormationType;

public class PlacementInputMapper {

    private static final String ERROR_INVALID_INPUT = "잘못된 입력 값입니다. 1~4 값을 입력해주세요.";

    public static FormationType toFormationType(String input) {
        if ("1".equals(input)) return FormationType.DEFAULT;
        if ("2".equals(input)) return FormationType.INNER_HORSE;
        if ("3".equals(input)) return FormationType.LEFT_INNER_HORSE;
        if ("4".equals(input)) return FormationType.RIGHT_INNER_HORSE;
        throw new IllegalArgumentException(ERROR_INVALID_INPUT);
    }
}
