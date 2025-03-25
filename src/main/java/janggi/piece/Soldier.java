package janggi.piece;

import janggi.board.Position;

import java.util.List;

public class Soldier extends Piece {
    private static final String NAME = "졸";

    public Soldier(Team team) {
        super(team);
    }

    public List<Position> findPositionsInPath(Position start, Position goal) {
        return List.of(goal);
    }

    @Override
    protected String getName() {
        return NAME;
    }

    @Override
    public boolean isSameType(PieceType pieceType) {
        return pieceType == PieceType.SOLDIER;
    }
}
