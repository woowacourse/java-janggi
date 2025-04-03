package model.piece;

import java.util.Map;
import model.Moving;
import model.Path;
import model.Point;
import model.Team;

public abstract class Piece {

    private final Team team;
    private final PieceName pieceName;
    private final Score score;

    protected Piece(Team team, PieceName pieceName, Score score) {
        this.team = team;
        this.pieceName = pieceName;
        this.score = score;
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
        return score.getScore();
    }

    public boolean isEnemy(Piece piece) {
        return piece.team != team;
    }
}
