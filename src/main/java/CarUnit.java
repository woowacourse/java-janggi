import java.util.Objects;

public class CarUnit implements Unit {
    private final Point point;

    public CarUnit(Point point) {
        this.point = point;
    }

    @Override
    public CarUnit move(Point movedPoint) {
        if (point.equals(movedPoint) || !point.isHorizontal(movedPoint)) {
            throw new IllegalArgumentException("");
        }
        return new CarUnit(movedPoint);
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        CarUnit carUnit = (CarUnit) object;
        return Objects.equals(point, carUnit.point);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(point);
    }
}
