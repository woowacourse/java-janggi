package janggi.piece;

import janggi.point.InitialPoint;
import janggi.Movable;
import janggi.point.Point;
import janggi.TeamColor;
import java.util.ArrayList;
import java.util.List;

public class Sa implements Movable {

    private static final String NAME = "사";
    private static final List<Sa> sas;

    private final TeamColor color;
    private final Point point;

    public Sa(TeamColor color, Point point) {
        this.color = color;
        this.point = point;
    }

    static {
        List<Sa> createdPieces = new ArrayList<>();
        for(int i=0; i<2; i++) {
            for (Point point : InitialPoint.GUNG.getRedPoints()) {
                createdPieces.add(new Sa(TeamColor.RED, point));
            }
            for (Point point : InitialPoint.GUNG.getBluePoints()) {
                createdPieces.add(new Sa(TeamColor.BLUE, point));
            }
        }
        sas = createdPieces;
    }
    public static List<Sa> values() {
        return new ArrayList<>(sas);
    }
}
