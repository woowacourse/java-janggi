package janggi.ui;

import janggi.domain.status.Team;

public class OutputView {

    public static void printWinner(Team winner) {
        System.out.println("승자는 " + winner.getName());
    }
}
