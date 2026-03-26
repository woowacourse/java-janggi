package domain.movement;

import domain.game.Position;
import java.util.List;

public interface Movement {
    List<Path> candidatePaths(Position from);
}
