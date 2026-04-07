package database.dto;

import domain.board.JanggiBoard;
import domain.piece.Team;

public record GameResult(
        Team winner,
        double hanScore,
        double choScore
) {

    public static GameResult from(JanggiBoard janggiBoard) {
        double hanScore = janggiBoard.calculateTeamScore(Team.HAN) + 1.5;
        double choScore = janggiBoard.calculateTeamScore(Team.CHO);

        if (janggiBoard.isGeneralDead()) {
            Team winner = janggiBoard.getWinner();
            return new GameResult(winner, hanScore, choScore);
        }

        Team winner = hanScore > choScore ? Team.HAN : Team.CHO;
        return new GameResult(winner, hanScore, choScore);
    }

}
