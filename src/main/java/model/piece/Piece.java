package model.piece;

import java.util.Map;
import model.Path;
import model.Point;
import model.Team;

public abstract class Piece {
    Team team;
    PieceName pieceName;

    protected Piece(Team team) {
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }

    public abstract boolean isValidPoint(Point beforePoint, Point targetPoint);

    public abstract Path calculatePath(Point beforePoint, Point targetPoint);

    public abstract boolean canMove(Map<Piece, Boolean> piecesOnPathWithTargetOrNot);

    public PieceName getPieceName() {
        return pieceName;
    }

    public int getVectorX(Point beforePoint, Point targetPoint){
        return targetPoint.x() - beforePoint.x();
    }
    public int getVectorY(Point beforePoint, Point targetPoint){
        return targetPoint.y() - beforePoint.y();
    }

    public int getUnitVector(int vector){
        if(vector==0){
            return 0;
        }
        return vector/Math.abs(vector);
    }
}
