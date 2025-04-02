package janggi.view;

import janggi.domain.position.Position;
import janggi.domain.team.TeamType;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class InputView {

    private static final String LINE = System.lineSeparator();
    private static final String BLANK = " ";

    private final Scanner scanner;

    public InputView() {
        scanner = new Scanner(System.in);
    }

    public String readElephantSetting(TeamType teamType) {
        System.out.printf(LINE + "%s나라의 배치 순서를 선택해주세요." + LINE, teamType.getTitle());
        return scanner.nextLine();
    }

    public Menu readMenu() {
        System.out.println(LINE + """
                메뉴를 선택해주세요.
                1. MOVE (기물 이동)
                2. SAVE (저장 후 중단)
                3. QUIT (종료 및 결과 확인)
                """);
        return Menu.of(scanner.nextLine());
    }

    public List<Position> readMovingPosition() {
        System.out.println(LINE + """
                이동을 희망하는 기물의 현재 위치와 해당 기물이 이동할 위치를 입력해주세요.
                (세로를 10의 자리, 가로를 1의 자리로 보아 좌표를 입력해주세요. 예를 들어 초기 초나라의 궁의 좌표는 95입니다.)
                ex) 71 72
                """);

        List<Integer> points = parseInt(split(readLine()));

        return points.stream()
                .map(input -> Position.valueOf(input / 10, input % 10))
                .toList();
    }

    private List<String> split(final String input) {
        return Arrays.asList(input.split(BLANK));
    }

    private List<Integer> parseInt(final List<String> tokens) {
        try {
            List<Integer> parsedTokens = tokens.stream()
                    .map(Integer::parseInt)
                    .toList();
            validateEnterPositionCount(parsedTokens);
            return parsedTokens;
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("[ERROR] 좌표는 숫자여야 합니다.");
        }
    }

    private void validateEnterPositionCount(List<Integer> parsedTokens) {
        if (parsedTokens.size() != 2) {
            throw new IllegalArgumentException("[ERROR] 두개의 좌표를 입력해야 합니다.");
        }
    }

    private String readLine() {
        return scanner.nextLine();
    }
}
