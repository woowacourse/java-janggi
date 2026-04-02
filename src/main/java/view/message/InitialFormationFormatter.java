package view.message;

import domain.board.formation.InitialFormationType;

import java.util.Arrays;

public enum InitialFormationFormatter {

    TYPE_1(1, InitialFormationType.OUTSIDE_MA, "마-상-상-마 (외마)"),
    TYPE_2(2, InitialFormationType.RIGHT_SANG, "마-상-마-상 (오른상)"),
    TYPE_3(3, InitialFormationType.INSIDE_MA, "상-마-마-상 (안마)"),
    TYPE_4(4, InitialFormationType.LEFT_SANG, "상-마-상-마 (왼상)");

    private final int index;
    private final InitialFormationType formation;
    private final String message;

    InitialFormationFormatter(int index, InitialFormationType formation, String message) {
        this.index = index;
        this.formation = formation;
        this.message = message;
    }

    public static String format(int index) {
        return findBy(index).message;
    }

    public static InitialFormationType from(int index) {
        return findBy(index).formation;
    }

    private static InitialFormationFormatter findBy(int index) {
        return Arrays.stream(values())
                .filter(v -> v.index == index)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 차림 번호입니다: " + index));
    }
}
