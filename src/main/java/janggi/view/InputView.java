package janggi.view;

import janggi.piece.Team;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class InputView {

    private static final String LINE = System.lineSeparator();
    private static final String BLANK = " ";
    private static final String BOARD_ORDER_TITLE = """
            장기 게임을 시작하겠습니다!  \s
             \s
            마와 상을 배치할 수 있는 경우의 수는 다음과 같습니다. \s
            1. 상 마 상 마 \s
            2. 마 상 마 상 \s
            3. 상 마 마 상 \s
            4. 마 상 상 마 \s
            """;
    private static final String ASK_BOARD_ORDER = "%s나라의 배치 순서를 선택해주세요.";
    private static final Map<Team, String> TEAM_KOREAN = Map.of(
            Team.CHO, "초",
            Team.HAN, "한"
    );

    private final Scanner scanner;

    public InputView() {
        scanner = new Scanner(System.in);
    }

    public List<Integer> readMovingPosition() {
        System.out.println(LINE + """
                이동을 희망하는 기물의 현재 위치와 해당 기물이 이동할 위치를 입력해주세요.
                (세로를 10의 자리, 가로를 1의 자리로 보아 좌표를 입력해주세요. 예를 들어 초기 초나라의 왕의 좌표는 95입니다.)
                ex) 71 72""");
        final List<String> tokens = split(readLine());
        return parseInt(tokens);
    }

    public String readChoBoardOrder() {
        System.out.println(BOARD_ORDER_TITLE);
        return readBoardOrder(Team.CHO);
    }

    public String readHanBoardOrder() {
        return readBoardOrder(Team.HAN);
    }

    private String readBoardOrder(final Team team) {
        System.out.printf(ASK_BOARD_ORDER + LINE, TEAM_KOREAN.get(team));
        return readLine();
    }

    private List<String> split(final String input) {
        return Arrays.asList(input.split(BLANK));
    }

    private List<Integer> parseInt(final List<String> tokens) {
        try {
            final List<Integer> parsedTokens = tokens.stream()
                    .map(Integer::parseInt)
                    .toList();
            if (parsedTokens.size() != 2) {
                throw new IllegalArgumentException("[ERROR] 두개의 좌표를 입력해야 합니다.");
            }
            return parsedTokens;
        } catch (final NumberFormatException exception) {
            throw new IllegalArgumentException("[ERROR] 좌표는 숫자여야 합니다.");
        }
    }

    private String readLine() {
        return scanner.nextLine();
    }
}
