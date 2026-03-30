package domain.players;

import domain.board.Board;
import domain.piece.Side;
import domain.player.Player;
import domain.position.Move;

public class Players {
    private final Player choPlayer;
    private final Player hanPlayer;
    private Side currentTurn;

    public Players(Player choPlayer, Player hanPlayer) {
        this.choPlayer = choPlayer;
        this.hanPlayer = hanPlayer;
        this.currentTurn = Side.CHO;
    }

    public void initPlacementBySide(Side side, int placementCode, Board board) {
        if (side == Side.HAN) hanPlayer.initBoard(board, placementCode);
        if (side == Side.CHO) choPlayer.initBoard(board, placementCode);
    }

    public void playTurn(Board board, Move move) {
        Player currentPlayer = getCurrentPlayer();
        currentPlayer.play(board, move);
        currentTurn = currentTurn.next();
    }

    private Player getCurrentPlayer() {
        if (currentTurn == Side.CHO) return choPlayer;
        return hanPlayer;
    }
}
