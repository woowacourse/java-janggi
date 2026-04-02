package janggi.entity;

public record BoardEntity(
    long id,
    String name
) {

    public static BoardEntity from(final long id, final String name) {
        return new BoardEntity(id, name);
    }

    public static BoardEntity from(final String name) {
        return new BoardEntity(0, name);
    }
}
