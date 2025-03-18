package janggi.piece;

import janggi.InitialPoint;
import janggi.Movable;
import janggi.Point;
import janggi.TeamColor;
import java.util.ArrayList;
import java.util.List;

public class Ma implements Movable {

    private static final String NAME = "마";
    private static final List<Ma> mas;

    private final TeamColor color;
    private final Point point;

    public Ma(TeamColor color, Point point) {
        this.color = color;
        this.point = point;
    }

    static {
        List<Ma> createdPieces = new ArrayList<>();
        for(int i=0; i<2; i++) {
            for (Point point : InitialPoint.GUNG.getRedPoints()) {
                createdPieces.add(new Ma(TeamColor.RED, point));
            }
            for (Point point : InitialPoint.GUNG.getBluePoints()) {
                createdPieces.add(new Ma(TeamColor.BLUE, point));
            }
        }
        mas = createdPieces;
    }
    public static List<Ma> values() {
        return new ArrayList<>(mas);
    }
}
