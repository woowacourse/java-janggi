package database.dto;

import domain.board.JanggiBoard;
import domain.piece.Team;

public record GameResult(
        Team winner,
        double hanScore,
        double choScore
) {

    public static final double KOMI_POINT = 1.5;

    public static GameResult from(JanggiBoard janggiBoard) {
        double hanScore = janggiBoard.calculateTeamScore(Team.HAN) + KOMI_POINT;
        double choScore = janggiBoard.calculateTeamScore(Team.CHO);

        return new GameResult(janggiBoard.getWinner(), hanScore, choScore);
    }

}
