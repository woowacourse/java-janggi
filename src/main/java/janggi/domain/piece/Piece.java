package janggi.domain.piece;

public abstract class Piece {
    protected final TeamType teamType;
    private final Position position;
    private final String name;

    public Piece(final String name, final Position position, final TeamType teamType) {
        this.name = name;
        this.position = position;
        this.teamType = teamType;
    }

    public Position getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

    public TeamType getTeamType() {
        return teamType;
    }

    public static Piece createEmpty() {
        return new None();
    }
}
