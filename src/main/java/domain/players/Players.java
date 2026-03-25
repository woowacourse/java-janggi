package domain.players;

import domain.piece.Side;
import domain.player.Player;
import dto.BoardResponseDto;

public class Players {
    private final Player choPlayer;
    private final Player hanPlayer;

    public Players(Player choPlayer, Player hanPlayer) {
        this.choPlayer = choPlayer;
        this.hanPlayer = hanPlayer;
    }

    public void initPlacementBySide(Side side, int placementCode) {
        if (side == Side.HAN) hanPlayer.initBoard(placementCode);
        if (side == Side.CHO) choPlayer.initBoard(placementCode);
    }

    public BoardResponseDto findBoardState() {
        return hanPlayer.findBoardState();
    }
}
