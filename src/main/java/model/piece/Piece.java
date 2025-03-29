package model.piece;

import java.util.Map;
import model.Path;
import model.Point;
import model.Team;
import model.Moving;

public abstract class Piece {
    Team team;
    PieceName pieceName;
    int score;

    protected Piece(Team team, PieceName pieceName) {
        this.team = team;
        this.pieceName = pieceName;
    }

    public Team getTeam() {
        return team;
    }

    public abstract boolean isValidPoint(Point beforePoint, Point targetPoint);

    public Path calculatePath(Point beforePoint, Point targetPoint) {

        Moving moving = new Moving(beforePoint, targetPoint);
        Path path = new Path();

        for (int i = 0; i < moving.getBiggerVector(); i++) {
            path.addPoint(new Point(targetPoint.x() - moving.getUnitVectorX() * i,
                    targetPoint.y() - moving.getUnitVectorY() * i));
        }

        return path;
    }
    public abstract boolean canMove(Map<Piece, Boolean> piecesOnPathWithTargetOrNot);

    public PieceName getPieceName() {
        return pieceName;
    }

    public int getScore() {
        return score;
    }
}
