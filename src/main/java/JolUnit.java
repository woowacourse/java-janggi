public class JolUnit implements Unit {
    private final Point point;
    private final String team;

    public JolUnit(Point point, String team) {
        this.point = point;
        this.team = team;
    }

    public static Unit of(Point point, String team) {
        return new JolUnit(point, team);
    }

    @Override
    public Unit move(Point point) {
        return null;
    }
}
