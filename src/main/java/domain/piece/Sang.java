package domain.piece;

import domain.JanggiCoordinate;
import java.util.List;

public class Sang implements Piece {

    private static final List<JanggiCoordinate> OFFSET_MOVEMENT = List.of(
            new JanggiCoordinate(3, 2),
            new JanggiCoordinate(3, -2),
            new JanggiCoordinate(-3, 2),
            new JanggiCoordinate(-3, -2),
            new JanggiCoordinate(2, 3),
            new JanggiCoordinate(2, -3),
            new JanggiCoordinate(-2, 3),
            new JanggiCoordinate(-2, -3)
    );

    private final Team team;

    public Sang(Team team) {
        this.team = team;
    }

    @Override
    public boolean isPho() {
        return false;
    }

    @Override
    public Team getTeam() {
        return team;
    }
}
