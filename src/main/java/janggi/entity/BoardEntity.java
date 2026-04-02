package janggi.entity;

public record BoardEntity(
    long id,
    long game_state_id,
    String name
) {

    public static BoardEntity from(final long id, final long gameStateId, final String name) {
        return new BoardEntity(id, gameStateId, name);
    }

    public static BoardEntity from(final long gameStateId, final String name) {
        return new BoardEntity(0, gameStateId, name);
    }
}
