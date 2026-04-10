package janggi.view;

import janggi.dto.PositionInputDto;
import janggi.exception.input.EmptyCoordinateException;
import janggi.exception.input.EmptyInputException;
import janggi.exception.input.InvalidDelimiterException;
import janggi.exception.input.InvalidGameIdException;
import janggi.exception.input.InvalidMenuNumberException;
import janggi.exception.input.NonNumericInputException;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class InputView {
    private static final List<Integer> EXIT = null;
    private final Scanner sc;

    public InputView() {
        this.sc = new Scanner(System.in);
    }

    public String readMenu() {
        String input = sc.nextLine().trim();

        if (!input.matches("[1-3]")) {
            throw new InvalidMenuNumberException();
        }
        return input;
    }

    public int readGameId() {
        System.out.println("진입할 방 번호를 입력하세요. (메뉴로 돌아가고 싶으면 : -1)");
        String input = sc.nextLine().trim();

        if (input.equals("-1")) {
            return Integer.parseInt(input);
        }

        if (input.isEmpty() || !input.matches("\\d+")) {
            throw new InvalidGameIdException();
        }

        return Integer.parseInt(input);
    }

    public void waitBeforeReturning() {
        System.out.println("메인 메뉴로 돌아가려면 Enter를 누르세요...");
        sc.nextLine();
    }

    public PositionInputDto playTurn(String team) {
        System.out.println(team + "나라 턴입니다.");

        List<Integer> from = readCoordinates("이동할 기물의 좌표를 입력하세요. (예 : 1, 2 / 종료 : exit)");
        if (from == EXIT) {
            return null;
        }

        List<Integer> to = readCoordinates("도착할 좌표를 입력하세요. (예 : 1, 2 / 종료 : exit)");
        if (to == EXIT) {
            return null;
        }

        return new PositionInputDto(from.get(0), from.get(1), to.get(0), to.get(1));
    }

    private List<Integer> readCoordinates(String message) {
        System.out.println(message);
        String input = sc.nextLine();

        if (input.equals("exit")) {
            return EXIT;
        }

        validateText(input);
        validateDelimiter(input);

        String[] splitNumbers = input.split(",");

        if (splitNumbers.length != 2) {
            throw new InvalidDelimiterException();
        }

        return Arrays.stream(splitNumbers)
                .map(String::trim)
                .peek(this::validateEmptyToken)
                .map(this::validateNumber)
                .toList();
    }

    private void validateText(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new EmptyInputException();
        }
    }

    private void validateDelimiter(String input) {
        if (!input.contains(",")) {
            throw new InvalidDelimiterException();
        }
    }

    private int validateNumber(String token) {
        try {
            return Integer.parseInt(token);
        } catch (NumberFormatException e) {
            throw new NonNumericInputException();
        }
    }

    private void validateEmptyToken(String token) {
        if (token.isEmpty()) {
            throw new EmptyCoordinateException();
        }
    }
}
