package domain.movement;

import domain.board.Intersection;
import java.util.List;

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

    public static Vector up() {
        return new Vector(-1, 0);
    }

    public static Vector down() {
        return new Vector(1, 0);
    }

    public static Vector left() {
        return new Vector(0, -1);
    }

    public static Vector right() {
        return new Vector(0, 1);
    }

    public static Vector leftUp() {
        return new Vector(-1, -1);
    }

    public static Vector leftDown() {
        return new Vector(1, -1);
    }

    public static Vector rightUp() {
        return new Vector(-1, 1);
    }

    public static Vector rightDown() {
        return new Vector(1, 1);
    }

    public static List<Vector> cardinals() {
        return List.of(
                Vector.up(),
                Vector.down(),
                Vector.left(),
                Vector.right()
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
