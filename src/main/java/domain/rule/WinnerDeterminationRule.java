package domain.rule;

import domain.board.Board;
import domain.player.Player;
import domain.player.PlayerProfile;

public interface WinnerDeterminationRule {
    boolean canApply(boolean isDraw);

    PlayerProfile determineWinner(Board board, Player currentPlayer, Player standbyPlayer);
}

