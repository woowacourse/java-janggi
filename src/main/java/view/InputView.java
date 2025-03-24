package view;

import domain.unit.Team;
import java.util.Scanner;

public class InputView {

    private final Scanner scanner = new Scanner(System.in);

    public String readUnitPosition(Team team) {
        System.out.println(teamToName(team) + ":: 이동할 장기의 위치를 선택해 주세요.");
        return scanner.nextLine();
    }

    public String readDestinationPosition(Team team) {
        System.out.println("도착할 위치를 선택해 주세요.");
        return scanner.nextLine();
    }

    private String teamToName(Team team) {
        if (team == Team.HAN) {
            return "한나라";
        }
        if (team == Team.CHO) {
            return "초나라";
        }
        throw new IllegalStateException("예기치 못한 예외가 발생하였습니다.");
    }
}
