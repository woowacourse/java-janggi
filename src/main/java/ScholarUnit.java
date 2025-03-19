public class ScholarUnit implements Unit {

    private final Point point;
    private final String team;

    public ScholarUnit(Point point, String team) {
        this.point = point;
        this.team = team;
    }

    public static Unit of(Point point, String team) {
        return new ScholarUnit(point, team);
    }

    @Override
    public Unit move(Point point) {
        return null;
    }
}
