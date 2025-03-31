package entity;

public class PieceEntity {
    private long id;
    private long teamId;
    private String type;

    public PieceEntity(long id, long teamId, String type) {
        this.id = id;
        this.teamId = teamId;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public long getTeamId() {
        return teamId;
    }

    public String getType() {
        return type;
    }
}
