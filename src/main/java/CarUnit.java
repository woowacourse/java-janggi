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
        // TODO: 이동 경로에 기물이 존재할 시 이동할 수 없다.
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
