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

    public static void printStartGame() {
        System.out.println("장기 게임을 시작합니다. 초나라부터 시작하고, 왼쪽 아래를 (0,0)으로 합니다.");
    }
}
