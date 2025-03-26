package model.piece;

import java.util.Map;
import model.Path;
import model.Point;
import model.Team;

public abstract class Piece implements Hierarchy {
    protected Team team;
    protected PieceInfo pieceInfo;

    public Piece(Team team) {
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }

    public Path calculatePath(Point beforePoint, Point targetPoint) {
        int vectorX = targetPoint.x() - beforePoint.x();
        int vectorY = targetPoint.y() - beforePoint.y();

        Path path = new Path();

        for (int i = 0; i < Math.max(Math.abs(vectorX), Math.abs(vectorY)); i++) {
            path.addPoint(new Point(targetPoint.x() - i * getUnitVector(vectorX),
                    targetPoint.y() - i * getUnitVector(vectorY)));
        }

        return path;
    }

    private int getUnitVector(int vector) {
        if (vector == 0) {
            return 0;
        }
        return vector / Math.abs(vector);
    }

    public abstract boolean isValidPoint(Point beforePoint, Point targetPoint);

    public abstract boolean canMove(Map<Piece, Boolean> piecesOnPathWithTargetOrNot);

    public String getPieceName() {
        return pieceInfo.getName();
    }

    public double getPieceScore() {
        return pieceInfo.getScore();
    }

}
