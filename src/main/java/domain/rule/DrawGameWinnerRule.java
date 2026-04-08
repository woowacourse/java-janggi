package domain.rule;

import domain.board.Board;
import domain.player.Player;
import domain.player.PlayerProfile;
import domain.player.Team;

public class DrawGameWinnerRule implements WinnerDeterminationRule {

    private static final double HAN_SCORE_INCREASE = 1.5;

    @Override
    public boolean canApply(boolean isDraw) {
        return isDraw;
    }

    @Override
    public PlayerProfile determineWinner(Board board, Player currentPlayer, Player standbyPlayer) {
        double choScore = board.calculateRawScore(Team.CHO);
        double hanScore = board.calculateRawScore(Team.HAN) + HAN_SCORE_INCREASE;

        if (choScore > hanScore) {
            return findPlayerProfileByTeam(Team.CHO, currentPlayer, standbyPlayer);
        }
        return findPlayerProfileByTeam(Team.HAN, currentPlayer, standbyPlayer);
    }

    private PlayerProfile findPlayerProfileByTeam(Team team, Player currentPlayer, Player standbyPlayer) {
        if (currentPlayer.getProfile().team() == team) {
            return currentPlayer.getProfile();
        }
        return standbyPlayer.getProfile();
    }
}

