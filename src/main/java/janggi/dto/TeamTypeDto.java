package janggi.dto;

public class TeamTypeDto {
    private int id;
    private String name;
    private boolean current;

    public TeamTypeDto(int id, String name, boolean current) {
        this.id = id;
        this.name = name;
        this.current = current;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isCurrent() {
        return current;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCurrent(boolean current) {
        this.current = current;
    }
}
