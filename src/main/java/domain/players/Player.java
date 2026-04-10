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

    public Player(Side side, double score, PlayerStatus playerStatus) {
        this.side = side;
        this.score = score;
        this.playerStatus = playerStatus;
    }

    public static Player of(Side side, double score, PlayerStatus playerStatus) {
        return new Player(side, score, playerStatus);
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
