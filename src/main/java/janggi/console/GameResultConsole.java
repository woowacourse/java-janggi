package janggi.console;

import janggi.game.Team;
import janggi.view.SystemView;

public final class GameResultConsole {

    private final SystemView systemView;

    public GameResultConsole(final SystemView systemView) {
        this.systemView = systemView;
    }

    public void displayGameResult(final GameStatus gameStatus) {
        if (gameStatus == GameStatus.ENDED) {
            systemView.outGame();
        }
        if (gameStatus == GameStatus.CHO_WIN) {
            systemView.win(Team.CHO);
        }
        if (gameStatus == GameStatus.CHO_WIN) {
            systemView.win(Team.HAN);
        }
    }
}
