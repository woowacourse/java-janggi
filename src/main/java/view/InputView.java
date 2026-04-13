package view;

import domain.Formation;
import domain.TurnAction;

import java.util.Scanner;

import static constant.ErrorMessage.*;

public class InputView {

    private final Scanner scanner = new Scanner(System.in);

    public int readOption() {
        System.out.println("""
                1. 새 게임
                2. 이어하기
                """);
        return parseOption(scanner.nextLine());
    }

    public String readGameId() {
        System.out.println("게임 ID를 입력해주세요");
        return scanner.nextLine();
    }

    public Formation readChoFormation() {
        System.out.printf(READ_FORMATION_MESSAGE, "초");
        System.out.println();
        return parseFormation(readFormationMenu());
    }

    public Formation readHanFormation() {
        System.out.printf(READ_FORMATION_MESSAGE, "한");
        System.out.println();
        return parseFormation(readFormationMenu());
    }

    public int readSourceXPosition() {
        System.out.println();
        System.out.println("움직일 기물의 x 좌표를 입력해주세요. (x 범위 1 ~ 9)");
        return parseCoordinate(scanner.nextLine());
    }

    public int readSourceYPosition() {
        System.out.println("움직일 기물의 y 좌표를 입력해주세요. (y 범위 1 ~ 10)");
        return parseCoordinate(scanner.nextLine());
    }

    public int readTargetXPosition() {
        System.out.println();
        System.out.println("목적지 x 좌표를 입력해주세요. (x 범위 1 ~ 9)");
        return parseCoordinate(scanner.nextLine());
    }

    public int readTargetYPosition() {
        System.out.println("목적지 y 좌표를 입력해주세요. (y 범위 1 ~ 10)");
        return parseCoordinate(scanner.nextLine());
    }

    public TurnAction readTurnAction() {
        System.out.println("""
                1. 기물 이동
                2. 한수쉼
                3. 점수 판정
                """);
        String input = scanner.nextLine();
        return parseTurnAction(input);
    }

    private String readFormationMenu() {
        System.out.println("""
                1. 상마마상
                2. 마상상마
                3. 상마상마
                4. 마상마상
                """);
        return scanner.nextLine();
    }

    private Formation parseFormation(String input) {
        int index = parseFormationIndex(input);
        return Formation.values()[index - 1];
    }

    private int parseFormationIndex(String input) {
        try {
            int index = Integer.parseInt(input);
            if (index < 1 || index > Formation.values().length) {
                throw new IllegalArgumentException(INVALID_FORMATION_RANGE);
            }
            return index;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INVALID_FORMATION_RANGE);
        }
    }

    private int parseCoordinate(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INVALID_NUMBER_FORMAT);
        }
    }

    private TurnAction parseTurnAction(String input) {
        int index = parseTurnActionIndex(input);
        return TurnAction.values()[index - 1];
    }

    private int parseTurnActionIndex(String input) {
        try {
            int index = Integer.parseInt(input);
            if (index < 1 || index > TurnAction.values().length) {
                throw new IllegalArgumentException(INVALID_TURN_ACTION_RANGE);
            }
            return index;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INVALID_TURN_ACTION_RANGE);
        }
    }

    private int parseOption(String input) {
        try {
            int index = Integer.parseInt(input);
            if(!(1 <= index && index <= 2)) {
                throw new IllegalArgumentException(INVALID_OPTION_RANGE);
            }
            return index;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INVALID_OPTION_RANGE);
        }
    }
}
