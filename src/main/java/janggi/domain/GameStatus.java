package janggi.domain;

import janggi.exception.business.InvalidGameStatusException;

public enum GameStatus {
    PROGRESS,
    END;

    public boolean isEnd() {
        return this == END;
    }

    public static GameStatus from(String name) {
        try {
            return GameStatus.valueOf(name.trim().toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new InvalidGameStatusException();
        }
    }
}
