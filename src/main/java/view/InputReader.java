package view;

import domain.board.Formation;
import domain.piece.Team;
import domain.point.Command;

import java.util.Scanner;

public class InputReader {

    private static final String COMMAND_REQUEST_MESSAGE = "의 차례: 출발 기물의 좌표를 입력해주세요 ex 0,0 0,2";

    public final Scanner scanner;

    public InputReader() {
        this.scanner = new Scanner(System.in);
    }

    public Formation requestFormation(Team team) {
        System.out.println(team + "팀의 마,상 포메이션을 입력해주세요.");
        System.out.println("1 - 상마상마");
        System.out.println("2 - 상마마상");
        System.out.println("3 - 마상상마");
        System.out.println("4 - 마상마상");
        return Formation.from(scanner.nextLine());
    }

    public Command requestCommand(Team team) {
        System.out.println(team + COMMAND_REQUEST_MESSAGE);
        return Command.from(scanner.nextLine());
    }

}
