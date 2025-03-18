package domain.piece;

import domain.Coordinate;
import domain.Team;
import java.util.Objects;

public class Piece {

    private final Coordinate coordinate;
    private final Team team;

    public Piece(Coordinate coordinate, Team team) {
        this.coordinate = coordinate;
        this.team = team;
    }

    public Piece moveLeft(int distance) {
        int appliedDistance = team.applyDirection(-1 * distance);
        return new Piece(coordinate.changeWidth(appliedDistance), team);
    }

    public Piece moveRight(int distance) {
        int appliedDistance = team.applyDirection(distance);
        return new Piece(coordinate.changeWidth(appliedDistance), team);
    }

    public Piece moveForward(int distance) {
        int appliedDistance = team.applyDirection(-1 * distance);
        return new Piece(coordinate.changeHeight(appliedDistance), team);
    }

    public Piece moveBackward(int distance) {
        int appliedDistance = team.applyDirection(distance);
        return new Piece(coordinate.changeHeight(appliedDistance), team);
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Piece piece = (Piece) object;
        return Objects.equals(coordinate, piece.coordinate) && team == piece.team;
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinate, team);
    }

    @Override
    public String toString() {
        return "Piece{" +
                "coordinate=" + coordinate +
                ", team=" + team +
                '}';
    }
}
