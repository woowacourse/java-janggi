package view;

import domain.position.ChessPosition;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import util.LoopTemplate;

public class InputView {

    private static final Pattern POSITION_PATTERN = Pattern.compile("\\d+,\\d+");

    public ChessPosition readFromPosition() {
        return LoopTemplate.tryCatchLoop(() -> {
            System.out.println("기물 선택: 기물의 위치를 입력하세요.");
            final String input = readInput();
            validatePositionPattern(input);
            final List<Integer> position = parseToNumbers(input);
            return new ChessPosition(position.getFirst(), position.getLast());
        });
    }

    public ChessPosition readToPosition() {
        return LoopTemplate.tryCatchLoop(() -> {
            System.out.println("기물 이동: 기물을 놓을 위치를 입력하세요.");
            final String input = readInput();
            validatePositionPattern(input);
            final List<Integer> position = parseToNumbers(input);
            return new ChessPosition(position.getFirst(), position.getLast());
        });
    }

    public String readInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private void validatePositionPattern(final String input) {
        if (!POSITION_PATTERN.matcher(input).matches()) {
            throw new IllegalArgumentException("좌표는 (a,b)형식이어야 합니다.");
        }
    }

    private List<Integer> parseToNumbers(final String input) {
        try {
            return Arrays.stream(input.split(","))
                    .map(Integer::parseInt)
                    .toList();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("좌표는 숫자여야 합니다.");
        }
    }
}
