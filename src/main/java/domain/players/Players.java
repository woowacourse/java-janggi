package domain.players;

import domain.piece.Piece;
import domain.piece.Side;
import domain.player.Player;
import domain.position.Position;

import java.util.Map;

public class Players {
    private final Player choPlayer;
    private final Player hanPlayer;

    public Players(Player choPlayer, Player hanPlayer) {
        this.choPlayer = choPlayer;
        this.hanPlayer = hanPlayer;
    }

    public void initPlacementBySide(Side side, int placementCode) {
        if (side == Side.HAN) {
            hanPlayer.initBoard(placementCode);
        }
        if (side == Side.CHO) {
            choPlayer.initBoard(placementCode);
        } ;
    }

    public Map<Position, Piece> findBoardState() {
        return hanPlayer.findBoardState();
    }

    public void move(Position from, Position to, Side attackSide) {
        if (attackSide == Side.HAN) {
            hanPlayer.move(from, to);
        }
        if (attackSide == Side.CHO) {
            choPlayer.move(from, to);
        }
    }
}
