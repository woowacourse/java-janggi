package janggi.domain.move;

import janggi.domain.Team;

public record Vector(int y, int x) {

    public static Vector rotate(Vector vector) {
        return new Vector(vector.x(), -vector.y());
    }

    public Vector side(Team team) {
        if (team.isSameSide(Team.CHO)) {
            return new Vector(-y, x);
        }
        return new Vector(y, x);
    }

    public Vector add(Vector vector) {
        return new Vector(y + vector.y, x + vector.x);
    }
}
