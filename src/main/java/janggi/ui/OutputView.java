package janggi.ui;

import janggi.domain.status.Team;
import janggi.dto.GameStatusInfo;

public class OutputView {

    public static void printWinner(Team winner) {
        System.out.println("승자는 " + winner.getName());
    }

    public static void printGameStatus(GameStatusInfo status) {
        status.pieces()
                .forEach(System.out::println);
    }
}
