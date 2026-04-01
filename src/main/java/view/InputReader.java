package view;

import domain.board.Formation;
import domain.piece.Team;
import domain.point.dto.Command;

import java.util.Scanner;

public class InputReader {

    private static final String COMMAND_REQUEST_MESSAGE = "%s의 차례: 출발 기물의 좌표를 입력해주세요 ex 0,0 0,2";
    private static final String FORMATION_REQUEST_MESSAGE = "%s팀의 마,상 포진을 입력해주세요. (1-4사이의 숫자)";
    private static final String FORMATION_INFORMATION = "1 - 상마상마\n2 - 상마마상\n3 - 마상상마\n4 - 마상마상";

    public final Scanner scanner;

    public InputReader() {
        this.scanner = new Scanner(System.in);
    }

    public Formation requestFormation(Team team) {
        System.out.println(FORMATION_INFORMATION);
        System.out.printf(FORMATION_REQUEST_MESSAGE, team);
        return Formation.from(scanner.nextLine());
    }

    public Command requestCommand(Team team) {
        System.out.printf(COMMAND_REQUEST_MESSAGE, team);
        System.out.println();
        return Command.from(scanner.nextLine());
    }

}
