package domain.players;

import domain.board.Placement;
import domain.piece.Side;
import domain.player.Player;

public class Players {
    private final Player choPlayer;
    private final Player hanPlayer;

    public Players(Player choPlayer, Player hanPlayer) {
        this.choPlayer = choPlayer;
        this.hanPlayer = hanPlayer;
    }

    public void initPlacementBySide(Side side, int placementCode) {
        // if side placement 받아서 side가 한이면
        if (side == Side.HAN) {
            hanPlayer.initBoard(placementCode);
        }
        if (side == Side.CHO) {
            choPlayer.initBoard(placementCode);
        }
    }
}
