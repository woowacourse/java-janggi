package domain;

public enum CommandOption {

    MOVE("기물 이동"),
    UNDO("무르기");

    private final String description;

    CommandOption(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
