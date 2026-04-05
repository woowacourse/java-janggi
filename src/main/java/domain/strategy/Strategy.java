package domain.strategy;

import domain.Team;
import domain.position.Position;
import domain.PieceProvider;

import java.util.List;

public interface Strategy {
    List<Position> getMoveCandidates(Position from, Team team, PieceProvider board);
}
