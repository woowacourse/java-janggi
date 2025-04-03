package janggi;

import java.util.Objects;

public class GameId {

    private static final long UNSET = -1;

    private final long value;

    private GameId(final long value) {
        this.value = value;
    }

    public static GameId from(final long value) {
        if (value > 0) {
            return new GameId(value);
        }
        throw new IllegalArgumentException("GameId는 0보다 커야 합니다.");
    }

    public static GameId unset() {
        return new GameId(UNSET);
    }

    public long getValue() {
        if (isSet()) {
            return value;
        }
        throw new IllegalStateException("저장되지 않아, 게임 아이디가 설정되지 않았습니다.");
    }

    public GameId setValue(final long value) {
        return new GameId(value);
    }

    public boolean isSet() {
        return this.value != UNSET;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        final GameId gameId = (GameId) o;
        return value == gameId.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
