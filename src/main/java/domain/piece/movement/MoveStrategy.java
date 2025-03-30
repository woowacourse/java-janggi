package domain.piece.movement;

import domain.Moves;
import domain.piece.Position;
import domain.piece.Team;
import java.util.List;

public interface MoveStrategy {

    List<Moves> findPossibleMoves(Position src, Position dest, Team team);
}
