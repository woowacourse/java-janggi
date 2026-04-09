package view;

import common.exception.JanggiException;

import java.util.List;
import java.util.Scanner;

public class InputView {

    private final Scanner scanner = new Scanner(System.in);

    public String askChoPlayerName() {
        return askPlayerName("선공");
    }

    public String askHanPlayerName() {
        return askPlayerName("후공");
    }

    private String askPlayerName(String team) {
        System.out.println(team + " 닉네임을 입력하세요.");
        return scanner.nextLine();
    }

    public int askChoPositionInput() {
        return askPositionInput("선공");
    }

    public int askHanPositionInput() {
        return askPositionInput("후공");
    }

    private int askPositionInput(String team) {
        System.out.println(team + " 배치 선택 (1-상마상마, 2-마상마상, 3-마상상마, 4-상마마상)");
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new JanggiException("숫자만 입력해주세요.");
        }
    }

    public int askGameMode() {
        printGameModeMenu();
        return parseGameMode();
    }

    private void printGameModeMenu() {
        System.out.println("게임 모드를 선택하세요.");
        System.out.println("1. 새 게임");
        System.out.println("2. 저장된 게임 불러오기");
    }

    private int parseGameMode() {
        try {
            int mode = Integer.parseInt(scanner.nextLine());
            if (mode != 1 && mode != 2) {
                throw new JanggiException("1 또는 2를 입력하세요.");
            }
            return mode;
        } catch (NumberFormatException e) {
            throw new JanggiException("숫자를 입력하세요.");
        }
    }

    public int askSelectGame(int gameCount) {
        System.out.println(buildGameSelectionMessage(gameCount));
        return parseGameChoice(gameCount);
    }

    private String buildGameSelectionMessage(int gameCount) {
        StringBuilder message = new StringBuilder("게임을 선택하세요. (");
        for (int i = 1; i <= gameCount; i++) {
            message.append(i).append("-저장된 게임");
            if (i < gameCount) {
                message.append(", ");
            }
        }
        message.append(", ").append(gameCount + 1).append("-새 게임)");
        return message.toString();
    }

    private int parseGameChoice(int gameCount) {
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            if (choice < 1 || choice > gameCount + 1) {
                throw new JanggiException("1부터 " + (gameCount + 1) + " 사이의 숫자를 입력하세요.");
            }
            return choice;
        } catch (NumberFormatException e) {
            throw new JanggiException("숫자를 입력하세요.");
        }
    }

    public List<Integer> askSourcePosition() {
        System.out.println("기물 위치를 입력하세요. (형식 : 행 열)");
        return askPosition();
    }

    public List<Integer> askDestinationPosition() {
        System.out.println("이동 위치를 입력하세요. (형식 : 행 열)");
        return askPosition();
    }

    private List<Integer> askPosition() {
        try {
            List<String> splitString = List.of(scanner.nextLine().split(" "));
            return parsePositionNumbers(splitString);
        } catch (NumberFormatException e) {
            throw new JanggiException("숫자 두 개를 공백으로 구분하여 입력하세요.");
        }
    }

    private List<Integer> parsePositionNumbers(List<String> splitString) {
        List<Integer> numbers = splitString.stream()
                .map(Integer::parseInt)
                .toList();
        if (numbers.size() != 2) {
            throw new JanggiException("숫자 두 개를 공백으로 구분하여 입력하세요.");
        }
        return numbers;
    }
}
