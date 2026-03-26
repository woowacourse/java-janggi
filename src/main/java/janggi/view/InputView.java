package janggi.view;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class InputView {
    private static final String CHOICE_REGEX = "[^1234]";
    private static final int CHOICE_MIN_SIZE = 2;
    private final Scanner sc;

    public InputView() {
        sc = new Scanner(System.in);
    }

    public List<Integer> readOpeningFormationChoice() {
        System.out.println("한과 초의 차림을 선택하세요.(쉼표로 구분)\n" +
                "1. 왼상차림 (상마상마)\n" +
                "2. 오른상 차림 (마상마상)\n" +
                "3. 안상 차림 (마상상마)\n" +
                "4. 바깥상 차림 (상마마상)");
        List<Integer> choices = Arrays.stream(sc.nextLine().split(","))
                .map(String::trim)
                .peek(this::validateChoiceNumber)
                .map(Integer::parseInt)
                .toList();

        validateChoicesSize(choices);
        return choices;
    }

    private void validateChoiceNumber(String input) {
        if (input.matches(CHOICE_REGEX)) {
            throw new IllegalArgumentException("차림 선택은 1~4번까지 가능합니다.");
        }
    }

    private void validateChoicesSize(List<Integer> choices) {
        if (choices.size() != CHOICE_MIN_SIZE) {
            throw new IllegalArgumentException("올바르지 않은 차림 선택입니다.");
        }
    }
}
