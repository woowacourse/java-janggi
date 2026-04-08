package view;

import constant.BoardSpec;
import domain.Formation;
import java.util.Scanner;

public class InputView {

    private static final String READ_FORMATION_MESSAGE = "[%s] 포진을 선택해주세요.";
    private static final String READ_SOURCE_X_POSITION = String.format("움직일 기물의 x 좌표를 입력해주세요. (x 범위 %d ~ %d)",
        BoardSpec.MIN_X, BoardSpec.MAX_X);
    private static final String READ_SOURCE_Y_POSITION = String.format("움직일 기물의 y 좌표를 입력해주세요. (y 범위 %d~ %d)",
        BoardSpec.MIN_Y, BoardSpec.MAX_Y);
    private static final String READ_TARGET_X_POSITION = String.format("목적지의 x 좌표를 입력해주세요. (x 범위 %d ~ %d)",
        BoardSpec.MIN_X, BoardSpec.MAX_X);
    private static final String READ_TARGET_Y_POSITION = String.format("목적지의 y 좌표를 입력해주세요. (y 범위 %d~ %d)",
        BoardSpec.MIN_Y, BoardSpec.MAX_Y);
    private static final String READ_LOAD_GAME = "진행중인 게임이 있습니다. 이어서 하시겠습니까? (y/n)";

    private final Scanner scanner = new Scanner(System.in);

    public String readChoFormation() {
        System.out.printf(READ_FORMATION_MESSAGE, "초");
        System.out.println();
        return readFormation();
    }

    public String readHanFormation() {
        System.out.printf(READ_FORMATION_MESSAGE, "한");
        System.out.println();
        return readFormation();
    }

    public String readSourceXPosition() {
        System.out.println();
        System.out.println(READ_SOURCE_X_POSITION);
        return scanner.nextLine();
    }

    public String readSourceYPosition() {
        System.out.println(READ_SOURCE_Y_POSITION);
        return scanner.nextLine();
    }

    public String readTargetXPosition() {
        System.out.println();
        System.out.println(READ_TARGET_X_POSITION);
        return scanner.nextLine();
    }

    public String readTargetYPosition() {
        System.out.println(READ_TARGET_Y_POSITION);
        return scanner.nextLine();
    }

    public String readLoadGame() {
        System.out.println(READ_LOAD_GAME);
        return scanner.nextLine();
    }

    private String readFormation() {
        for (Formation formation : Formation.values()) {
            System.out.println(formation.toDisplayString());
        }
        return scanner.nextLine();
    }
}
