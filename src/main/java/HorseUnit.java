import java.util.List;

public class HorseUnit implements Unit {
    private final Point point;
    private final String team;

    public HorseUnit(Point point, String team) {
        this.point = point;
        this.team = team;
    }

    public static Unit of(Point point, String team) {
        return new HorseUnit(point, team);
    }

    @Override
    public Unit move(Point point) {
        return null;
    }
}
