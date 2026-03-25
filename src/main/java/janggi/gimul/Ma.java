package janggi.gimul;

import janggi.Path;
import janggi.Position;
import janggi.Team;
import java.util.List;

public class Ma extends Gimul{

    protected Ma(Team team) {
        super(team);
    }

    @Override
    public Path getLegalPath(Position from, Position to) {
        return null;
    }

    @Override
    public boolean canPassThrough(List<Gimul> gimuls) {
        return gimuls.isEmpty();
    }
}
