package view;

import domain.Team;
import exceptions.JanggiGameRuleWarningException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class InputView {
    private static final Scanner scanner = new Scanner(System.in);
    private static final int START_POINT_INDEX = 0;
    private static final int ARRIVAL_POINT_INDEX = 1;

    public int readChoiceForElephantLocation(final String team) {
        System.out.println(team + "의 입력 차례입니다.");
        System.out.println("""
                마와 상의 배치를 선택해주세요.
                1. 바깥상 차림(상마마상)
                2. 안상 차림(마상상마)
                3. 왼상 차림(상마상마)
                4. 오른상 차림(마상마상)
                """);
        return parseToInt(scanner.nextLine());
    }

    public List<List<Integer>> readMovementRequest(final Team team) {
        System.out.println(team.toString() + "의 입력 차례입니다.");
        System.out.println("출발점과 도착점의 위치를 입력해주세요 ex. (A,1) (B,2)");
        final String input = scanner.nextLine();
        final String[] splitInput = input.split(" ");
        if (splitInput.length != 2) {
            throw new JanggiGameRuleWarningException("출발점과 도착점 정보가 잘못 입력되었습니다: " + input);
        }
        final List<Integer> startPoint = formatToIntegerList(splitInput[START_POINT_INDEX]);
        final List<Integer> arrivalPoint = formatToIntegerList(splitInput[ARRIVAL_POINT_INDEX]);
        return List.of(startPoint, arrivalPoint);
    }

    private List<Integer> formatToIntegerList(final String splitInput) {
        final String parseInput = splitInput.replaceAll("[()]", "");
        final String[] split = parseInput.split(",");
        final List<Integer> parsedInputs = new ArrayList<>();
        parsedInputs.add(parseRowToInt(split[0]));
        parsedInputs.add(parseToInt(split[1]));
        return parsedInputs;
    }

    private int parseToInt(final String split) {
        try {
            return Integer.parseInt(split);
        } catch (NumberFormatException e) {
            throw new JanggiGameRuleWarningException("입력값이 숫자여야 합니다: " + split);
        }
    }

    private int parseRowToInt(final String split) {
        final char c = split.charAt(0);
        return 'J' - c;
    }
}
