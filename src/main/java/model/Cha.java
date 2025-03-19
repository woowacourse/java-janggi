package model;

public class Cha extends Piece {

    public Cha(String team) {
        super(team);
    }

    @Override
    public boolean isValidPoint(int beforeX, int beforeY, int afterX, int afterY) {
        return !((beforeX == afterX && beforeY == afterY) || (beforeX != afterX && beforeY != afterY));
    }

    @Override
    public Path calculatePath(int beforeX, int beforeY, int afterX, int afterY) {
        int vectorX = afterX - beforeX;
        int vectorY = afterY - beforeY;

        Path path = new Path();

        if (vectorX == 0) {
            int unitVectorY = vectorY / Math.abs(vectorY);
            for (int i = 0; i < Math.abs(vectorY); i++) {
                path.addPoint(new Point(afterX, afterY - unitVectorY * i));
            }
        }

        if (vectorY == 0) {
            int unitVectorX = vectorX / Math.abs(vectorX);
            for (int i = 0; i < Math.abs(vectorX); i++) {
                path.addPoint(new Point(afterX - unitVectorX * i, afterY));
            }
        }
        return path;
    }

    @Override
    public boolean canMove(int size) {
        return false;
    }
}
