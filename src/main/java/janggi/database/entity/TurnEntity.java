package janggi.database.entity;

import java.util.Objects;

public class TurnEntity {

    private final Long id;
    private final String team;

    public TurnEntity(final Long id, final String team) {
        this.id = id;
        this.team = team;
    }

    public Long getId() {
        return id;
    }

    public String getTeam() {
        return team;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TurnEntity that = (TurnEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
