package domain;

public record Room(int id, boolean isActive) {

    public Room(final int id) {
        this(id, true);
    }
}
