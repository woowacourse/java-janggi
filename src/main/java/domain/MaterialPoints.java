package domain;

public record MaterialPoints(double value) {

    public static MaterialPoints zero() {
        return new MaterialPoints(0);
    }

    public static MaterialPoints of(double value) {
        return new MaterialPoints(value);
    }

    public MaterialPoints plus(MaterialPoints other) {
        return new MaterialPoints(this.value + other.value);
    }
}
