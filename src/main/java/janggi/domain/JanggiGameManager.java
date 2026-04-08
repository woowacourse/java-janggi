package janggi.domain;

import janggi.domain.turn.GameState;
import janggi.domain.position.Position;
import janggi.domain.space.Space;
import janggi.domain.space.piece.Team;
import java.util.Map;
import java.util.Optional;

public class JanggiGameManager {
    private GameState currentState;

    public JanggiGameManager(GameState currentState) {
        this.currentState = currentState;
    }

    public void move(Position from, Position to) {
        currentState = currentState.move(from, to);
    }

    public boolean isFinished() {
        return currentState.isFinished();
    }

    public Map<Position, Space> captureBoard() {
        return currentState.captureBoard();
    }

    public Optional<Team> getCurrentTeam() {
        return currentState.getTeam();
    }

    public double getPieceScore() {
        return currentState.calculatePieceScore();
    }
}
