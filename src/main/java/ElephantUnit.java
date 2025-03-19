public class ElephantUnit implements Unit {
    private final Point point;
    private final String team;

    public ElephantUnit(Point point, String team) {
        this.point = point;
        this.team = team;
    }

    public static Unit of(Point point, String team) {
        return new ElephantUnit(point, team);
    }

    @Override
    public Unit move(Point point) {
        return null;
    }
}
