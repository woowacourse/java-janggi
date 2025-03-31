package entity;

public class TeamEntity {
    private final long id;
    private final String name;

    public TeamEntity(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
