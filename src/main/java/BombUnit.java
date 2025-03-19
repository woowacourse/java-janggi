public class BombUnit implements Unit {
    private final Point point;
    private final String team;

    public BombUnit(Point point, String team) {
        this.point = point;
        this.team = team;
    }

    public static Unit of(Point point, String team) {
        return new BombUnit(point, team);
    }

    @Override
    public Unit move(Point point) {
        return null;
    }
}
