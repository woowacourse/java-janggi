package janggi.domain.board;

import janggi.domain.dynasty.Dynasty;

public enum HorseElephantPosition {

    HEHE(2, 3, 7, 8),
    HEEH(2, 3, 8, 7),
    EHEH(3, 2, 8, 7),
    EHHE(3, 2, 7, 8);

    private final int firstHorseColumn;
    private final int firstElephantColumn;
    private final int secondHorseColumn;
    private final int secondElephantColumn;

    HorseElephantPosition(int firstHorseColumn, int firstElephantColumn,
                          int secondHorseColumn, int secondElephantColumn) {
        this.firstHorseColumn = firstHorseColumn;
        this.firstElephantColumn = firstElephantColumn;
        this.secondHorseColumn = secondHorseColumn;
        this.secondElephantColumn = secondElephantColumn;
    }
}
