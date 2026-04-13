package dto;

import domain.board.TableSetting;
import java.time.LocalDateTime;

public record GameInfo(
        LocalDateTime createdAt,
        TableSetting choTableSetting,
        TableSetting hanTableSetting
) {
    public static GameInfo of(LocalDateTime createdAt, TableSetting choTableSetting, TableSetting hanTableSetting) {
        return new GameInfo(createdAt, choTableSetting, hanTableSetting);
    }
}
