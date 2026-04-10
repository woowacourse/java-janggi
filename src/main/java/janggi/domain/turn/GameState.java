package janggi.domain.turn;

import janggi.domain.position.Position;
import janggi.domain.space.Space;
import janggi.domain.space.piece.Team;
import java.util.Map;
import java.util.Optional;

public interface GameState {
    GameState move(Position from, Position to);

    boolean isFinished();

    Map<Position, Space> captureBoard();

    Optional<Team> getTeam();

    double calculatePieceScore();
}
