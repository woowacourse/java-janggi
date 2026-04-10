package janggi.view;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class InputView {
    private static final String CHOICE_REGEX = "[1-4]";
    private static final int CHOICE_MIN_SIZE = 2;
    private static final int POSITION_SIZE = 2;
    private static final String GAME_CHOICE_REGEX = "[1-4]";

    private final Scanner sc;

    public InputView() {
        sc = new Scanner(System.in);
    }

    public List<Integer> readOpeningFormationChoice() {
        System.out.println("한과 초의 차림을 선택하세요.(쉼표로 구분 예:1,4)\n" +
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

    public List<Integer> readEndPiecePosition() {
        System.out.println("도착할 기물의 좌표를 입력하세요.(쉼표로 구분 예:1,1)");
        return readPiecePosition();
    }

    public List<Integer> readStartPiecePosition() {
        System.out.println("이동할 기물의 시작 좌표를 입력하세요.(쉼표로 구분 예:1,3)");
        return readPiecePosition();
    }

    public int readMenuChoice() {
        System.out.println("메뉴를 선택하세요.");
        String input = sc.nextLine().trim();
        if (!input.matches(GAME_CHOICE_REGEX)) {
            throw new IllegalArgumentException("1~4번 중에서 선택해주세요.");
        }
        return Integer.parseInt(input);
    }

    public Long readGameId() {
        System.out.println("입장할 게임방 번호를 입력하세요.");
        String input = sc.nextLine().trim();
        return Long.parseLong(input);
    }

    private List<Integer> readPiecePosition() {
        List<Integer> position = Arrays.stream(sc.nextLine().split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .toList();
        validatePosition(position);
        return position;
    }

    private void validateChoiceNumber(String input) {
        if (!input.matches(CHOICE_REGEX)) {
            throw new IllegalArgumentException("차림 선택은 1~4번까지 가능합니다.");
        }
    }

    private void validateChoicesSize(List<Integer> choices) {
        if (choices.size() != CHOICE_MIN_SIZE) {
            throw new IllegalArgumentException("올바르지 않은 차림 선택입니다.");
        }
    }

    private void validatePosition(List<Integer> choices) {
        if (choices.size() != POSITION_SIZE) {
            throw new IllegalArgumentException("기물의 좌표를 올바르게 입력하세요.");
        }
    }
}
