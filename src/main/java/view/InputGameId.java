package view;

import core.GameSummary;
import java.util.List;

public record InputGameId(Long id) {

    public void validateWith(List<GameSummary> gameSummaries) {
        GameSummary game = gameSummaries.stream()
            .filter(gameSummary -> gameSummary.id().equals(id))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("게임 ID를 잘못 입력하셨습니다."));

        if (game.isOver()) {
            throw new IllegalArgumentException("종료된 게임은 입장할 수 없습니다.");
        }
    }

    public boolean isNewGame() {
        return id == 0;
    }
}
