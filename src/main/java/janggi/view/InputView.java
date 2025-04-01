package janggi.view;

import janggi.domain.Coordinate;
import janggi.domain.Team;
import java.util.Map;
import java.util.Scanner;

public class InputView {

    private static final Map<Team, String> TEAM_NAMES = Map.of(
        Team.HAN, "한",
        Team.CHO, "초"
    );

    private static final Map<Team, String> TEAM_COLORS = Map.of(
            Team.HAN, ViewTools.RED,
            Team.CHO, ViewTools.BLUE
    );

    private static final Scanner scanner = new Scanner(System.in);

    public UserInput readMoveOrder(Team team) {
        System.out.printf("%s 나라의 차례입니다.", ViewTools.applyColor(TEAM_COLORS.get(team), TEAM_NAMES.get(team)));
        System.out.println();

        System.out.println("출발 좌표를 입력해 주세요. 예) 1,5");
        System.out.println("만약 저장하고 종료하고 싶다면, 'Q'를 입력해 주세요.");
        String input = scanner.nextLine();
        if ("Q".equalsIgnoreCase(input)) {
            return new UserInput(true);
        }
        Coordinate departure = parseCoordinate(input);

        System.out.println("도착 좌표를 입력해 주세요. 예) 1,5");
        Coordinate arrival = parseCoordinate(scanner.nextLine());
        return new UserInput(departure, arrival);
    }

    private Coordinate parseCoordinate(String input) {
        String[] parts = input.split(",");
        if (parts.length != 2) {
            throw new IllegalArgumentException("잘못된 좌표 입력 형식입니다.");
        }
        return new Coordinate(Integer.parseInt(parts[0].trim()), Integer.parseInt(parts[1].trim()));
    }

    public static class UserInput {
        public final boolean wantsToQuit;
        public Coordinate departure;
        public Coordinate arrival;

        UserInput(final boolean wantsToQuit) {
            this.wantsToQuit = wantsToQuit;
        }

        UserInput(final Coordinate departure, final Coordinate arrival) {
            this.wantsToQuit = false;
            this.departure = departure;
            this.arrival = arrival;
        }
    }
}
