package janggi.piece;

import janggi.Board;
import janggi.Position;
import janggi.Score;
import janggi.Team;

public class Chariot extends Piece {

    public Chariot(final Position position, final Team team) {
        super(position, team);
    }

    @Override
    public Piece move(final Board board, final Position destination) {
        return new Chariot(destination, team);
    }

    @Override
    public Score die() {
        return Score.Chariot();
    }
}
