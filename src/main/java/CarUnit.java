import java.util.Objects;

public class CarUnit implements Unit {
    private final Point point;
    private final String team;

    public CarUnit(Point point, String team) {
        this.point = point;
        this.team = team;
    }

    public static Unit of(Point point, String team) {
        return new CarUnit(point, team);
    }

    @Override
    public CarUnit move(Point movedPoint) {
        if (point.equals(movedPoint) || !point.isHorizontal(movedPoint)) {
            throw new IllegalArgumentException("");
        }
        // TODO: 이동 경로에 기물이 존재할 시 이동할 수 없다.
        return new CarUnit(movedPoint, this.team);
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
