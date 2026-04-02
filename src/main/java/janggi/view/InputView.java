package janggi.view;

import janggi.dto.PositionInputDto;
import janggi.exception.input.EmptyCoordinateException;
import janggi.exception.input.EmptyInputException;
import janggi.exception.input.InvalidDelimiterException;
import janggi.exception.input.NonNumericInputException;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class InputView {
    private final Scanner sc;

    public InputView() {
        this.sc = new Scanner(System.in);
    }

    public PositionInputDto playTurn(String team) {
        System.out.println(team + "나라 턴입니다.");

        List<Integer> from = readCoordinates("이동할 기물의 좌표를 입력하세요. (예 : 1, 2)");
        List<Integer> to = readCoordinates("도착할 좌표를 입력하세요. (예 : 1, 3)");

        return new PositionInputDto(from.get(0), from.get(1), to.get(0), to.get(1));
    }

    private List<Integer> readCoordinates(String message) {
        System.out.println(message);
        String input = sc.nextLine();

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
