package domain.movement;

import domain.board.Intersection;

public record Vector(
        int rowDelta,
        int fileDelta
) {

    private static final double COS_45 = Math.cos(Math.PI / 4);
    private static final double SIN_45 = Math.sin(Math.PI / 4);

    public Intersection next(Intersection intersection) {
        return new Intersection(
                intersection.getRow() + rowDelta,
                intersection.getFile() + fileDelta
        );
    }

    public Vector turnLeft45Degrees() {
        return rotate(COS_45, SIN_45);
    }

    public Vector turnRight45Degrees() {
        return rotate(COS_45, -SIN_45);
    }

    private Vector rotate(double cos, double sin) {
        double rotatedFile = fileDelta * cos - rowDelta * sin;
        double rotatedRow = fileDelta * sin + rowDelta * cos;

        return new Vector(
                (int) Math.round(rotatedRow),
                (int) Math.round(rotatedFile)
        );
    }
}
