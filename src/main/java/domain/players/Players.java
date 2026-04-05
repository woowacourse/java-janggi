package domain.players;

import domain.board.Board;
import domain.piece.Side;

import java.util.List;

public class Players {
    private final Player choPlayer;
    private final Player hanPlayer;
    private Side currentTurn;

    public Players() {
        this.choPlayer = new Player(Side.CHO);
        this.hanPlayer = new Player(Side.HAN);
        this.currentTurn = Side.CHO;
    }

    public Side getWhoseTurn() {
        return currentTurn;
    }

    public void switchTurn() {
        currentTurn = currentTurn.next();
    }

    public void updateState(Board board) {
        hanPlayer.updateScore(board.calculateScoreBy(Side.HAN));
        choPlayer.updateScore(board.calculateScoreBy(Side.CHO));

        if (board.isFinished()) {
            Side side = board.getWinner();
            if (side.isHan()) {
                hanPlayer.updateStatus(PlayerStatus.WIN);
                choPlayer.updateStatus(PlayerStatus.LOSS);
            }
            if (side.isCho()) {
                choPlayer.updateStatus(PlayerStatus.WIN);
                hanPlayer.updateStatus(PlayerStatus.LOSS);
            }
        }
    }

    public List<Player> getPlayers() {
        return List.of(hanPlayer, choPlayer);
    }
}
