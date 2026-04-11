package model.board;

import model.game.Team;

public record ScoreResult(double hanScore, double choScore) {

    public Team winner() {
        if (choScore >= hanScore) {
            return Team.CHO;
        }
        return Team.HAN;
    }
}
