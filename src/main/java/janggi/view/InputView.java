package janggi.view;

import janggi.domain.Position;
import janggi.exception.input.EmptyInputException;
import janggi.exception.input.InvalidInputFormatException;
import janggi.exception.input.InvalidInputSizeException;
import janggi.exception.input.NotNumberException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class InputView {
    private static final String DELIMITER = ",";
    private final Scanner sc;

    public InputView() {
        this.sc = new Scanner(System.in);
    }

    public MoveCommand readMoveCommand(String team) {
        System.out.println(team + "나라 턴입니다.");

        List<Integer> from = readCoordinates("이동할 기물의 좌표를 입력하세요. (예 : 1, 2)");
        List<Integer> to = readCoordinates("도착할 좌표를 입력하세요. (예 : 1, 3)");

        return new MoveCommand(from, to);
    }

    private List<Integer> readCoordinates(String message) {
        System.out.println(message);
        String input = sc.nextLine();

        validateEmpty(input);
        validateFormat(input);

        List<Integer> coordinates = parseToIntegers(input);
        validateSize(coordinates);

        return coordinates;
    }

    private void validateEmpty(String input) {
        if (input == null || input.isBlank()) {
            throw new EmptyInputException();
        }
    }

    private void validateFormat(String input) {
        if (!input.contains(DELIMITER)) {
            throw new InvalidInputFormatException();
        }
    }

    private List<Integer> parseToIntegers(String input) {
        if (hasNonNumeric(input)) {
            throw new NotNumberException();
        }

        return Arrays.stream(input.split(DELIMITER))
                .map(String::trim)
                .map(Integer::parseInt)
                .toList();
    }

    private boolean hasNonNumeric(String input) {
        return !input.chars()
                .allMatch(c -> Character.isDigit(c) || c == DELIMITER.charAt(0) || Character.isWhitespace(c));
    }

    private void validateSize(List<Integer> coordinates) {
        if (coordinates.size() != 2) {
            throw new InvalidInputSizeException();
        }
    }

    public String readMenuCommand() {
        return sc.nextLine();
    }

    public record MoveCommand(List<Integer> from, List<Integer> to) {
        public Position fromPosition() {
            return Position.from(from);
        }

        public Position toPosition() {
            return Position.from(to);
        }
    }

    public String readListAction() {
        System.out.println("\n명령어를 입력하세요.");
        System.out.println("입장: play [ID] / 삭제: delete [ID] / 이전 메뉴: back");
        System.out.print("(예: play 1 혹은 delete 1 혹은 back) : ");

        String input = sc.nextLine();
        validateNotEmpty(input);

        return input;
    }

    private void validateNotEmpty(String input) {
        if (input == null || input.isBlank()) {
            throw new EmptyInputException();
        }
    }
}
