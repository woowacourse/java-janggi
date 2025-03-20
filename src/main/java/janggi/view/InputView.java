package janggi.view;

import janggi.piece.Team;
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

    public List<Integer> readMovingPosition(Team team) {
        System.out.printf(LINE + "%s나라의 순서입니다." + LINE + LINE, team.getTitle());
        System.out.println("""
                이동을 희망하는 기물의 현재 위치와 해당 기물이 이동할 위치를 입력해주세요.
                (세로를 10의 자리, 가로를 1의 자리로 보아 좌표를 입력해주세요. 예를 들어 초기 초나라의 왕의 좌표는 95입니다.)
                ex) 71 72
                """);
        List<String> tokens = split(readLine());
        return parseInt(tokens);
    }

    private List<String> split(final String input) {
        return Arrays.asList(input.split(BLANK));
    }

    private List<Integer> parseInt(final List<String> tokens) {
        try {
            List<Integer> parsedTokens = tokens.stream()
                    .map(Integer::parseInt)
                    .toList();
            if (parsedTokens.size() != 2) {
                throw new IllegalArgumentException("[ERROR] 두개의 좌표를 입력해야 합니다.");
            }
            return parsedTokens;
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("[ERROR] 좌표는 숫자여야 합니다.");
        }
    }

    private String readLine() {
        return scanner.nextLine();
    }
}
