package view;

import coordinate.Coordinate;
import java.util.Map;
import java.util.Scanner;
import team.Team;

public class InputView {

    private static final Map<Team, String> TEAM_NAMES = Map.of(
            Team.HAN, "한",
            Team.CHO, "초"
    );

    private static final String RED = "\u001B[31m";
    private static final String BLUE = "\u001B[34m";
    private static final String RESET = "\u001B[0m";

    private static final Map<Team, String> TEAM_COLORS = Map.of(
            Team.HAN, RED,
            Team.CHO, BLUE
    );

    private static final Scanner scanner = new Scanner(System.in);

    public int readTableSetting(Team team) {
        System.out.printf("%s 나라의 상차림을 선택해 주세요.", applyColor(TEAM_NAMES.get(team), team));
        System.out.println();
        System.out.println("1. 마상상마");
        System.out.println("2. 마상마상");
        System.out.println("3. 상마상마");
        System.out.println("4. 상마마상");
        String rawTableSetting = scanner.nextLine();
        System.out.println();

        validateInteger(rawTableSetting);
        int tableSetting = parseInteger(rawTableSetting);
        validateOption(tableSetting);
        return tableSetting;
    }

    public CoordinatesPair readMoveCoordinate(Team team) {
        System.out.printf("%s 나라의 차례입니다.", applyColor(TEAM_NAMES.get(team), team));
        System.out.println();
        System.out.println("출발 좌표를 입력해 주세요. 예) 1,5");
        Coordinate departure = parseCoordinate(scanner.nextLine());
        System.out.println("도착 좌표를 입력해 주세요. 예) 1,5");
        Coordinate arrival = parseCoordinate(scanner.nextLine());
        return new CoordinatesPair(departure, arrival);
    }

    private String applyColor(String text, Team team) {
        return TEAM_COLORS.get(team) + text + RESET;
    }

    private void validateInteger(String input) {
        if (!input.matches("\\d+")) {
            throw new IllegalArgumentException("숫자만 입력해 주세요.");
        }
    }

    private int parseInteger(String input) {
        return Integer.parseInt(input);
    }

    private void validateOption(int input) {
        if (!(input >= 1 && input <= 4)) {
            throw new IllegalArgumentException("유효한 옵션을 선택해 주세요.");
        }
    }

    private Coordinate parseCoordinate(String input) {
        String[] parts = input.split(",");
        if (parts.length != 2) {
            throw new IllegalArgumentException("잘못된 좌표 입력 형식입니다.");
        }
        return new Coordinate(Integer.parseInt(parts[0].trim()), Integer.parseInt(parts[1].trim()));
    }

    public record CoordinatesPair(Coordinate departure, Coordinate arrival) {
    }
}
