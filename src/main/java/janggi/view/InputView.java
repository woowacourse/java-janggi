package janggi.view;

import java.util.Scanner;

public class InputView {

    private final Scanner scanner = new Scanner(System.in);

    public MoveCommand inputMoveCommand() {
        System.out.println("자신의 기물을 움직이세요. (예시) 1,1 2,1");

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
}
