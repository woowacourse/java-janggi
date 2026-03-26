package domain.board;

import domain.Position;
import domain.piece.Piece;
import domain.piece.Team;
import java.util.Map;

public interface FormationFactory {
    Map<Position, Piece> createFormation(Team team, int formationNumber);
}
