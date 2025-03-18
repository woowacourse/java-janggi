package janggi.piece;

import janggi.Position;
import janggi.Score;
import janggi.Team;

import java.util.List;

public class Guard extends Piece {

    public Guard(final Position position, final Team team) {
        super(position, team);
    }

    @Override
    public List<Position> getRoute(final Position destination) {
        return List.of();
    }

    @Override
    public Piece move(final Position destination) {
        return new Guard(destination, team);
    }

    @Override
    public Score die() {
        return Score.Guard();
    }
}
