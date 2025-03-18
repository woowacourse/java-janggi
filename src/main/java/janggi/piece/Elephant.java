package janggi.piece;

import janggi.Board;
import janggi.Position;
import janggi.Score;
import janggi.Team;

public class Elephant extends Piece {

    public Elephant(final Position position, final Team team) {
        super(position, team);
    }

    @Override
    public Piece move(final Board board, final Position destination) {
        return new Elephant(destination, team);
    }

    @Override
    public Score die() {
        return Score.Elephant();
    }
}
