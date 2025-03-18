package janggi.piece;

import janggi.InitialPoint;
import janggi.Movable;
import janggi.Point;
import janggi.TeamColor;
import java.util.ArrayList;
import java.util.List;

public class Cha implements Movable {

    private static final String NAME = "차";
    private static final List<Cha> chas;

    private final TeamColor color;
    private final Point point;

    public Cha(TeamColor color, Point point) {
        this.color = color;
        this.point = point;
    }

    static {
        List<Cha> createdPieces = new ArrayList<>();
        for(int i=0; i<2; i++) {
            for (Point point : InitialPoint.GUNG.getRedPoints()) {
                createdPieces.add(new Cha(TeamColor.RED, point));
            }
            for (Point point : InitialPoint.GUNG.getBluePoints()) {
                createdPieces.add(new Cha(TeamColor.BLUE, point));
            }
        }
        chas = createdPieces;
    }
    public static List<Cha> values() {
        return new ArrayList<>(chas);
    }
}
