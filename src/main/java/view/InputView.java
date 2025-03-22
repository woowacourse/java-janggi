package view;

import coordinate.Coordinate;
import java.util.Scanner;
import team.Team;

public class InputView {

    private static final Scanner scanner = new Scanner(System.in);

    public int readTableSetting(Team team) {
        printf("%s 나라의 상차림을 선택해 주세요.", team.applyColorTeamName());
        printNewLine();
        println("1. 마상상마");
        println("2. 마상마상");
        println("3. 상마상마");
        println("4. 상마마상");
        String rawTableSetting = scanner.nextLine();
        printNewLine();

        validateOption(rawTableSetting);
        return parseInteger(rawTableSetting);
    }

    public CoordinatesPair readMoveCoordinate(Team team) {
        printf("%s 나라의 차례입니다.", team.applyColorTeamName());
        printNewLine();

        println("출발 좌표를 입력해 주세요. 예) 1,4");
        String rawDeparture = scanner.nextLine();
        validateCoordinate(rawDeparture);
        Coordinate departure = parseCoordinate(rawDeparture);

        println("도착 좌표를 입력해 주세요. 예) 1,5");
        String rawArrival = scanner.nextLine();
        validateCoordinate(rawArrival);
        Coordinate arrival = parseCoordinate(rawArrival);

        return new CoordinatesPair(departure, arrival);
    }

    private void validateOption(String input) {
        if (!input.matches("[1-4]")) {
            throw new IllegalArgumentException("유효하지 않은 옵션입니다.");
        }
    }

    private int parseInteger(String input) {
        return Integer.parseInt(input);
    }

    private void validateCoordinate(String input) {
        if (!input.matches("\\d{1,2},\\d{1,2}")) {
            throw new IllegalArgumentException("유효하지 않은 좌표 형식입니다.");
        }
    }

    private Coordinate parseCoordinate(String input) {
        String[] parts = input.split(",");
        int x = Integer.parseInt(parts[0].trim());
        int y = Integer.parseInt(parts[1].trim());
        return new Coordinate(x, y);
    }

    public record CoordinatesPair(Coordinate departure, Coordinate arrival) {
    }

    private void printf(String format, Object... args) {
        System.out.printf(format, args);
    }

    private void println(String text) {
        System.out.println(text);
    }

    private void printNewLine() {
        System.out.println();
    }
}
