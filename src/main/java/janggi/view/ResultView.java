package janggi.view;

import janggi.game.Game;
import janggi.game.Team;

public class ResultView {

    public void printResult(Game game) {
        Team winner = game.findWinner();

        System.out.printf("%n%s팀이 우승했습니다.%n", winner.getText());
    }
}
