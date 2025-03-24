package janggi.view;

import janggi.Player;
import janggi.Team;

import java.util.Scanner;

public class InputView {

    private final Scanner scanner = new Scanner(System.in);

    public MoveCommand inputMoveCommand(final Player player) {
        System.out.println(processColor(player.getTeam(), player.getTeam().getDescription() + "나라") + ", " + "자신의 기물을 움직이세요. (예시) 1,1 2,1");

        String move = scanner.nextLine();

        String[] departureAndDestination = move.trim().split(" ");
        String[] departurePosition = departureAndDestination[0].split(",");
        String[] destinationPosition = departureAndDestination[1].split(",");

        return MoveCommand.of(
                departurePosition[0],
                departurePosition[1],
                destinationPosition[0],
                destinationPosition[1]);
    }

    private String processColor(final Team team, final String string) {
        if (team.isCho()) {
            return processGreenColorString(string);
        }
        return processRedColorString(string);
    }

    private String processRedColorString(final String string) {
        return "\u001B[31m" + string + "\u001B[0m";
    }

    private String processGreenColorString(final String string) {
        return "\u001B[32m" + string + "\u001B[0m";
    }
}
