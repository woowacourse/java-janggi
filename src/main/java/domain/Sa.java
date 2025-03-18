package domain;

import java.util.Objects;

public class Sa {

    private final Coordinate coordinate;
    private final Team team;

    public Sa(Coordinate coordinate, Team team) {
        this.coordinate = coordinate;
        this.team = team;
    }

    public Sa moveLeft() {
        int distance = team.applyDirection(-1);
        return new Sa(coordinate.changeWidth(distance), team);
    }

    public Sa moveRight() {
        int distance = team.applyDirection(1);
        return new Sa(coordinate.changeWidth(distance), team);
    }

    public Sa moveForward() {
        int distance = team.applyDirection(-1);
        return new Sa(coordinate.changeHeight(distance), team);
    }

    public Sa moveBackward() {
        int distance = team.applyDirection(1);
        return new Sa(coordinate.changeHeight(distance), team);
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Sa sa = (Sa) object;
        return Objects.equals(coordinate, sa.coordinate) && team == sa.team;
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinate, team);
    }
}
