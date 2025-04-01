package janggi.view;

import janggi.domain.Team;
import java.util.Map;
import java.util.Scanner;

public class BoardInitiliazeView {

    private static final Map<Team, String> TEAM_NAMES = Map.of(
        Team.HAN, "한",
        Team.CHO, "초"
    );

    private static final Map<Team, String> TEAM_COLORS = Map.of(
        Team.HAN, ViewTools.RED,
        Team.CHO, ViewTools.BLUE
    );

    private static final Scanner scanner = new Scanner(System.in);

    public void printContinueGame() {
        System.out.println("------------------------------");
        System.out.println("진행중인 게임이 있어 이어서 진행합니다.");
        System.out.println("------------------------------");
    }

    public boolean readRenewGame() {
        System.out.println("진행중인 게임이 존재합니다. 이어서 할까요? (Y / N)");
        String input = scanner.nextLine();
        if ("Y".equalsIgnoreCase(input)) {
            return true;
        }
        if ("N".equalsIgnoreCase(input)) {
            return false;
        }
        throw new IllegalArgumentException("잘못 입력하셨습니다.");
    }

    public int readTableSetting(Team team) {
        System.out.printf("%s 나라의 상차림을 선택해 주세요.",
            ViewTools.applyColor(TEAM_COLORS.get(team), TEAM_NAMES.get(team)));
        System.out.println();
        System.out.println("1. 마상상마");
        System.out.println("2. 마상마상");
        System.out.println("3. 상마상마");
        System.out.println("4. 상마마상");
        String rawTableSetting = scanner.nextLine();
        System.out.println();

        int tableSetting = parseInteger(rawTableSetting);
        validateOption(tableSetting);
        return tableSetting;
    }

    private int parseInteger(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자만 입력해 주세요.");
        }
    }

    private void validateOption(int input) {
        if (!(input >= 1 && input <= 4)) {
            throw new IllegalArgumentException("유효한 옵션을 선택해 주세요.");
        }
    }
}
