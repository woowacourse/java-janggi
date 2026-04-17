package domain.strategy;

import domain.Team;
import domain.position.Position;
import domain.board.PieceProvider;

import java.util.List;

public interface Strategy {
    List<Direction> getDirections();
    List<Position> getMoveCandidates(Position from, Team team, PieceProvider board);
}
