package janggi.presentation.ui;

import janggi.domain.status.Team;
import janggi.presentation.dto.GameStatusInfo;
import java.util.Map;

public class OutputView {

    private static final Map<Team, String> DISPLAY_NAME = Map.of(
            Team.HAN, "한",
            Team.CHO, "초"
    );

    public static void printWinner(Team winner) {
        System.out.println("승자는 " + DISPLAY_NAME.get(winner));
    }

    public static void printGameStatus(GameStatusInfo status) {
        status.pieces()
                .forEach(System.out::println);
    }

    public static void printCurrentScore(double hanScore, double choScore) {
        System.out.printf("한 : %.1f, 초 : %.1f\n", hanScore, choScore);
    }

    public static void printStartGame() {
        System.out.println("장기 게임을 시작합니다. 초나라부터 시작하고, 왼쪽 아래를 (0,0)으로 합니다.");
    }

    public static void printError(String message) {
        System.out.println(message);
    }
}
