package janggi.rule;

public record MoveVector(int dy, int dx) {

    public MoveVector add(final MoveVector moveVector) {
        return new MoveVector(dy + moveVector.dy(), dx + moveVector.dx());
    }
}
