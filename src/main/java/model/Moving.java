package model;

public class Moving {

    private final Point beforePoint;
    private final Point targetPoint;

    public Moving(Point beforePoint, Point targetPoint) {
        this.beforePoint = beforePoint;
        this.targetPoint = targetPoint;
    }

    public int getVectorX() {
        return targetPoint.x() - beforePoint.x();
    }

    public int getVectorXSize() {
        return Math.abs(getVectorX());
    }

    public int getVectorY() {
        return targetPoint.y() - beforePoint.y();
    }

    public int getVectorYSize() {
        return Math.abs(getVectorY());
    }

    public int getUnitVectorX() {
        int vector = getVectorX();
        if (vector == 0) {
            return 0;
        }
        return vector / Math.abs(vector);
    }

    public int getUnitVectorY() {
        int vector = getVectorY();
        if (vector == 0) {
            return 0;
        }
        return vector / Math.abs(vector);
    }

    public int getBiggerVector() {
        int vectorX = getVectorX();
        int vectorY = getVectorY();
        return Math.max(Math.abs(vectorX), Math.abs(vectorY));
    }

    public int getDistance() {
        int vectorX = getVectorX();
        int vectorY = getVectorY();
        return vectorX * vectorX + vectorY * vectorY;
    }

    public boolean isDistance(int distance) {
        return getDistance() == distance;
    }

    public boolean isDistanceLessThanOrEqualTo(int distance) {
        return getDistance() <= distance;
    }

    public boolean isUpDownMoving() {
        return (getVectorX() == 0) ^ (getVectorY() == 0);
    }
}
