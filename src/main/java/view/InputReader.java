package view;

import domain.piece.Team;
import domain.point.Command;

import java.util.Scanner;

public class InputReader {

    private static final String COMMAND_REQUEST_MESSAGE = "의 차례: 출발 기물의 좌표를 입력해주세요 ex 0,0 0,2";

    public final Scanner scanner;

    public InputReader() {
        this.scanner = new Scanner(System.in);
    }

    public Command requestCommand(Team team) {
        System.out.println(team + COMMAND_REQUEST_MESSAGE);
        return Command.from(scanner.nextLine());
    }

}
