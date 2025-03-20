package view;

import domain.Team;
import java.util.Scanner;

public class InputView {

    private final Scanner scanner = new Scanner(System.in);

    public String readPosition(Team team) {
        System.out.println(teamToName(team) + " 이동할 장기의 위치를 선택해 주세요.");
        return scanner.nextLine();
    }

    private String teamToName(Team team) {
        if (team == Team.HAN) {
            return "한나라";
        }
        return "초나라";
    }
}
