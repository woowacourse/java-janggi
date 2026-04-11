package domain.rule;

import domain.board.Board;
import domain.player.Player;
import domain.player.PlayerProfile;

public class NormalGameWinnerRule implements WinnerDeterminationRule {

    @Override
    public boolean canApply(boolean isDraw) {
        return !isDraw;
    }

    @Override
    public PlayerProfile determineWinner(Board board, Player currentPlayer, Player standbyPlayer) {
        return currentPlayer.getProfile();
    }
}

