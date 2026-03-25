package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.Team;
import java.util.List;

public class Sang extends Piece {

    public Sang(Team team) {
        super(team);
    }

    @Override
    public void validateMove(Position from, Position to) {

    }

    @Override
    public List<Position> getRoutes(Position from, Position to) {
        return List.of();
    }
}
