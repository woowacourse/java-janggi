package domain;

public record GameId(Long value) {
    public static final GameId UNASSIGNED = new GameId(null);
}
