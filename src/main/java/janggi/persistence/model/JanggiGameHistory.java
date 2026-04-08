package janggi.persistence.model;

import janggi.persistence.GameStatus;
import janggi.persistence.dto.MoveHistory;
import java.util.List;

public class JanggiGameHistory {

    private final long gameId;
    private final List<MoveHistory> moveHistories;
    private final GameStatus gameStatus;

    public JanggiGameHistory(long gameId, List<MoveHistory> moveHistories, GameStatus gameStatus) {
        this.gameId = gameId;
        this.moveHistories = moveHistories;
        this.gameStatus = gameStatus;
    }

    public static JanggiGameHistory createEmpty() {
        return new JanggiGameHistory(0L, List.of(), GameStatus.BEFORE_START);
    }

    public long getGameId() {
        return gameId;
    }

    public List<MoveHistory> getMoveHistories() {
        return moveHistories;
    }

    public boolean isBeforeStart() {
        return gameStatus == GameStatus.BEFORE_START;
    }
}
