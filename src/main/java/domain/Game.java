package domain;

import domain.board.TableSetting;
import dto.GameInfo;
import java.time.LocalDateTime;

public class Game {
    private static final String GAME_IS_NOT_SAVED = "[ERROR] 저장되지 않은 게임입니다.";

    private final Long gameId;
    private final LocalDateTime createdAt;
    private final TableSetting choTableSetting;
    private final TableSetting hanTableSetting;

    public Game(TableSetting choTableSetting, TableSetting hanTableSetting) {
        gameId = null;
        this.createdAt = LocalDateTime.now();
        this.choTableSetting = choTableSetting;
        this.hanTableSetting = hanTableSetting;
    }

    public Game(Long gameId, TableSetting choTableSetting, TableSetting hanTableSetting) {
        this.gameId = gameId;
        this.createdAt = LocalDateTime.now();
        this.choTableSetting = choTableSetting;
        this.hanTableSetting = hanTableSetting;
    }

    public Long getGameId() {
        if (gameId == null) {
            throw new IllegalStateException(GAME_IS_NOT_SAVED);
        }
        return gameId;
    }

    public GameInfo toSaveValues() {
        return GameInfo.of(createdAt, choTableSetting, hanTableSetting);
    }
}
