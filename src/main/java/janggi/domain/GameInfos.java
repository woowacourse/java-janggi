package janggi.domain;

import java.util.List;

public class GameInfos {
    private static final String INVALID_NUMBER = "존재하지 않는 게임 번호입니다.";

    private List<GameInfo> gameInfos;

    public GameInfos(List<GameInfo> gameInfos) {
        this.gameInfos = gameInfos;
    }

    public GameInfo getGameInfo(int index) {
        if(index < 0 || index > gameInfos.size() - 1) {
            throw new IllegalArgumentException(INVALID_NUMBER);
        }
        return gameInfos.get(index);
    }

    public boolean isEmpty() {
        return gameInfos.isEmpty();
    }

    public List<GameInfo> getGameInfos() {
        return List.copyOf(gameInfos);
    }
}
