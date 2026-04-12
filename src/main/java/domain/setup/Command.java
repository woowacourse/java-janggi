package domain.setup;

public class Command {
    private final String value;

    public Command(String input) {
        this.value = input;
    }

    public Arrangement toArrangement() {
        return Arrangement.toArrangement(value);
    }

    public Coordinate toCoordinate() {
        return Coordinate.toCoordinate(value);
    }
}
