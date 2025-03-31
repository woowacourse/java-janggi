package janggi.database.entity;

import java.util.Objects;

public class PieceEntity {

    private final Long id;
    private final String type;
    private final String team;
    private final int x;
    private final int y;

    public PieceEntity(final Long id, final String type, final String team, final int x, final int y) {
        this.id = id;
        this.type = type;
        this.team = team;
        this.x = x;
        this.y = y;
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getTeam() {
        return team;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PieceEntity that = (PieceEntity) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
