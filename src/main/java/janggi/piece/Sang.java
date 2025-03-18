package janggi.piece;

import janggi.InitialPoint;
import janggi.Movable;
import janggi.Point;
import janggi.TeamColor;
import java.util.ArrayList;
import java.util.List;

public class Sang implements Movable {

    private static final String NAME = "상";
    private static final List<Sang> sangs;

    private final TeamColor color;
    private final Point point;

    public Sang(TeamColor color, Point point) {
        this.color = color;
        this.point = point;
    }

    static {
        List<Sang> createdPieces = new ArrayList<>();
        for(int i=0; i<2; i++) {
            for (Point point : InitialPoint.GUNG.getRedPoints()) {
                createdPieces.add(new Sang(TeamColor.RED, point));
            }
            for (Point point : InitialPoint.GUNG.getBluePoints()) {
                createdPieces.add(new Sang(TeamColor.BLUE, point));
            }
        }
        sangs = createdPieces;
    }
    public static List<Sang> values() {
        return new ArrayList<>(sangs);
    }
}
