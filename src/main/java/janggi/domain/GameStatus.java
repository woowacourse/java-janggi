package janggi.domain;

import static janggi.domain.Team.BLUE;
import static janggi.domain.Team.RED;

public enum GameStatus {
    PROGRESS,
    RED_WIN,
    BLUE_WIN,
    OVER;

    public static GameStatus checkStatus(final Pieces pieces) {
        if (pieces.isGeneralDead(RED) || pieces.isGeneralDead(BLUE) || pieces.isAliveOnlyGeneralEachTeam()) {
            return chooseResult(pieces);
        }
        return PROGRESS;
    }

    private static GameStatus chooseResult(final Pieces pieces) {
        if (pieces.isGeneralDead(RED)) {
            return BLUE_WIN;
        }
        if (pieces.isGeneralDead(BLUE)) {
            return RED_WIN;
        }
        return OVER;
    }
}
