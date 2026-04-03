package domain;

public final class MaterialPoints {

    private final double value;

    private MaterialPoints(double value) {
        this.value = value;
    }

    public static MaterialPoints zero() {
        return new MaterialPoints(0);
    }

    public static MaterialPoints of(double value) {
        return new MaterialPoints(value);
    }

    public MaterialPoints plus(MaterialPoints other) {
        return new MaterialPoints(this.value + other.value);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        MaterialPoints that = (MaterialPoints) object;
        return Double.compare(that.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(value);
    }
}
