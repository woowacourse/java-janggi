package janggi.view;

import janggi.Team;

import java.util.Scanner;

public class InputView {
    private final Scanner scanner = new Scanner(System.in);

    public String readStartAndGoalPosition(Team team) {
        System.out.printf("\n%s나라의 공격 차례입니다.\n", team.getName());
        return scanner.nextLine();
    }
}
