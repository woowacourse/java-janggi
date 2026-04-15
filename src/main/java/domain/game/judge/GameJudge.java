package domain.game.judge;

import domain.board.Board;
import domain.game.GameResult;
import domain.game.Team;

public final class GameJudge {
    private final ScoringRule scoringRule;
    private final CaptureVictoryRule captureVictoryRule;

    public GameJudge(ScoringRule scoringRule, CaptureVictoryRule captureVictoryRule) {
        this.scoringRule = scoringRule;
        this.captureVictoryRule = captureVictoryRule;
    }

    public static GameJudge defaultJudge() {
        return new GameJudge(new HansuBonusScoringRule(), new GeneralCaptureVictoryRule());
    }

    public GameResult decide(Board board) {
        double choScore = scoringRule.score(board, Team.CHO);
        double hanScore = scoringRule.score(board, Team.HAN);
        Team winner = captureVictoryRule.findWinner(board)
                .orElseGet(() -> compareScores(choScore, hanScore));
        return new GameResult(winner, choScore, hanScore);
    }

    public double scoreOf(Board board, Team team) {
        return scoringRule.score(board, team);
    }

    private Team compareScores(double choScore, double hanScore) {
        int cmp = Double.compare(choScore, hanScore);
        if (cmp > 0) {
            return Team.CHO;
        }
        if (cmp < 0) {
            return Team.HAN;
        }
        return Team.NONE;
    }
}
