package domain;

import domain.board.TableSetting;
import dto.GameInfo;
import java.time.LocalDateTime;

public class Game {
    private Long gameId;
    private final LocalDateTime createdAt;
    private final TableSetting choTableSetting;
    private final TableSetting hanTableSetting;

    public Game(TableSetting choTableSetting, TableSetting hanTableSetting) {
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
        return gameId;
    }

    public GameInfo toSaveValues() {
        return GameInfo.of(createdAt, choTableSetting, hanTableSetting);
    }
}
