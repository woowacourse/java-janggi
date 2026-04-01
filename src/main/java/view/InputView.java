package view;

import domain.Formation;
import java.util.Scanner;

public class InputView {

    private final String READ_FORMATION_MESSAGE = "[%s] 포진을 선택해주세요.";

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
        System.out.println("움직일 기물의 x 좌표를 입력해주세요. (x 범위 1 ~ 9)");
        return scanner.nextLine();
    }

    public String readSourceYPosition() {
        System.out.println("움직일 기물의 y 좌표를 입력해주세요. (y 범위 1 ~ 10)");
        return scanner.nextLine();
    }

    public String readTargetXPosition() {
        System.out.println();
        System.out.println("목적지 x 좌표를 입력해주세요. (x 범위 1 ~ 9)");
        return scanner.nextLine();
    }

    public String readTargetYPosition() {
        System.out.println("목적지 y 좌표를 입력해주세요. (y 범위 1 ~ 10)");
        return scanner.nextLine();
    }

    private String readFormation() {
        for (Formation formation : Formation.values()) {
            System.out.println(formation.toDisplayString());
        }
        return scanner.nextLine();
    }
}
