package domain;

import java.util.Objects;

public class Jol {
    private final Coordinate coordinate;
    private final Team team;

    public Jol(Coordinate coordinate, Team team) {
        this.coordinate = coordinate;
        this.team = team;
    }

    public Jol moveLeft() {
        int distance = team.applyDirection(-1);
        return new Jol(coordinate.changeWidth(distance), team);
    }

    public Jol moveRight() {
        int distance = team.applyDirection(1);
        return new Jol(coordinate.changeWidth(distance), team);
    }

    public Jol moveForward() {
        int distance = team.applyDirection(-1);
        return new Jol(coordinate.changeHeight(distance), team);
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Jol jol = (Jol) object;
        return Objects.equals(coordinate, jol.coordinate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(coordinate);
    }
}
