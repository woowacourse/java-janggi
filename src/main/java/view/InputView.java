package view;

import domain.Team;
import execptions.JanggiGameRuleWarningException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Scanner;

public final class InputView {

    private static final Scanner scanner = new Scanner(System.in);
    private static final int START_POINT_INDEX = 0;
    private static final int ARRIVAL_POINT_INDEX = 1;

    public EnumMap<Team, Integer> readChoicesForSetup() {
        final EnumMap<Team, Integer> elephantLocatorByTeam = new EnumMap<>(Team.class);
        for (final Team team : Team.getActualTeams()) {
            final int choice = readChoiceForElephantLocation();
            elephantLocatorByTeam.put(team, choice);
        }
        return elephantLocatorByTeam;
    }

    public List<List<Integer>> readMovementRequest() {
        System.out.println("출발점과 도착점의 위치를 입력해주세요 ex. A,1 B,2");
        final String input = scanner.nextLine();
        final String[] splitInput = input.split(" ");
        final List<Integer> startPoint = formatToIntegerList(splitInput[START_POINT_INDEX]);
        final List<Integer> arrivalPoint = formatToIntegerList(splitInput[ARRIVAL_POINT_INDEX]);
        return List.of(startPoint, arrivalPoint);
    }

    private List<Integer> formatToIntegerList(String splitInput) {
        String[] split = splitInput.split(",");
        List<Integer> parsedInputs = new ArrayList<>();
        parsedInputs.add(parseRowToInt(split[0]));
        parsedInputs.add(parseColumnToInt(split[1]));
        return parsedInputs;
    }

    private static int parseColumnToInt(String split) {
        try {
            int column = Integer.parseInt(split);
            if (column < 0 || column > 9) {
                throw new IllegalArgumentException();
            }
            return column;
        } catch (IllegalArgumentException e) {
            throw new JanggiGameRuleWarningException("열 입력은 0부터 8까지의 숫자여야 합니다.");
        }
    }

    private int parseRowToInt(String split) {
        char c = split.charAt(0);
        if (c >= 'A' && c <= 'J') {
            return c - 'A';
        }
        throw new JanggiGameRuleWarningException("행 입력은 A부터 J까지의 대문자여야 합니다.");
    }

    private int readChoiceForElephantLocation() {
        System.out.println("""
                마와 상의 배치를 선택해주세요.
                1. 바깥상 차림(상마마상)
                2. 안상 차림(마상상마)
                3. 왼상 차림(상마상마)
                4. 오른상 차림(마상마상)
                """);
        return Integer.parseInt(scanner.nextLine());
    }
}
