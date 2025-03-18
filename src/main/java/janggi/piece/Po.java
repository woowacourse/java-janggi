package janggi.piece;

import janggi.InitialPoint;
import janggi.Movable;
import janggi.Point;
import janggi.TeamColor;
import java.util.ArrayList;
import java.util.List;

public class Po implements Movable {

    private static final String NAME = "포";
    private static final List<Po> pos;

    private final TeamColor color;
    private final Point point;

    public Po(TeamColor color, Point point) {
        this.color = color;
        this.point = point;
    }

    static {
        List<Po> createdPieces = new ArrayList<>();
        for(int i=0; i<2; i++) {
            for (Point point : InitialPoint.GUNG.getRedPoints()) {
                createdPieces.add(new Po(TeamColor.RED, point));
            }
            for (Point point : InitialPoint.GUNG.getBluePoints()) {
                createdPieces.add(new Po(TeamColor.BLUE, point));
            }
        }
        pos = createdPieces;
    }
    public static List<Po> values() {
        return new ArrayList<>(pos);
    }
}
