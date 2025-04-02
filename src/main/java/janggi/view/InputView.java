package janggi.view;

import janggi.Team;

import janggi.board.position.Position;
import java.util.Scanner;

public class InputView {
    private final InputParser inputParser = new InputParser();
    private final Scanner scanner = new Scanner(System.in);

    public StartAndGoalPosition readStartAndGoalPosition(Team team) {
        System.out.printf("\n%s나라의 공격 차례입니다.\n", team.getDisplayName());
        String startAndGoalPosition = scanner.nextLine();
        Position startPosition = inputParser.splitStartPosition(startAndGoalPosition);
        Position goalPosition = inputParser.splitGoalPosition(startAndGoalPosition);
        return new StartAndGoalPosition(startPosition, goalPosition);
    }
}
