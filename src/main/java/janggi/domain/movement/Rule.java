package janggi.domain.movement;

import janggi.domain.Position;
import java.util.List;

public interface Rule {
    List<Position> execute(Position from);
}
