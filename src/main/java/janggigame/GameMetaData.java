package janggigame;

import domain.board.Board;
import domain.piece.Side;

public class GameMetaData {
    private final Long id;
    private JanggiGameStatus status;
    private Side currentTurnSide;
    private JangGunCount jangGunCount;

    public GameMetaData(Long id, JanggiGameStatus status, Side currentTurnSide, JangGunCount jangGunCount) {
        this.id = id;
        this.status = status;
        this.currentTurnSide = currentTurnSide;
        this.jangGunCount = jangGunCount;
    }

    public static GameMetaData newGame() {
        return new GameMetaData(
                null,
                JanggiGameStatus.WAITING_HAN_PLACEMENT,
                Side.CHO,
                new JangGunCount(0, 0)
        );
    }

    public void changeCurrentTurn(Side side) {
        if (side == Side.HAN) {
            currentTurnSide = Side.CHO;
        }
        if (side == Side.CHO) {
            currentTurnSide = Side.HAN;
        }
    }

    public boolean isGameOver(Board board) {
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

    public JanggiGameStatus getStatus() {
        return status;
    }

    public Side getCurrentTurnSide() {
        return currentTurnSide;
    }

    public JangGunCount getJangGunCount() {
        return jangGunCount;
    }
}
