package janggi.domain.game;

import janggi.domain.dynasty.Dynasty;
import java.util.Arrays;

public enum GameState {
    PLAYING(null),
    HAN_WIN(Dynasty.HAN),
    CHO_WIN(Dynasty.CHO);

    private final Dynasty winner;

    GameState(Dynasty winner) {
        this.winner = winner;
    }

    public static GameState from(Dynasty dynasty) {
        return Arrays.stream(values())
                .filter(state -> dynasty.equals(state.winner))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 진영의 게임 상태는 존재하지 않습니다."));
    }

    public Dynasty winner() {
        if (winner == null) {
            throw new IllegalStateException("진행 중 상태에는 승자가 없습니다.");
        }
        return winner;
    }

    public boolean isFinished() {
        return this != PLAYING;
    }

}
