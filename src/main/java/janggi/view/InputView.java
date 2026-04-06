package janggi.view;

import janggi.domain.Position;
import janggi.exception.BusinessException;
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

    private void validateSize(List<Integer> coordinates) {
        if (coordinates.size() != 2) {
            throw new BusinessException("좌표는 두 개의 숫자(예: 1, 2)로 입력해야 합니다.");
        }
    }

    private static List<Integer> parseToIntegers(String input) {
        try {
            return Arrays.stream(input.split(DELIMITER))
                    .map(String::trim)
                    .map(Integer::parseInt)
                    .toList();
        } catch (NumberFormatException e) {
            throw new BusinessException("좌표는 숫자로 입력해야 합니다.");
        }
    }

    private static void validateEmpty(String input) {
        if (input == null || input.isBlank()) {
            throw new BusinessException("입력값이 비어있습니다.");
        }
    }

    private static void validateFormat(String input) {
        if (!input.contains(DELIMITER)) {
            throw new BusinessException("쉼표(,)를 기준으로 입력하세요.");
        }
    }

    public record MoveCommand(List<Integer> from, List<Integer> to) {
        public Position fromPosition() {
            return Position.from(from);
        }

        public Position toPosition() {
            return Position.from(to);
        }
    }
}
