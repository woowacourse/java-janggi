package model.position;

public record Column(int value) {
    private static final Column INVALID = new Column(-1);
    public static Column from(int value) {
        return new Column(value);
    }

    public static Column inValid() {
        return INVALID;
    }

    public int diff(Column to) {
        return to.value - this.value;
    }
}
