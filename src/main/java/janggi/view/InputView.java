package janggi.view;

import janggi.piece.Team;
import java.util.Scanner;

public class InputView {
    private final Scanner scanner = new Scanner(System.in);

    public String readPosition(Team team) {
        System.out.println("현재 차례: " + teamToName(team));
        System.out.println(ConsoleColors.RESET_COLOR + "이동할 장기의 위치를 column, row 형태로 선택해 주세요. (ex. 8, 0)");
        return scanner.nextLine();
    }

    private String teamToName(Team team) {
        if (team == Team.HAN) {
            return ConsoleColors.HAN_COLOR + "한나라";
        }
        return ConsoleColors.CHO_COLOR + "초나라";
    }

    public String readRoute() {
        System.out.println("이동하고 싶은 경로의 번호를 선택해 주세요.");
        return scanner.nextLine();
    }

    public void close() {
        scanner.close();
    }
}
