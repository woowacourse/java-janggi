package view;

import core.GameSummary;
import java.util.List;

public final class SelectedGame {

    private final Long id;
    private final GameSummary gameSummary;

    private SelectedGame(Long id, GameSummary gameSummary) {
        this.id = id;
        this.gameSummary = gameSummary;
    }

    public static SelectedGame of(Long id, List<GameSummary> gameSummaries) {
        GameSummary gameSummary = findGameSummary(id, gameSummaries);
        return new SelectedGame(id, gameSummary);
    }

    private static GameSummary findGameSummary(Long id, List<GameSummary> gameSummaries) {
        return gameSummaries.stream()
            .filter(gameSummary -> gameSummary.id().equals(id))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게임 ID입니다. 다시 입력해주세요."));
    }

    public boolean isOver() {
        return gameSummary.isOver();
    }

    public boolean isNewGame() {
        return id == 0;
    }

    public Long id() {
        return id;
    }
}
