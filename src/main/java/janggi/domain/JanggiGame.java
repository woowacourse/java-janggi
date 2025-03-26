package janggi.domain;

import janggi.domain.board.PlayingBoard;
import janggi.domain.board.Position;
import janggi.domain.gameState.State;
import janggi.domain.piece.TeamColor;
import janggi.domain.piece.PieceType;
import java.util.Map;

public class JanggiGame {
    private State state;
    private final Map<TeamColor, Integer> teamScore;

    public JanggiGame(State state, Map<TeamColor, Integer> teamScore) {
        this.state = state;
        this.teamScore = teamScore;
    }

    public void move(PieceType pieceType, Position source, Position destination) {
        TeamColor turnColor = state.getColor();
        int score = state.getDestinationPieceScore(destination);

        this.state = state.movePiece(pieceType, source, destination);

        teamScore.put(turnColor, teamScore.getOrDefault(turnColor, 0) + score);
    }

    public TeamColor getTurnColor() {
        return this.state.getColor();
    }

    public boolean isFinished() {
        return state.isFinished();
    }

    public Map<TeamColor, Integer> getTeamScore() {
        return teamScore;
    }

    public PlayingBoard getPlayingBoard() {
        return state.getPlayingBoard();
    }
}
