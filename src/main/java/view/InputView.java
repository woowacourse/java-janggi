package view;

import domain.player.Team;
import dto.Choice;
import exceptions.JanggiGameRuleWarningException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class InputView {
    private static final Scanner scanner = new Scanner(System.in);
    private static final int START_POINT_INDEX = 0;
    private static final int ARRIVAL_POINT_INDEX = 1;

    public Choice readChoiceForLoadOrInitialize() {
        System.out.println("""
                불러올 게임 방의 번호를 입력해 주세요.
                활성화된 방이 아닐 경우 새로운 게임을 시작합니다.""");
        final int input = parseToInt(scanner.nextLine());
        return new Choice(input);
    }

    public Choice readChoiceForElephantLocation(final String team) {
        System.out.println(team + """
                의 입력 차례입니다.
                마와 상의 배치를 선택해주세요.
                1. 바깥상 차림(상마마상)
                2. 안상 차림(마상상마)
                3. 왼상 차림(상마상마)
                4. 오른상 차림(마상마상)
                """);
        final int input = parseToInt(scanner.nextLine());
        return new Choice(input);
    }

    public List<List<Choice>> readMovementRequest(final Team team) {
        System.out.println(team + """
                의 입력 차례입니다.
                출발점과 도착점의 위치를 입력해주세요 ex. (A,1) (B,2)
                """);
        final String input = scanner.nextLine();
        final String[] splitInput = input.split(" ");
        if (splitInput.length != 2) {
            throw new JanggiGameRuleWarningException("출발점과 도착점 정보가 잘못 입력되었습니다: " + input);
        }
        final List<Choice> startPoint = formatToIntegerList(splitInput[START_POINT_INDEX]);
        final List<Choice> arrivalPoint = formatToIntegerList(splitInput[ARRIVAL_POINT_INDEX]);
        return List.of(startPoint, arrivalPoint);
    }

    private List<Choice> formatToIntegerList(final String splitInput) {
        final String parseInput = splitInput.replaceAll("[()]", "");
        final String[] split = parseInput.split(",");
        final List<Choice> parsedInputs = new ArrayList<>();
        parsedInputs.add(new Choice(parseRowToInt(split[0])));
        parsedInputs.add(new Choice(parseToInt(split[1])));
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
