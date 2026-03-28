package model.position;

public record Column(int value) {
    public static Column from(int value) {
        return new Column(value);
    }

    public int diff(Column to) {
        return to.value - this.value;
    }
}
