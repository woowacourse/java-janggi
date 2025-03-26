package janggi.view;

import janggi.board.Position;
import janggi.team.Team;
import janggi.team.TeamName;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Supplier;

public class Input {
    private static final String RESET = "\u001B[0m";
    private static final String SAMPLE_GREEN = "\u001B[32m";
    private static final String HAN_RED = "\u001B[31m";
    private static final String CHO_BLUE = "\u001B[34m";

    private static final String PATTERN_START_POINT = "(?i)^[KGEHPCS]-\\[[0-8],\\s?[0-9]\\]$";
    private static final String PATTERN_DESTINATION = "^\\[[0-8],\\s?[0-9]\\]$";
    private static final String PATTERN_GAME_CONTINUE = "(?i)^[YN]$";

    private static final String DELIMITER = "-";
    private static final String REGEX_PATTERN = "[\\[\\]]";
    private static final String DELIMITER_COMMA = ",";

    private static final String INVALID_PATTERN = "입력 패턴이 올바르지 않습니다.";

    private final Scanner scanner = new Scanner(System.in);

    private <T> T repeatInput(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                displayError(e.getMessage());
            }
        }
    }

    public void displayError(String message) {
        System.out.println("[ERROR] " + message);
    }

    public List<String> readPositionOption(TeamName teamName) {
        System.out.println();
        System.out.printf("%s의 상차림을 선택해주세요.%n", formatTeamColor(teamName) + teamName.getName() + RESET);
        System.out.println(" 선택 옵션 > " + SAMPLE_GREEN + "EHEH: 상마상마 | HEHE: 마상마상 | HEEH: 마상상마 | EHHE: 상마마상" + RESET);
        System.out.println(" ex) EHEH");
        return List.of(teamName.getName(), scanner.nextLine());
    }

    private String formatTeamColor(TeamName teamName) {
        if (teamName.equals(TeamName.CHO)) {
            return CHO_BLUE;
        }
        return HAN_RED;
    }

    public Map<String, Position> readPieceStartPoint(Team currentTeam) {
        System.out.println();
        TeamName currentTeamName = currentTeam.getTeamName();
        System.out.printf("%s 진영의 움직일 기물 이름과 출발 좌표를 입력해주세요.%n",
                formatTeamColor(currentTeamName) + currentTeamName.getName() + RESET);
        System.out.println(" 선택 옵션 > " + SAMPLE_GREEN + "K: 왕 | G: 사 | E: 상 | H: 마 | P: 포 | C: 차 | S: 졸병" + RESET);
        System.out.println(" ex) E-[1, 0]");
        String pieceStartPointInfo = repeatInput(() -> validatePatternStartPoint(scanner.nextLine()));

        List<String> startPointInfos = Arrays.asList(pieceStartPointInfo.split(DELIMITER));
        String pieceName = startPointInfos.getFirst();
        Position pieceStartPoint = parsePosition(startPointInfos.getLast());
        return Map.of(pieceName, pieceStartPoint);
    }

    private String validatePatternStartPoint(String pattern) {
        if (!pattern.matches(PATTERN_START_POINT)) {
            throw new IllegalArgumentException(INVALID_PATTERN);
        }
        return pattern;
    }

    public Position readPieceDestination() {
        System.out.println();
        System.out.println("선택한 기물의 도착 좌표를 입력해주세요.");
        System.out.println(" 선택 옵션 > " + SAMPLE_GREEN + "x: 0 ~ 8, y: 0 ~ 9" + RESET);
        System.out.println(" ex) [3, 3]");

        String pieceDestination = repeatInput(() -> validatePatternDestination(scanner.nextLine()));
        return parsePosition(pieceDestination);
    }

    private String validatePatternDestination(String pattern) {
        if (!pattern.matches(PATTERN_DESTINATION)) {
            throw new IllegalArgumentException(INVALID_PATTERN);
        }
        return pattern;
    }

    public String readGameContinue() {
        System.out.println();
        System.out.println("게임을 계속하시겠습니까?");
        System.out.println(" 선택 옵션 > " + SAMPLE_GREEN + "Y: 예 | N: 아니오" + RESET);
        System.out.println(" ex) Y");
        return repeatInput(() -> validatePatternGameContinue(scanner.nextLine()));
    }

    private String validatePatternGameContinue(String pattern) {
        if (!pattern.matches(PATTERN_GAME_CONTINUE)) {
            throw new IllegalArgumentException(INVALID_PATTERN);
        }
        return pattern;
    }

    private Position parsePosition(String coordinates) {
        List<Integer> pieceCoordinates = Arrays.stream(coordinates.replaceAll(REGEX_PATTERN, "").split(DELIMITER_COMMA))
                .map(String::trim)
                .map(Integer::parseInt)
                .toList();
        return new Position(pieceCoordinates.getFirst(), pieceCoordinates.getLast());
    }
}
