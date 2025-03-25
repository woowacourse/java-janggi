package janggi.view;

import janggi.team.TeamName;
import java.util.Arrays;
import java.util.List;
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

    public String readPositionOption(TeamName teamName) {
        System.out.println();
        System.out.printf("%s의 상차림을 선택해주세요.%n", formatTeamColor(teamName) + teamName.getName() + RESET);
        System.out.println(" 선택 옵션 > " + SAMPLE_GREEN + "EHEH: 상마상마 | HEHE: 마상마상 | HEEH: 마상상마 | EHHE: 상마마상" + RESET);
        System.out.println(" ex) EHEH");
        return scanner.nextLine();
    }

    private String formatTeamColor(TeamName teamName) {
        if (teamName.equals(TeamName.CHO)) {
            return CHO_BLUE;
        }
        return HAN_RED;
    }

    public String readPieceTeamName() {
        System.out.println();
        System.out.println("움직임 팀 이름을 입력해주세요.");
        System.out.println(" 선택 옵션 > " + SAMPLE_GREEN + "CHO: 초 | HAN: 한" + RESET);
        System.out.println(" ex) " + CHO_BLUE + "CHO" + RESET);
        return scanner.nextLine();
    }

    public List<String> readPieceStartPoint() {
        System.out.println();
        System.out.println("움직일 기물 이름과 출발 좌표를 입력해주세요.");
        System.out.println(" 선택 옵션 > " + SAMPLE_GREEN + "K: 왕 | G: 사 | E: 상 | H: 마 | P: 포 | C: 차 | S: 졸병" + RESET);
        System.out.println(" ex) E-[1, 0]");
        String pieceStartPointInfo = repeatInput(() -> validatePatternStartPoint(scanner.nextLine()));
        return Arrays.asList(pieceStartPointInfo.split(DELIMITER));
    }

    private String validatePatternStartPoint(String pattern) {
        if (!pattern.matches(PATTERN_START_POINT)) {
            throw new IllegalArgumentException(INVALID_PATTERN);
        }
        return pattern;
    }

    public String readPieceDestination() {
        System.out.println();
        System.out.println("선택한 기물의 도착 좌표를 입력해주세요.");
        System.out.println(" 선택 옵션 > " + SAMPLE_GREEN + "x: 0 ~ 8, y: 0 ~ 9" + RESET);
        System.out.println(" ex) [3, 3]"); //도착 좌표가 도달할 수 있는 곳인지 검증 필요
        return repeatInput(() -> validatePatternDestination(scanner.nextLine()));
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
}
