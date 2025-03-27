package janggi.controller;

import java.util.Arrays;

public enum MainOption {
    NEW_GAME(1),
    CONTINUE_GAME(2),
    ;

    MainOption(int optionNumber) {
        this.optionNumber = optionNumber;
    }

    private final int optionNumber;

    public static MainOption from(int optionNumber) {
        return Arrays.stream(MainOption.values())
                .filter(mainOption -> mainOption.optionNumber == optionNumber)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("해당 번호의 옵션이 존재하지 않습니다."));
    }
}
