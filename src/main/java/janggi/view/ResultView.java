package janggi.view;

import janggi.game.Game;
import janggi.score.Score;
import janggi.score.ScoreResult;
import janggi.game.Team;
import java.util.Map.Entry;

public class ResultView {

    public void printResult(Game game, ScoreResult scoreResult) {
        Team winner = game.findWinner();

        System.out.printf("%n%s팀이 우승했습니다.%n", winner.getText());
        for (Entry<Team, Score> result : scoreResult.getResult().entrySet()) {
            System.out.printf("%s팀 : %.1f점%n", result.getKey().getText(), result.getValue().value());
        }
    }
}
