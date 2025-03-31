package infra.entity;

import java.util.Objects;

public class TurnEntity {

    private final Long id;
    private final String team;

    public TurnEntity(
        final String team
    ) {
        this(null, team);
    }

    public TurnEntity(
        final Long id,
        final String team
    ) {
        validateNotBlank(team);
        this.id = id;
        this.team = team;
    }

    private void validateNotBlank(final String team) {
        if (team == null || team.isBlank()) {
            throw new IllegalArgumentException("TurnEntity의 team은 null이거나 공백일 수 없습니다.");
        }
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
