package domain.janggigame;

import domain.board.Board;
import domain.piece.Side;

public class Game {
    private final Long id;
    private final GameStatus status;
    private Side currentTurnSide;
    private final JangGunCount jangGunCount;

    public Game(Long id, GameStatus status, Side currentTurnSide, JangGunCount jangGunCount) {
        this.id = id;
        this.status = status;
        this.currentTurnSide = currentTurnSide;
        this.jangGunCount = jangGunCount;
    }

    public static Game newGame() {
        return new Game(
                null,
                GameStatus.WAITING_HAN_PLACEMENT,
                Side.CHO,
                new JangGunCount(0, 0)
        );
    }

    public void changeCurrentTurnSide() {
        if (this.currentTurnSide == Side.HAN) {
            this.currentTurnSide = Side.CHO;
            return;
        }
        this.currentTurnSide = Side.HAN;
    }

    public boolean isOver(Board board) {
        return jangGunCount.isBigJang() || board.isEmptyGeneral(currentTurnSide);
    }

    public void incrementJangGunCount(Side side){
        jangGunCount.increment(side);
    }

    public void resetJangGunCount(Side side){
        jangGunCount.reset(side);
    }

    public Long getId() {
        return id;
    }

    public GameStatus getStatus() {
        return status;
    }

    public Side getCurrentTurnSide() {
        return currentTurnSide;
    }

    public JangGunCount getJangGunCount() {
        return jangGunCount;
    }
}
