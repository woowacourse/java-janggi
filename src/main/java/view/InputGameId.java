package view;

import core.GameSummary;
import java.util.List;

public record InputGameId(Long id) {

    public void validateWith(final List<GameSummary> gameSummaries) {
        final GameSummary game = gameSummaries.stream()
            .filter(gameSummary -> gameSummary.id().equals(id))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게임 ID입니다. 다시 입력해주세요."));

        if (game.isOver()) {
            throw new IllegalArgumentException("종료된 게임은 입장할 수 없습니다.");
        }
    }

    public boolean isNewGame() {
        return id == 0;
    }
}
