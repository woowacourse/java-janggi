package janggi.rule;

public record Vector(int dy, int dx) {

    public Vector add(final Vector moveVector) {
        return new Vector(dy + moveVector.dy(), dx + moveVector.dx());
    }
}
