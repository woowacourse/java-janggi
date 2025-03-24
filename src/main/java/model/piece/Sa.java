package model.piece;

import java.util.Map;
import model.Path;
import model.Point;
import model.Team;

public class Sa extends Piece {

    public Sa(Team team) {
        super(team,PieceName.SA);
    }

    @Override
    public boolean isValidPoint(Point beforePoint, Point targetPoint) {
        int vectorX = getVectorX(beforePoint, targetPoint);
        int vectorY = getVectorY(beforePoint, targetPoint);

        return Math.pow(vectorX,2) + Math.pow(vectorY,2) == 1;
    }

    @Override
    public Path calculatePath(Point beforePoint, Point targetPoint) {
        Path path = new Path();
        path.addPoint(new Point(targetPoint.x(), targetPoint.y()));
        return path;
    }

    @Override
    public boolean canMove(Map<Piece, Boolean> piecesOnPathWithTargetOrNot) {
        if (piecesOnPathWithTargetOrNot.size() == 1) {
            return piecesOnPathWithTargetOrNot.keySet().stream()
                    .findFirst()
                    .get()
                    .getTeam() != this.team;
        }
        return true;
    }
}
