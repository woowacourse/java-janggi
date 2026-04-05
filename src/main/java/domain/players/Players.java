package domain.players;

import domain.piece.Side;
import domain.player.Player;

public class Players {
    private final Player choPlayer;
    private final Player hanPlayer;
    private Side currentTurn;

    public Players(Player choPlayer, Player hanPlayer) {
        this.choPlayer = choPlayer;
        this.hanPlayer = hanPlayer;
        this.currentTurn = Side.CHO;
    }

    public Side getWhoseTurn() {
        return currentTurn;
    }

    public void switchTurn() {
        currentTurn = currentTurn.next();
    }
}
