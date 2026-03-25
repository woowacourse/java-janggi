package janggi;

import java.util.ArrayList;
import java.util.List;

public class Cha extends Gimul{

    protected Cha(Team team) {
        super(team);
    }

    @Override
    public Path getLegalPath(Position from, Position to) {
        if (from.row() != to.row() && from.column() != to.column()) {
            throw new IllegalArgumentException();
        }

        ArrayList<Position> positions = new ArrayList<>();

        return null;
    }

    @Override
    public boolean canPassThrough(List<Gimul> gimuls) {
        return false;
    }
}
