package janggi.piece;

import janggi.Position;
import janggi.Score;
import janggi.Team;

import java.util.List;

public class Horse extends Piece {

    public Horse(final Position position, final Team team) {
        super(position, team);
    }

    @Override
    public List<Position> getRoute(final Position destination) {
        return List.of();
    }

    @Override
    public Piece move(final Position destination) {
        return new Horse(destination, team);
    }

    @Override
    public Score die() {
        return Score.Horse();
    }
}
