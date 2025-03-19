package model;

public class Pho extends Piece {

    public Pho(Team team) {
        super(team);
    }

    @Override
    public boolean isValidPoint(Point beforePoint, Point afterPoint) {
        return !((beforePoint.x() == afterPoint.x() && beforePoint.y() == afterPoint.y()) || (beforePoint.x() != afterPoint.x() && beforePoint.y() != afterPoint.y()));
    }

    @Override
    public Path calculatePath(Point beforePoint, Point afterPoint) {
        int vectorX = afterPoint.x() - beforePoint.x();
        int vectorY = afterPoint.y() - beforePoint.y();

        Path path = new Path();

        if (vectorX == 0) {
            int unitVectorY = vectorY / Math.abs(vectorY);
            for (int i = 0; i < Math.abs(vectorY); i++) {
                path.addPoint(new Point(afterPoint.x(), afterPoint.y() - unitVectorY * i));
            }
        }

        if (vectorY == 0) {
            int unitVectorX = vectorX / Math.abs(vectorX);
            for (int i = 0; i < Math.abs(vectorX); i++) {
                path.addPoint(new Point(afterPoint.x() - unitVectorX * i, afterPoint.y()));
            }
        }
        return path;
    }

    @Override
    public boolean canMove(int size) {
        return false;
    }
}