package domain.players;

import domain.piece.Side;

public class Player {
    private final Side side;
    private double score;
    private PlayerStatus playerStatus;

    public Player(Side side) {
        this.side = side;
        if (side.isCho()) this.score = 72;
        if (side.isHan()) this.score = 73.5;
        this.playerStatus = PlayerStatus.RUNNING;
    }

    public Side getSide() {
        return side;
    }

    public void updateScore(double score) {
        this.score = score;
    }

    public void updateStatus(PlayerStatus playerStatus) {
        this.playerStatus = playerStatus;
    }

    public double getScore() {
        return score;
    }

    public PlayerStatus getPlayerStatus() {
        return playerStatus;
    }
}
