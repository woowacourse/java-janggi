package entity;

public class PlayerEntity {
    private long id;
    private long teamId;

    public PlayerEntity(long id, long teamId) {
        this.id = id;
        this.teamId = teamId;
    }

    public long getId() {
        return id;
    }

    public long getTeamId() {
        return teamId;
    }
}
