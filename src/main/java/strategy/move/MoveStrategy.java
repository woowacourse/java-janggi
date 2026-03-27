package strategy.move;

import domain.MovePath;
import domain.TeamColor;
import java.util.List;

public interface MoveStrategy {

    public List<MovePath> getPaths(TeamColor teamColor);

}
