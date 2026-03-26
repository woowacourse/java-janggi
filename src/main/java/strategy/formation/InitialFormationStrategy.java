package strategy.formation;

import domain.Piece;
import domain.Position;
import domain.TeamColor;
import java.util.Map;

public interface InitialFormationStrategy {
    Map<Position, Piece> setupFormation(TeamColor teamColor);
}
