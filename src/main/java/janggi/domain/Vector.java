package janggi.domain;

import java.util.List;

public record Vector(int y, int x) {

    public Vector side(Side side) {
        if (side == Side.CHO) {
            return new Vector(-y, x);

        }
        return new Vector(y, x);
    }

    public static List<List<Vector>> rotate(List<List<Vector>> vectorsList) {
        return vectorsList.stream()
                .map(list -> list.stream()
                        .map(vector -> new Vector(vector.x(), -vector.y()))
                        .toList()
                )
                .toList();
    }
}
