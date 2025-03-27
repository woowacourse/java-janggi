package janggi.view;

import janggi.domain.GameStatus;
import java.util.Arrays;

public enum GameStatusText {
    CONTINUE(GameStatus.CONTINUE, ""),
    RED_WIN(GameStatus.RED_WIN, "한 팀이 승리하였습니다!"),
    GREEN_WIN(GameStatus.GREEN_WIN, "초 팀이 승리하였습니다!"),
    DRAW(GameStatus.DRAW, "무승부 입니다!"),
    ;

    private final GameStatus gameStatus;
    private final String text;

    GameStatusText(final GameStatus gameStatus, final String text) {
        this.gameStatus = gameStatus;
        this.text = text;
    }

    public static String getText(GameStatus gameStatus) {
        return Arrays.stream(GameStatusText.values())
                .filter(gameStatusText -> gameStatusText.gameStatus == gameStatus)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("게임 상태 메시지 탐색 실패: " + gameStatus))
                .text;
    }
}
